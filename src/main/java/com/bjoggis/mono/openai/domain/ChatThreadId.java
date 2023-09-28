package com.bjoggis.mono.openai.domain;

public record ChatThreadId(String chatThreadId) {

  public static ChatThreadId of(String chatThreadId) {
    return new ChatThreadId(chatThreadId);
  }
}
