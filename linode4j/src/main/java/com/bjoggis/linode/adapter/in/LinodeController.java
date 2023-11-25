package com.bjoggis.linode.adapter.in;


import com.bjoggis.linode.domain.LinodeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/v1/linode")
@ResponseBody
public class LinodeController {

  private final LinodeService service;

  public LinodeController(LinodeService service) {
    this.service = service;
  }

  @PostMapping("/instance/create")
  CreateInstanceResponse createInstance(CreateInstanceRequest request) {
    return service.createInstance(request);
  }

}
