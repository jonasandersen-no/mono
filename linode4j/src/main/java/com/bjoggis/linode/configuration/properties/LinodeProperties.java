package com.bjoggis.linode.configuration.properties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "linode")
@Validated
public record LinodeProperties(@NotNull String token, @Valid Instance instance) {


  @Validated
  public record Instance(@NotNull String password) {

  }
}
