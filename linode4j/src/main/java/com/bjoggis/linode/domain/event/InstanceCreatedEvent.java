package com.bjoggis.linode.domain.event;

import java.io.Serializable;
import java.util.Date;

public record InstanceCreatedEvent(Long linodeId, Date eventCreated) implements Serializable {

}
