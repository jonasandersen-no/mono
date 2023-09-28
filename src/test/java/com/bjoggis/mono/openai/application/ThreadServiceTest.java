package com.bjoggis.mono.openai.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.bjoggis.mono.openai.application.port.ChatThreadRepository;
import com.bjoggis.mono.openai.domain.AccountId;
import com.bjoggis.mono.openai.domain.ChatThread;
import org.junit.jupiter.api.Test;

class ThreadServiceTest {

  @Test
  void returnsNewChatThreadForAccountWhenCreateThreadIsCalled() {

    TestThreadServiceBuilder builder = new TestThreadServiceBuilder();
    ChatThreadRepository chatThreadRepository = builder.getChatThreadRepository();
    ThreadService threadService = builder.build();

    ChatThread thread = threadService.createThread(AccountId.of(1L));

    assertNotNull(thread);
    assertEquals(AccountId.of(1L), thread.getAccountId());

    ChatThread foundChatThread = chatThreadRepository.findById(thread.getChatThreadId());
    assertNotNull(foundChatThread);
    assertEquals(thread.getChatThreadId(), foundChatThread.getChatThreadId());
  }
}