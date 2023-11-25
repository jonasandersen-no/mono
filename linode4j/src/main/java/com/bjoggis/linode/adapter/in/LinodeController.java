package com.bjoggis.linode.adapter.in;


import com.bjoggis.linode.domain.LinodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@ResponseBody
@RequestMapping("/v1/linode")
@RestController
public class LinodeController {

  private final LinodeService service;

  public LinodeController(LinodeService service) {
    this.service = service;
  }

  @PostMapping("/instance/create")
  CreateInstanceResponse createInstance() {
    return service.createInstance();
  }

  @GetMapping("/instance/{linodeId}")
  ResponseEntity<GetInstanceResponse> getInstance(@PathVariable Long linodeId) {
    GetInstanceResponse response = service.getLinodeInstanceById(linodeId);

    if (response == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/instance/{linodeId}")
  void deleteInstance(@PathVariable Long linodeId) {
    service.deleteInstance(linodeId);
  }

}
