package com.bjoggis.mono.openai.adapter.in.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.bjoggis.mono.openai.application.ChatThreadService;
import com.bjoggis.mono.openai.application.port.InMemoryChatThreadRepository;
import org.junit.jupiter.api.Test;

public class ThreadControllerTest {

  @Test
  void returnsThreadResponseWhen() {
    InMemoryChatThreadRepository threadRepository = new InMemoryChatThreadRepository();
    ChatThreadService chatThreadService = new ChatThreadService(threadRepository);

    ThreadController threadController = new ThreadController(chatThreadService);
    CreateThreadResponse response = threadController.createThread(new CreateThreadRequest(1L));

    assertNotNull(response);
    assertEquals(1L, response.accountId());
    assertNotNull(response.chatThreadId());
  }
}
