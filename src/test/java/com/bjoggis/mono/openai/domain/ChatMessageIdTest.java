package com.bjoggis.mono.openai.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ChatMessageIdTest {

  @Test
  void testChatMessageIdOfReturnsInstance() {
    ChatMessageId chatMessageId = ChatMessageId.of(1L);

    assertEquals(1L, chatMessageId.id());
  }
}