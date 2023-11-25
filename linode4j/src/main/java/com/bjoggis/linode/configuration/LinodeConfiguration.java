package com.bjoggis.linode.configuration;

import com.bjoggis.linode.adapter.out.LinodeInterface;
import com.bjoggis.linode.domain.LinodeService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@AutoConfiguration
public class LinodeConfiguration {

  @Bean
  LinodeInterface linodeInterface(LinodeProperties properties) {
    RestClient restClient = RestClient.builder()
        .baseUrl("https://api.linode.com")
        .requestInitializer(request -> request.getHeaders().setBearerAuth(properties.token()))
        .build();

    RestClientAdapter adapter = RestClientAdapter.create(restClient);
    HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

    return factory.createClient(LinodeInterface.class);
  }

  @Bean
  LinodeService linodeService(LinodeInterface linodeInterface) {
    return new LinodeService(linodeInterface);
  }
}
