package com.bjoggis.kassalapp;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@ResponseBody
@RequestMapping("/v1/kassalapp")
@RestController
public class KassalappController {

  private final KassalappService service;

  public KassalappController(KassalappService service) {
    this.service = service;
  }

  @GetMapping("/products/ean/{ean}")
  public String getProductByEan(@PathVariable String ean) {
    return service.getProductByEan(ean);
  }
}
