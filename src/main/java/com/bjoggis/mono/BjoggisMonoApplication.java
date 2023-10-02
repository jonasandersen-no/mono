package com.bjoggis.mono;

import com.bjoggis.mono.openai.adapter.out.openai.OpenaiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(OpenaiProperties.class)
public class BjoggisMonoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BjoggisMonoApplication.class, args);
	}

}
