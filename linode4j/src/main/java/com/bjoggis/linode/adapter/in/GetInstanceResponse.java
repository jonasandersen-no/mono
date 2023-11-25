package com.bjoggis.linode.adapter.in;

import com.bjoggis.linode.adapter.out.database.LinodeInstanceDbo;

public record GetInstanceResponse(Long linodeId, String label, String ip, String status) {

  public static GetInstanceResponse fromDbo(LinodeInstanceDbo dbo) {
    return new GetInstanceResponse(dbo.getLinodeId(), dbo.getLabel(), dbo.getIpv4(),
        dbo.getStatus());
  }
}
