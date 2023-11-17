package com.bjoggis.kassalapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@AutoConfiguration
@EnableConfigurationProperties(KassalappProperties.class)
class KassalappAutoConfiguration {

  private final Logger logger = LoggerFactory.getLogger(KassalappAutoConfiguration.class);

  @Bean
  KassalappApi kassalappApi(KassalappProperties properties) {
    RestClient restClient = RestClient.builder().baseUrl(properties.baseUrl())
        .defaultHeader("Authorization", "Bearer " + properties.token())
        .build();

    RestClientAdapter adapter = RestClientAdapter.create(restClient);
    HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

    logger.info("Creating KassalappApi using base url: {}", properties.baseUrl());
    return factory.createClient(KassalappApi.class);
  }

  @Bean
  KassalappService kassalappService(KassalappApi kassalappApi) {
    return new KassalappServiceImpl(kassalappApi);
  }

  @Bean
  KassalappController kassalappController(KassalappService service) {
    return new KassalappController(service);
  }
}
