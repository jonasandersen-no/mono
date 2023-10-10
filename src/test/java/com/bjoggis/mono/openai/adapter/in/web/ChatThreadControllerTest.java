package com.bjoggis.mono.openai.adapter.in.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bjoggis.mono.openai.application.AIAccountService;
import com.bjoggis.mono.openai.application.TestThreadServiceBuilder;
import com.bjoggis.mono.openai.application.port.ChatThreadRepository;
import com.bjoggis.mono.openai.application.port.DummyAccountAdapter;
import com.bjoggis.mono.openai.domain.AccountId;
import com.bjoggis.mono.openai.domain.ChatThread;
import com.bjoggis.mono.user.adapter.in.TestPrincipal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

public class ChatThreadControllerTest {

  @Test
  void returnsThreadResponseWhenCreatingThread() {
    TestThreadServiceBuilder builder = new TestThreadServiceBuilder();

    AIAccountService aiAccountService = new AIAccountService(new DummyAccountAdapter());

    ChatThreadController chatThreadController = new ChatThreadController(builder.build(),
        aiAccountService);
    ResponseEntity<ChatThreadResponse> response = chatThreadController.createThread(
        new TestPrincipal("test"));

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
    AIAccountService aiAccountService = new AIAccountService(new DummyAccountAdapter());

    builder.save(chatThread);

    ChatThread saved = builder.getLastThread();

    ChatThreadController chatThreadController = new ChatThreadController(builder.build(),
        aiAccountService);

    ResponseEntity<ChatThreadResponse> findThreadResponse = chatThreadController.findThread(
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
    AIAccountService aiAccountService = new AIAccountService(new DummyAccountAdapter());
    ChatThreadController chatThreadController = new ChatThreadController(builder.build(),
        aiAccountService);

    ResponseEntity<?> responseEntity = chatThreadController.deleteThread(
        saved.getChatThreadId().chatThreadId(), new TestPrincipal("test"));

    Optional<ChatThread> notFound = threadRepository.findById(saved.getChatThreadId());

    assertNotNull(notFound);
    assertTrue(notFound.isEmpty());

    assertNull(responseEntity.getBody());
  }

  @Test
  void findAllByPrincipalReturnsTwoThreads() {
    ChatThread chatThread = new ChatThread();
    chatThread.setAccountId(AccountId.of(1L));
    ChatThread chatThread2 = new ChatThread();
    chatThread2.setAccountId(AccountId.of(1L));

    TestThreadServiceBuilder builder = new TestThreadServiceBuilder();
    builder.save(chatThread);
    builder.save(chatThread2);
    AIAccountService aiAccountService = new AIAccountService(new DummyAccountAdapter());
    ChatThreadController chatThreadController = new ChatThreadController(builder.build(),
        aiAccountService);

    ResponseEntity<List<ChatThreadResponse>> responseEntity = chatThreadController.findAllThreadsByPrincipal(
        new TestPrincipal("test"));

    List<ChatThreadResponse> chatThreads = responseEntity.getBody();

    assertNotNull(chatThreads);
    assertEquals(2, chatThreads.size());
  }
}
