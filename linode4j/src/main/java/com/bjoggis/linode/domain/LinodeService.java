package com.bjoggis.linode.domain;

import com.bjoggis.linode.adapter.in.CreateInstanceResponse;
import com.bjoggis.linode.adapter.in.GetInstanceResponse;
import com.bjoggis.linode.adapter.out.api.AttachVolumeRequestBody;
import com.bjoggis.linode.adapter.out.api.CreateInstanceRequestBody;
import com.bjoggis.linode.adapter.out.api.LinodeInterface;
import com.bjoggis.linode.adapter.out.database.LinodeInstanceDbo;
import com.bjoggis.linode.adapter.out.database.LinodeRepository;
import com.bjoggis.linode.configuration.properties.LinodeProperties;
import com.bjoggis.linode.domain.event.InstanceCreatedEvent;
import com.bjoggis.linode.domain.event.InstanceRunningEvent;
import com.bjoggis.linode.domain.event.LinodeEventPublisher;
import com.bjoggis.linode.domain.event.VolumeAttachedEvent;
import com.bjoggis.linode.model.InstanceType;
import com.bjoggis.linode.model.LinodeInstance;
import com.bjoggis.linode.model.Page;
import com.bjoggis.linode.model.Region;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

public class LinodeService {

  private static final long VOLUME_ID = 2034287L;

  private final Logger logger = LoggerFactory.getLogger(LinodeService.class);

  private final LinodeProperties properties;
  private final LinodeInterface api;
  private final LinodeRepository instanceRepository;

  private final LinodeEventPublisher eventPublisher;

  public LinodeService(LinodeProperties properties, LinodeInterface linodeInterface,
      LinodeRepository instanceRepository, LinodeEventPublisher eventPublisher) {
    this.properties = properties;
    this.api = linodeInterface;
    this.instanceRepository = instanceRepository;
    this.eventPublisher = eventPublisher;
  }

  @Cacheable("linode-instance-types")
  public Page<InstanceType> getLinodeInstanceTypes() {
    return api.types();
  }

  @Cacheable("linode-regions")
  public Page<Region> getLinodeRegions() {
    return api.regions();
  }


  public CreateInstanceResponse createInstance() {

    final Long id = instanceRepository.getNextAvailableId();

    CreateInstanceRequestBody body = new CreateInstanceRequestBody();
    body.setRegion("se-sto");
    body.setImage("linode/ubuntu22.04");
    body.setLabel("minecraft-auto-config-" + id);
    body.setType("g6-standard-2");
    body.setTags(List.of("minecraft", "auto-created"));
    body.setRootPassword((properties.instance().password()));

    LinodeInstance linodeInstance = api.create(body);
    logger.info("Created linode instance: {}", linodeInstance);

    LinodeInstanceDbo dbo = instanceRepository.saveLinodeInstanceToDb(linodeInstance);

    eventPublisher.onInstanceCreated(new InstanceCreatedEvent(dbo.getLinodeId(), dbo.getCreated()));

    return CreateInstanceResponse.fromDbo(dbo);
  }

  public void deleteInstance(Long linodeId) {
    Optional<LinodeInstanceDbo> dbo = instanceRepository.findByLinodeIdAndNotDeleted(linodeId);

    if (dbo.isEmpty()) {
      logger.warn("Could not find linode instance with id: {}", linodeId);
      return;
    }

    api.delete(linodeId);
    instanceRepository.setAsDeleted(dbo.get().getId());
    logger.info("Deleted linode instance: {}", linodeId);
  }

  @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
  @Transactional
  public void checkStatusFrequent() {
    List<LinodeInstanceDbo> instances = instanceRepository.findAllWithStatusNotReady();

    if (instances.isEmpty()) {
      return;
    }

    logger.info("Checking status for {} instances", instances.size());

    for (LinodeInstanceDbo instance : instances) {
      LinodeInstance linodeInstance = api.getInstance(instance.getLinodeId());

      logger.info("Linode instance: {} has status: {}", instance.getLinodeId(), linodeInstance.status());
      instanceRepository.updateStatus(instance.getId(), linodeInstance.status());

      if ("running".equalsIgnoreCase(linodeInstance.status())) {
        eventPublisher.onInstanceRunning(new InstanceRunningEvent(instance.getLinodeId()));
      }
    }
  }

  @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.MINUTES)
  public void checkStatusFull() {
    List<LinodeInstanceDbo> instances = instanceRepository.findAllNotDeleted();

    logger.info("Doing full status check for {} instances", instances.size());

    for (LinodeInstanceDbo instance : instances) {
      LinodeInstance linodeInstance = api.getInstance(instance.getLinodeId());
      instanceRepository.updateStatus(instance.getId(), linodeInstance.status());
    }
  }

  public GetInstanceResponse getLinodeInstanceById(Long id) {
    Optional<LinodeInstanceDbo> dbo = instanceRepository.findByLinodeIdAndNotDeleted(id);

    return dbo.map(GetInstanceResponse::fromDbo).orElse(null);

  }

  public List<GetInstanceResponse> getAllActiveLinodeInstances() {
    List<LinodeInstanceDbo> allNotDeleted = instanceRepository.findAllNotDeleted();

    return allNotDeleted.stream()
        .map(GetInstanceResponse::fromDbo)
        .toList();
  }

  public void attachVolume(Long linodeId) throws InterruptedException {
    AttachVolumeRequestBody body = new AttachVolumeRequestBody();
    body.setLinodeId(linodeId);
    body.setPersistAcrossBoots(false);

    api.attach(VOLUME_ID, body);

    Thread.sleep(Duration.ofSeconds(5));
    eventPublisher.onVolumeAttached(new VolumeAttachedEvent(linodeId, VOLUME_ID));
  }

  public void setupMinecraftServer(Long linodeId) {
    logger.info("Setting up minecraft server for linode instance: {}", linodeId);
  }
}
