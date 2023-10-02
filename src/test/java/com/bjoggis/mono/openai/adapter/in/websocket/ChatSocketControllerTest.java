package com.bjoggis.mono.openai.adapter.in.websocket;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bjoggis.mono.openai.application.TestThreadServiceBuilder;
import org.junit.jupiter.api.Test;

class ChatSocketControllerTest {

  @Test
  void sendingAMessageRequestWillSendAMessageResponseUsingWebsocket() {
    DummyWebSocketSender webSocketSender = new DummyWebSocketSender();
    TestThreadServiceBuilder builder = new TestThreadServiceBuilder();

    ChatSocketController controller = new ChatSocketController(webSocketSender, builder.build());

    controller.message(new ChatMessageRequest(1L, "Hello"));

    String lastMessage = webSocketSender.getLastMessage();

    assertEquals("olleH", lastMessage);
  }
}