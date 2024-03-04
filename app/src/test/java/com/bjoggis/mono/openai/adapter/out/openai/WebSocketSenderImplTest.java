package com.bjoggis.mono.openai.adapter.out.openai;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.bjoggis.mono.openai.adapter.in.websocket.ChatMessageResponse;
import com.bjoggis.mono.openai.application.port.WebSocketSender;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.SimpMessagingTemplate;

class WebSocketSenderImplTest {

  @Test
  void sendWillConvertAndSendToDestination() {
    SimpMessagingTemplate simpMessagingTemplate = mock(SimpMessagingTemplate.class);

    WebSocketSender webSocketSender = new WebSocketSenderImpl(simpMessagingTemplate);

    webSocketSender.send(1L, "message", "user1");

    verify(simpMessagingTemplate).convertAndSendToUser("user1", "/queue/response",
        new ChatMessageResponse(1L, "message"));

  }
}