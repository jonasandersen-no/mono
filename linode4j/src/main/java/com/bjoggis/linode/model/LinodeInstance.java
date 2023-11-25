package com.bjoggis.linode.model;

import com.bjoggis.linode.model.instance.Alerts;
import com.bjoggis.linode.model.instance.Backups;
import com.bjoggis.linode.model.instance.Specs;
import java.time.LocalDateTime;
import java.util.List;

public record LinodeInstance(
    long id,
    String label,
    String group,
    String status,
    LocalDateTime created,
    LocalDateTime updated,
    String type,
    List<String> ipv4,
    String ipv6,
    String image,
    String region,
    Specs specs,
    Alerts alerts,
    Backups backups,
    String hypervisor,
    boolean watchdogEnabled,
    List<String> tags,
    String hostUuid,
    boolean hasUserData
) {

  public LinodeInstance {
    if (id <= 0) {
      throw new IllegalArgumentException("Id must be positive");
    }
    if (ipv4 == null || ipv4.isEmpty()) {
      throw new IllegalArgumentException("At least one IPv4 address is required");
    }
    if (ipv6 == null || ipv6.isEmpty()) {
      throw new IllegalArgumentException("IPv6 address is required");
    }
  }
}
