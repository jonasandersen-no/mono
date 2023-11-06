package com.bjoggis.mono.openai.adapter.out.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bjoggis.mono.openai.domain.ChatMessage;
import com.bjoggis.mono.openai.domain.ChatMessageId;
import org.junit.jupiter.api.Test;

class ChatMessageDboTest {

  @Test
  void testConvertsToDbo() {
    ChatMessage chatMessage = new ChatMessage();
    chatMessage.setId(ChatMessageId.of(1L));
    chatMessage.setMessage("Hello");

    ChatMessageDbo dbo = ChatMessageDbo.from(chatMessage);

    assertEquals(1L, dbo.getId());
    assertEquals("Hello", dbo.getMessage());
  }

  @Test
  void testConvertsFromDbo() {
    ChatMessageDbo dbo = new ChatMessageDbo();
    dbo.setId(1L);
    dbo.setMessage("Hello");

    ChatMessage chatMessage = dbo.asChatMessage();

    assertEquals(1L, chatMessage.getId().id());
    assertEquals("Hello", chatMessage.getMessage());
  }
}