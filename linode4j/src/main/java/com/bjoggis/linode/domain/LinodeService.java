package com.bjoggis.linode.domain;

import com.bjoggis.linode.adapter.in.CreateInstanceRequest;
import com.bjoggis.linode.adapter.in.CreateInstanceResponse;
import com.bjoggis.linode.adapter.out.api.CreateInstanceRequestBody;
import com.bjoggis.linode.adapter.out.api.LinodeInterface;
import com.bjoggis.linode.adapter.out.database.LinodeInstanceDbo;
import com.bjoggis.linode.adapter.out.database.LinodeRepository;
import com.bjoggis.linode.model.InstanceType;
import com.bjoggis.linode.model.LinodeInstance;
import com.bjoggis.linode.model.Page;
import com.bjoggis.linode.model.Region;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationListener;

public class LinodeService implements ApplicationListener<ApplicationStartedEvent> {

  private final LinodeInterface api;
  private final LinodeRepository instanceRepository;
  private final Logger logger = LoggerFactory.getLogger(LinodeService.class);

  public LinodeService(LinodeInterface linodeInterface, LinodeRepository instanceRepository) {
    this.api = linodeInterface;
    this.instanceRepository = instanceRepository;
  }

  @Cacheable("linode-instance-types")
  public Page<InstanceType> getLinodeInstanceTypes() {
    return api.types();
  }

  @Cacheable("linode-regions")
  public Page<Region> getLinodeRegions() {
    return api.regions();
  }


  public CreateInstanceResponse createInstance(CreateInstanceRequest request) {

    final Long id = instanceRepository.getNextAvailableId();

    CreateInstanceRequestBody body = new CreateInstanceRequestBody();
    body.setRegion("se-sto");
    body.setImage("linode/ubuntu22.04");
    body.setLabel("minecraft-auto-config-" + id);
    body.setType("g6-nanode-1");
    body.setTags(List.of("minecraft", "auto-created"));
    body.setRootPassword("mag*cdt!ykd7KMF5xhc");

    LinodeInstance linodeInstance = api.create(body);
    logger.info("Created linode instance: {}", linodeInstance);

    LinodeInstanceDbo dbo = instanceRepository.saveLinodeInstanceToDb(linodeInstance);

    return null;
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

  @Override
  public void onApplicationEvent(ApplicationStartedEvent event) {
//    CreateInstanceResponse instance = createInstance(null);
//    deleteInstance(52342940L);
  }

}
