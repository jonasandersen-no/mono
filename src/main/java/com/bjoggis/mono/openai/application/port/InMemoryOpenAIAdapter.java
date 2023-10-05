package com.bjoggis.mono.openai.application.port;

public class InMemoryOpenAIAdapter implements OpenAIAdapter {

  private String lastUsername;

  @Override
  public String sendMessage(String message, String username) {
    lastUsername = username;
    return new StringBuilder(message).reverse().toString();
  }

  public String getLastUsername() {
    return lastUsername;
  }
}
