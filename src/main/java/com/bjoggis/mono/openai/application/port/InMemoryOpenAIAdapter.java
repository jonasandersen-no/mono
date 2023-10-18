package com.bjoggis.mono.openai.application.port;

import com.bjoggis.mono.openai.domain.ChatMessage;

public class InMemoryOpenAIAdapter implements OpenAIAdapter {

  private String lastUsername;

  @Override
  public ChatMessage sendMessage(String message, String username) {
    lastUsername = username;
    ChatMessage chatMessage = new ChatMessage();
    chatMessage.setMessage(new StringBuilder(message).reverse().toString());
    return chatMessage;
  }

  public String getLastUsername() {
    return lastUsername;
  }
}
