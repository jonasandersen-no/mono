package com.bjoggis.linode.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "linode")
@Validated
public record LinodeProperties(@NotNull String token) {

}
