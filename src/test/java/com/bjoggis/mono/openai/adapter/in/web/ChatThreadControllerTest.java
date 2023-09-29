package com.bjoggis.mono.openai.adapter.in.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.bjoggis.mono.openai.application.ChatThreadService;
import com.bjoggis.mono.openai.application.port.InMemoryChatThreadRepository;
import com.bjoggis.mono.openai.domain.AccountId;
import com.bjoggis.mono.openai.domain.ChatThread;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

public class ChatThreadControllerTest {

  @Test
  void returnsThreadResponseWhenCreatingThread() {
    InMemoryChatThreadRepository threadRepository = new InMemoryChatThreadRepository();
    ChatThreadService chatThreadService = new ChatThreadService(threadRepository);

    ChatThreadController chatThreadController = new ChatThreadController(chatThreadService);
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

    InMemoryChatThreadRepository threadRepository = new InMemoryChatThreadRepository();
    ChatThread saved = threadRepository.save(chatThread);
    ChatThreadService chatThreadService = new ChatThreadService(threadRepository);
    ChatThreadController chatThreadController = new ChatThreadController(chatThreadService);

    ResponseEntity<ChatThreadResponse> findThreadResponse = chatThreadController.FindThread(
        saved.getChatThreadId().chatThreadId());

    ChatThreadResponse foundThread = findThreadResponse.getBody();

    assertNotNull(foundThread);
    assertEquals(1L, foundThread.accountId());
    assertEquals(saved.getChatThreadId().chatThreadId(), foundThread.chatThreadId());
  }
}
