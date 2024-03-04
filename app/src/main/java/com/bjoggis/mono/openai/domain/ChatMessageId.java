package com.bjoggis.mono.openai.domain;

public record ChatMessageId(Long id) {

  public static ChatMessageId of(Long id) {
    return new ChatMessageId(id);
  }
}
