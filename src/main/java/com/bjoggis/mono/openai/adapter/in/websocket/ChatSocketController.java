package com.bjoggis.mono.openai.adapter.in.websocket;

import com.bjoggis.mono.openai.application.ChatThreadService;
import java.security.Principal;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ChatSocketController {

  private final ChatThreadService chatThreadService;

  public ChatSocketController(ChatThreadService chatThreadService) {
    this.chatThreadService = chatThreadService;
  }

  @MessageMapping("/hello")
  public void message(ChatMessageRequest message, Principal principal) {
    System.out.println("Principal: " + principal.getName());
    chatThreadService.sendMessage(message.threadId(), message.message(), principal.getName());
  }
}
