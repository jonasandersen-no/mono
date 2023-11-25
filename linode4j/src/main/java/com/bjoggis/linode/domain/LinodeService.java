package com.bjoggis.linode.domain;

import com.bjoggis.linode.adapter.out.LinodeInterface;
import com.bjoggis.linode.model.LinodeInstance;
import com.bjoggis.linode.model.instance.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinodeService {

  private final LinodeInterface api;
  private final Logger logger = LoggerFactory.getLogger(LinodeService.class);

  public LinodeService(LinodeInterface linodeInterface) {
    this.api = linodeInterface;

//    Page<LinodeInstance> list = api.list(1, 25);
//    System.out.println(list);
    String types = api.types();
    logger.info(types);
  }

}
