package com.bjoggis.mono.openai.domain;

public class ChatMessage {

  private ChatMessageId id;
  private String message;

  public ChatMessage() {
  }

  public ChatMessage(String message) {
    this.message = message;
  }

  public ChatMessageId getId() {
    return id;
  }

  public void setId(ChatMessageId id) {
    this.id = id;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
