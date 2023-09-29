package com.bjoggis.mono.openai.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.bjoggis.mono.openai.application.port.ChatThreadRepository;
import com.bjoggis.mono.openai.domain.AccountId;
import com.bjoggis.mono.openai.domain.ChatThread;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class ChatThreadServiceTest {

  @Test
  void returnsNewChatThreadForAccountWhenCreateThreadIsCalled() {
    TestThreadServiceBuilder builder = new TestThreadServiceBuilder();
    ChatThreadRepository chatThreadRepository = builder.getChatThreadRepository();
    ChatThreadService chatThreadService = builder.build();

    ChatThread thread = chatThreadService.createThread(AccountId.of(1L));

    assertNotNull(thread);
    assertEquals(AccountId.of(1L), thread.getAccountId());

    ChatThread foundChatThread = chatThreadRepository.findById(thread.getChatThreadId()).get();
    assertNotNull(foundChatThread);
    assertEquals(thread.getChatThreadId(), foundChatThread.getChatThreadId());
  }

  @Test
  void returnsChatThreadWhenFindByIdIsCalled() {
    ChatThread chatThread = new ChatThread();

    TestThreadServiceBuilder builder = new TestThreadServiceBuilder()
        .save(chatThread);
    ChatThreadService chatThreadService = builder.build();

    Optional<ChatThread> foundChatThread = chatThreadService.findById(builder.getLastThreadId());

    assertEquals(builder.getLastThreadId(), foundChatThread.get().getChatThreadId());
  }

  @Test
  void deletesChatThreadWhenChatThreadIsCalled() {
    ChatThread chatThread = new ChatThread();

    TestThreadServiceBuilder builder = new TestThreadServiceBuilder()
        .save(chatThread);
    ChatThreadService chatThreadService = builder.build();

    chatThreadService.deleteThreadById(builder.getLastThreadId());

    Optional<ChatThread> foundChatThread = chatThreadService.findById(builder.getLastThreadId());

    assertEquals(Optional.empty(), foundChatThread);
  }
}