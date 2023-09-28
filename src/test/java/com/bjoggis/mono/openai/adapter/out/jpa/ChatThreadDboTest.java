package com.bjoggis.mono.openai.adapter.out.jpa;

import static org.junit.jupiter.api.Assertions.*;

import com.bjoggis.mono.openai.domain.ChatThread;
import org.junit.jupiter.api.Test;

class ChatThreadDboTest {


  @Test
  void canConvertToDto() {
    ChatThreadDbo dbo = new ChatThreadDbo();
    dbo.setId(1L);

    ChatThread chatThread = dbo.asChatThread();

    assertEquals(1L, chatThread.getChatThreadId().chatThreadId());

  }
}