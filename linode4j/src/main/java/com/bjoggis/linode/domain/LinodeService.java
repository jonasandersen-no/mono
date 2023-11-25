package com.bjoggis.linode.domain;

import com.bjoggis.linode.adapter.in.CreateInstanceRequest;
import com.bjoggis.linode.adapter.in.CreateInstanceResponse;
import com.bjoggis.linode.adapter.out.api.CreateInstanceRequestBody;
import com.bjoggis.linode.adapter.out.api.LinodeInterface;
import com.bjoggis.linode.model.InstanceType;
import com.bjoggis.linode.model.LinodeInstance;
import com.bjoggis.linode.model.Page;
import com.bjoggis.linode.model.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

public class LinodeService {

  private final LinodeInterface api;
  private final Logger logger = LoggerFactory.getLogger(LinodeService.class);

  public LinodeService(LinodeInterface linodeInterface) {
    this.api = linodeInterface;

//    Page<InstanceType> types = api.types();
//    logger.info("Linode instance types: {}", types);
//
//    CreateInstanceRequestBody body = new CreateInstanceRequestBody();
//    body.setRegion("se-sto");
//    body.setType("g6-nanode-1");
//    LinodeInstance linodeInstance = api.create(body);
//    logger.info("Created linode instance: {}", linodeInstance);
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

    return null;
  }
}
