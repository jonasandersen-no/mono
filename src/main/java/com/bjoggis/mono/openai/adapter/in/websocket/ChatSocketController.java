package com.bjoggis.mono.openai.adapter.in.websocket;

import com.bjoggis.mono.openai.application.AIAccountService;
import com.bjoggis.mono.openai.application.ChatThreadService;
import com.bjoggis.mono.openai.domain.Account;
import com.bjoggis.mono.openai.domain.AccountId;
import com.bjoggis.mono.openai.domain.ChatThreadId;
import java.security.Principal;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class ChatSocketController {

  private final ChatThreadService chatThreadService;
  private final AIAccountService AIAccountService;

  public ChatSocketController(ChatThreadService chatThreadService,
      AIAccountService AIAccountService) {
    this.chatThreadService = chatThreadService;
    this.AIAccountService = AIAccountService;
  }

  @MessageMapping("/hello")
  @Transactional
  public void message(ChatMessageRequest request, Principal principal) {
    Account account = AIAccountService.findAccountByUsername(principal.getName())
        .orElseThrow(() -> new IllegalArgumentException("Account not found"));

    boolean validThread = chatThreadService.validThread(ChatThreadId.of(request.threadId()),
        account.getId());

    if (!validThread) {
      throw new IllegalArgumentException("Account is not owner of chat thread");
    }

    chatThreadService.addMessage(ChatThreadId.of(request.threadId()), AccountId.of(1L),
        request.message());

    String response = chatThreadService.sendMessage(request.message(), account.getUsername());

    chatThreadService.addMessage(ChatThreadId.of(request.threadId()), AccountId.of(1L), response);
  }
}
