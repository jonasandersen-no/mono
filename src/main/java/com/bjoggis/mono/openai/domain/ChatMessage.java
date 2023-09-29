package com.bjoggis.mono.openai.domain;

import java.time.LocalDateTime;

public class ChatMessage {

  private ChatMessageId chatMessageId;
  private Sender sender;
  private String message;
  private LocalDateTime created;
  private ChatThreadId chatThreadId;

  public ChatMessageId getMessageId() {
    return chatMessageId;
  }

  public void setMessageId(ChatMessageId chatMessageId) {
    this.chatMessageId = chatMessageId;
  }

  public Sender getSender() {
    return sender;
  }

  public void setSender(Sender sender) {
    this.sender = sender;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public void setCreated(LocalDateTime created) {
    this.created = created;
  }

  public ChatThreadId getThreadId() {
    return chatThreadId;
  }

  public void setThreadId(ChatThreadId chatThreadId) {
    this.chatThreadId = chatThreadId;
  }
}
