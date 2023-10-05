package com.bjoggis.mono.openai.adapter.out.openai;

import com.bjoggis.mono.openai.adapter.in.websocket.ChatMessageResponse;
import com.bjoggis.mono.openai.application.port.WebSocketSender;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebSocketSenderImpl implements WebSocketSender {

  private final SimpMessagingTemplate simpMessagingTemplate;

  public WebSocketSenderImpl(SimpMessagingTemplate simpMessagingTemplate) {
    this.simpMessagingTemplate = simpMessagingTemplate;
  }

  @Override
  public void send(Long threadId, String message) {
    simpMessagingTemplate.convertAndSendToUser("Bjoggis", "/queue/response",
        new ChatMessageResponse(threadId, message));
  }
}
