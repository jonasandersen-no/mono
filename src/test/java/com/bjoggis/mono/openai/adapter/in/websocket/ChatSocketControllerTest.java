package com.bjoggis.mono.openai.adapter.in.websocket;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bjoggis.mono.openai.application.TestThreadServiceBuilder;
import com.bjoggis.mono.openai.application.port.ChatThreadRepository;
import com.bjoggis.mono.openai.domain.AccountId;
import com.bjoggis.mono.openai.domain.ChatThread;
import com.bjoggis.mono.openai.domain.ChatThreadId;
import com.bjoggis.mono.user.adapter.in.TestPrincipal;
import org.junit.jupiter.api.Test;

class ChatSocketControllerTest {

  @Test
  void messageSentWillBeStoredInRepository() {
    ChatThread chatThread = new ChatThread();
    chatThread.setAccountId(AccountId.of(1L));
    TestThreadServiceBuilder builder = new TestThreadServiceBuilder().save(chatThread);
    ChatThreadId lastThreadId = builder.getLastThreadId();
    ChatThreadRepository repository = builder.getChatThreadRepository();
    ChatSocketController controller = new ChatSocketController(builder.build());

    controller.message(new ChatMessageRequest(lastThreadId.chatThreadId(), "Hello"),
        new TestPrincipal("test"));

    ChatThread thread = repository.findById(lastThreadId).get();

    assertEquals(1, thread.getMessages().size());

//    repository
  }
}