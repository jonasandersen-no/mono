package com.bjoggis.mono.openai.adapter.in.websocket;

import com.bjoggis.mono.openai.application.ChatThreadService;
import com.bjoggis.mono.openai.domain.AccountId;
import com.bjoggis.mono.openai.domain.ChatThreadId;
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
  public void message(ChatMessageRequest request, Principal principal) {
    boolean validThread = chatThreadService.validThread(ChatThreadId.of(request.threadId()),
        AccountId.of(1L));

    if (!validThread) {
      throw new IllegalArgumentException("Account is not owner of chat thread");
    }

    String response = chatThreadService.sendMessage(request.message(), principal.getName());

    chatThreadService.addMessage(ChatThreadId.of(request.threadId()), AccountId.of(1L), response);
  }
}
