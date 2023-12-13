package com.bjoggis.debug;

import com.bjoggis.debug.command.Sparebank1Commands;
import com.bjoggis.debug.command.Sparebank1Interface;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.command.annotation.EnableCommand;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@AutoConfiguration
@EnableConfigurationProperties(Sparebank1Properties.class)
@EnableCommand(Sparebank1Commands.class)
public class Sparebank1Configuration {

  @Bean
  Sparebank1Commands sparebank1Commands(Sparebank1Interface sparebank1Interface) {
    return new Sparebank1Commands(sparebank1Interface);
  }

  @Bean
  Sparebank1Interface sparebank1Interface(
      Sparebank1ClientHttpRequestInterceptor sparebank1ClientHttpRequestInterceptor) {
    RestClient restClient = RestClient.builder()
        .baseUrl("https://api.sparebank1.no/personal/banking/")
        .requestInterceptor(sparebank1ClientHttpRequestInterceptor)
        .build();

    RestClientAdapter adapter = RestClientAdapter.create(restClient);
    HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

    return factory.createClient(Sparebank1Interface.class);
  }

//  @Bean
//  CommandLineRunner commandLineRunner(Sparebank1Interface sparebank1Interface) {
//    return args -> {
//      System.out.println("Getting accounts");
//      System.out.printf("Accounts: %s%n", sparebank1Interface.getAccounts());
//    };
//  }

  @Bean
  Sparebank1ClientHttpRequestInterceptor sparebank1ClientHttpRequestInterceptor(
      Sparebank1Properties properties) {
    return new Sparebank1ClientHttpRequestInterceptor(properties);
  }
}
