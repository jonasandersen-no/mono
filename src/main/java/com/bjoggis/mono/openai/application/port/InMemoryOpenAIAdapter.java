package com.bjoggis.mono.openai.application.port;

public class InMemoryOpenAIAdapter implements OpenAIAdapter {

  @Override
  public String sendMessage(String message) {
    return new StringBuilder(message).reverse().toString();
  }
}
