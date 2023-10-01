package com.bjoggis.mono.openai.adapter.out.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bjoggis.mono.openai.domain.AccountId;
import com.bjoggis.mono.openai.domain.ChatThread;
import com.bjoggis.mono.openai.domain.ChatThreadId;
import java.util.List;
import org.junit.jupiter.api.Test;

class ChatThreadDboTest {

  @Test
  void canConvertFromDto() {
    ChatThread chatThread = new ChatThread();
    chatThread.setChatThreadId(ChatThreadId.of(1L));
    chatThread.setAccountId(AccountId.of(2L));
    chatThread.setMessages(List.of("Hello"));

    ChatThreadDbo dbo = ChatThreadDbo.from(chatThread);

    assertEquals(1L, dbo.getId());
    assertEquals(2L, dbo.getAccountId());
    assertEquals(List.of("Hello"), dbo.getMessages());
  }

  @Test
  void canConvertToDto() {
    ChatThreadDbo dbo = new ChatThreadDbo();
    dbo.setId(1L);
    dbo.setAccountId(2L);
    dbo.setMessages(List.of("Hello"));

    ChatThread chatThread = dbo.asChatThread();

    assertEquals(1L, chatThread.getChatThreadId().chatThreadId());
    assertEquals(2L, chatThread.getAccountId().id());
    assertEquals(List.of("Hello"), chatThread.getMessages());
  }
}