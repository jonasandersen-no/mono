package com.bjoggis.linode.domain.event;

import com.bjoggis.linode.adapter.out.database.LinodeRepository;
import com.bjoggis.linode.domain.LinodeService;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class LinodeEventListener implements LinodeEvents {

  private final Logger logger = LoggerFactory.getLogger(LinodeEventListener.class);

  private final LinodeRepository linodeRepository;
  private final LinodeService linodeService;

  public LinodeEventListener(LinodeRepository linodeRepository, LinodeService linodeService) {
    this.linodeRepository = linodeRepository;
    this.linodeService = linodeService;
  }

  @Override
  @Transactional
  @RabbitListener(queuesToDeclare = @Queue(name = "linode.instance.created", durable = "true"))
  public void onInstanceCreated(@Payload InstanceCreatedEvent event) {
//    logger.info("Instance created event: {}", event);
//
//    Long linodeId = event.linodeId();
//
//    Optional<LinodeInstanceDbo> dboOptional = linodeRepository.findByLinodeId(linodeId);
//
//    if (dboOptional.isPresent()) {
//      statusFn.accept(linodeId);
//    }
  }

  @Override
  @RabbitListener(queuesToDeclare = @Queue(name = "linode.instance.running", durable = "true"))
  public void onInstanceRunning(InstanceRunningEvent event) {
    logger.info("Instance running event: {}", event);
    try {
      linodeService.attachVolume(event.linodeId());
    } catch (InterruptedException e) {
      logger.error("Error while attaching volume: {}", e.getMessage());
    }
  }

  @Override
  @RabbitListener(queuesToDeclare = @Queue(name = "linode.volume.attached", durable = "true"))
  public void onVolumeAttached(VolumeAttachedEvent event) {
    logger.info("Volume attached event: {}", event);

    logger.info("Waiting 30 seconds for the server to boot up...");
    try {
      Thread.sleep(Duration.ofSeconds(30));
    } catch (InterruptedException e) {
      logger.error("Error while waiting for server to boot up: {}", e.getMessage());
    }

    linodeService.setupMinecraftServer(event.linodeId());
  }
}
