package com.bjoggis.mono.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/demo")
@RestController
public class DemoController {

  @GetMapping
  public String get() {
    return "Hello World!";
  }
}
