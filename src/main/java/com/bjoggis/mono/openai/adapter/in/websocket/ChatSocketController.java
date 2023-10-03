package com.bjoggis.mono.openai.adapter.in.websocket;

import com.bjoggis.mono.openai.application.ChatThreadService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ChatSocketController {

  private final ChatThreadService chatThreadService;

  public ChatSocketController(ChatThreadService chatThreadService) {
    this.chatThreadService = chatThreadService;
  }

  @MessageMapping("/hello")
  public void message(ChatMessageRequest message) {
    chatThreadService.sendMessage(message.threadId(), message.message());
  }
}
