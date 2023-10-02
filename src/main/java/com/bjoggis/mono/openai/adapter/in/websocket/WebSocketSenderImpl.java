package com.bjoggis.mono.openai.adapter.in.websocket;

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
    simpMessagingTemplate.convertAndSend("/topic/chat", new ChatMessageResponse(threadId, message));
  }
}
