package com.bjoggis.linode.domain.event;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class LinodeEventPublisher implements LinodeEvents {

  private final RabbitTemplate rabbitTemplate;

  public LinodeEventPublisher(RabbitTemplate amqpTemplate) {
    this.rabbitTemplate = amqpTemplate;
  }

  @Override
  public void onInstanceCreated(@NotNull @Valid InstanceCreatedEvent event) {
    rabbitTemplate.convertAndSend("linode.instance.created", event);
  }

  @Override
  public void onInstanceRunning(InstanceRunningEvent event) {
    rabbitTemplate.convertAndSend("linode.instance.running", event);
  }

  @Override
  public void onVolumeAttached(VolumeAttachedEvent event) {
    rabbitTemplate.convertAndSend("linode.volume.attached", event);
  }
}
