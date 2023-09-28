package com.bjoggis.mono.openai.adapter.in.web;

import com.bjoggis.mono.openai.application.ChatThreadService;
import com.bjoggis.mono.openai.application.port.ChatThreadRepository;
import com.bjoggis.mono.openai.application.port.InMemoryChatThreadRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestWebThreadControllerConfiguration {

  @Bean
  ChatThreadRepository chatThreadRepository() {
    return new InMemoryChatThreadRepository();
  }

  @Bean
  ChatThreadService threadService() {
    return new ChatThreadService(chatThreadRepository());
  }
}
