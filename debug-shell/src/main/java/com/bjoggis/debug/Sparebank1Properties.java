package com.bjoggis.debug;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sparebank1")
public record Sparebank1Properties(String refreshToken, String clientId, String clientSecret) {

}
