package com.bjoggis.mono;

import com.bjoggis.linode.configuration.properties.LinodeProperties;
import com.bjoggis.mono.openai.adapter.out.openai.OpenaiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties({OpenaiProperties.class, LinodeProperties.class})
@EntityScan(basePackages = {"com.bjoggis.mono", "com.bjoggis.linode"})
@EnableJpaRepositories(basePackages = {"com.bjoggis.mono", "com.bjoggis.linode"})
@EnableScheduling
public class BjoggisMonoApplication {

  public static void main(String[] args) {
    SpringApplication.run(BjoggisMonoApplication.class, args);
  }

}
