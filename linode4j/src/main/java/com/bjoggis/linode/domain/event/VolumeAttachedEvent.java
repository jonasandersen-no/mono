package com.bjoggis.linode.domain.event;

public record VolumeAttachedEvent(Long linodeId, Long volumeId) {

}
