package com.bjoggis.mono.openai.adapter.out.openai;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "openai")
@Validated
public record OpenaiProperties(@NotNull String token) {

}
