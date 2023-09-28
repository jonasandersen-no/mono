package com.bjoggis.mono.openai.domain;

import java.time.LocalDateTime;

public class Message {

  private MessageId messageId;
  private Sender sender;
  private byte[] message;
  private LocalDateTime created;
  private ChatThreadId chatThreadId;

  public MessageId getMessageId() {
    return messageId;
  }

  public void setMessageId(MessageId messageId) {
    this.messageId = messageId;
  }

  public Sender getSender() {
    return sender;
  }

  public void setSender(Sender sender) {
    this.sender = sender;
  }

  public byte[] getMessage() {
    return message;
  }

  public void setMessage(byte[] message) {
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
