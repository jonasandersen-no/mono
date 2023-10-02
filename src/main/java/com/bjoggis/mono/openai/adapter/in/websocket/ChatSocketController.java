package com.bjoggis.mono.openai.adapter.in.websocket;

import com.bjoggis.mono.openai.application.ChatThreadService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ChatSocketController {

  private final WebSocketSender webSocketSender;
  private final ChatThreadService chatThreadService;

  public ChatSocketController(WebSocketSender webSocketSender,
      ChatThreadService chatThreadService) {
    this.webSocketSender = webSocketSender;
    this.chatThreadService = chatThreadService;
  }

  @MessageMapping("/hello")
  public void message(ChatMessageRequest message) {

    String response = chatThreadService.sendMessage(message.threadId(), message.message());

//    webSocketSender.send(message.threadId(), response);
  }
}
