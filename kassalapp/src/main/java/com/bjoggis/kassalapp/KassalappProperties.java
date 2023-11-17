package com.bjoggis.kassalapp;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "kassalapp")
@Validated
public record KassalappProperties(@NotNull String token, @NotNull @URL String baseUrl) {

}
