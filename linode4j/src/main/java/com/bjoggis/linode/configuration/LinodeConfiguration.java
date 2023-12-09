package com.bjoggis.linode.configuration;

import com.bjoggis.linode.adapter.in.LinodeController;
import com.bjoggis.linode.adapter.out.api.LinodeInterface;
import com.bjoggis.linode.adapter.out.database.LinodeInstanceDboRepository;
import com.bjoggis.linode.adapter.out.database.LinodeRepository;
import com.bjoggis.linode.adapter.out.database.LinodeRepositoryImpl;
import com.bjoggis.linode.configuration.properties.LinodeProperties;
import com.bjoggis.linode.domain.LinodeService;
import com.bjoggis.linode.domain.event.LinodeEventPublisher;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@AutoConfiguration
@ComponentScan(basePackages = {"com.bjoggis.linode"})
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
  LinodeRepository linodeRepository(LinodeInstanceDboRepository instanceRepository) {
    return new LinodeRepositoryImpl(instanceRepository);
  }

  @Bean
  LinodeService linodeService(LinodeProperties properties,
      LinodeInterface linodeInterface,
      LinodeRepository linodeRepository,
      LinodeEventPublisher eventPublisher) {
    return new LinodeService(properties, linodeInterface, linodeRepository, eventPublisher);
  }

  @Bean
  LinodeController linodeController(LinodeService service) {
    return new LinodeController(service);
  }

  @Bean
  LinodeEventPublisher linodeEventPublisher(RabbitTemplate amqpTemplate) {
    return new LinodeEventPublisher(amqpTemplate);
  }

  @Bean
  RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
    return rabbitTemplate;
  }

  @Bean
  public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

}
