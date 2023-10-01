package com.bjoggis.mono.openai.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

  @Test
  void returnsTrueWhenAccountIdIsEqualToAccountIdInThread() {
    ChatThread chatThread = new ChatThread();
    chatThread.setAccountId(AccountId.of(1L));

    TestThreadServiceBuilder builder = new TestThreadServiceBuilder()
        .save(chatThread);

    ChatThreadService service = builder.build();

    boolean equal = service.validThread(builder.getLastThreadId(), AccountId.of(1L));

    assertTrue(equal);
  }

  @Test
  void returnsFalseWhenAccountIdIsNotEqualToAccountIdInThread() {
    ChatThread chatThread = new ChatThread();
    chatThread.setAccountId(AccountId.of(1L));

    TestThreadServiceBuilder builder = new TestThreadServiceBuilder()
        .save(chatThread);

    ChatThreadService service = builder.build();

    boolean equal = service.validThread(builder.getLastThreadId(), AccountId.of(2L));
    assertFalse(equal);
  }

  @Test
  void addMessageToChatThreadWhenSendingAsOwner() {
    ChatThread chatThread = new ChatThread();
    chatThread.setAccountId(AccountId.of(1L));

    TestThreadServiceBuilder builder = new TestThreadServiceBuilder()
        .save(chatThread);

    ChatThreadService service = builder.build();

    service.addMessage(builder.getLastThreadId(), AccountId.of(1L), "Hello");

    ChatThread foundChatThread = builder.getChatThreadRepository()
        .findById(builder.getLastThreadId()).get();

    assertEquals(1, foundChatThread.getMessages().size());
  }

  @Test
  void addMessageToChatThreadWhenNotSendingAsOwnerThrows() {
    ChatThread chatThread = new ChatThread();
    chatThread.setAccountId(AccountId.of(1L));

    TestThreadServiceBuilder builder = new TestThreadServiceBuilder()
        .save(chatThread);

    ChatThreadService service = builder.build();

    assertThrows(IllegalArgumentException.class, () -> {
      service.addMessage(builder.getLastThreadId(), AccountId.of(2L), "Hello");
    });
  }
}