package com.bjoggis.mono.openai.adapter.in.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bjoggis.mono.openai.application.TestThreadServiceBuilder;
import com.bjoggis.mono.openai.application.port.ChatThreadRepository;
import com.bjoggis.mono.openai.domain.AccountId;
import com.bjoggis.mono.openai.domain.ChatThread;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

public class ChatThreadControllerTest {

  @Test
  void returnsThreadResponseWhenCreatingThread() {
    TestThreadServiceBuilder builder = new TestThreadServiceBuilder();

    ChatThreadController chatThreadController = new ChatThreadController(builder.build());
    ResponseEntity<ChatThreadResponse> response = chatThreadController.createThread(
        new CreateThreadRequest(1L));

    ChatThreadResponse thread = response.getBody();

    assertNotNull(thread);
    assertEquals(1L, thread.accountId());
    assertNotNull(thread.chatThreadId());
  }

  @Test
  void returnsThreadResponseWhenFindingThread() {
    ChatThread chatThread = new ChatThread();
    chatThread.setAccountId(AccountId.of(1L));

    TestThreadServiceBuilder builder = new TestThreadServiceBuilder();

    builder.save(chatThread);

    ChatThread saved = builder.getLastThread();

    ChatThreadController chatThreadController = new ChatThreadController(builder.build());

    ResponseEntity<ChatThreadResponse> findThreadResponse = chatThreadController.FindThread(
        saved.getChatThreadId().chatThreadId());

    ChatThreadResponse foundThread = findThreadResponse.getBody();

    assertNotNull(foundThread);
    assertEquals(1L, foundThread.accountId());
    assertEquals(saved.getChatThreadId().chatThreadId(), foundThread.chatThreadId());
  }

  @Test
  void deletesThreadWhenDeletingThread() {
    ChatThread chatThread = new ChatThread();
    chatThread.setAccountId(AccountId.of(1L));

    TestThreadServiceBuilder builder = new TestThreadServiceBuilder();
    builder.save(chatThread);
    ChatThreadRepository threadRepository = builder.getChatThreadRepository();
    ChatThread saved = builder.getLastThread();
    ChatThreadController chatThreadController = new ChatThreadController(builder.build());

    ResponseEntity<?> responseEntity = chatThreadController.deleteThread(
        saved.getChatThreadId().chatThreadId());

    Optional<ChatThread> notFound = threadRepository.findById(saved.getChatThreadId());

    assertNotNull(notFound);
    assertTrue(notFound.isEmpty());

    assertNull(responseEntity.getBody());
  }
}
