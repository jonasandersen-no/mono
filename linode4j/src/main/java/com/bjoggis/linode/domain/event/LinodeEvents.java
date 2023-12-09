package com.bjoggis.linode.domain.event;

public interface LinodeEvents {

  void onInstanceCreated(InstanceCreatedEvent event);

  void onInstanceRunning(InstanceRunningEvent event);

  void onVolumeAttached(VolumeAttachedEvent event);
}
