package com.bjoggis.linode.adapter.in;

import com.bjoggis.linode.adapter.out.database.LinodeInstanceDbo;

public record CreateInstanceResponse(Long linodeId, String label, String ip, String status) {

  public static CreateInstanceResponse fromDbo(LinodeInstanceDbo dbo) {
    return new CreateInstanceResponse(dbo.getLinodeId(), dbo.getLabel(), dbo.getIpv4(),
        dbo.getStatus());
  }
}
