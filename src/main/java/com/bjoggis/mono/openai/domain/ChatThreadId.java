package com.bjoggis.mono.openai.domain;

public record ChatThreadId(Long chatThreadId) {

  public static ChatThreadId of(Long chatThreadId) {
    return new ChatThreadId(chatThreadId);
  }
}
