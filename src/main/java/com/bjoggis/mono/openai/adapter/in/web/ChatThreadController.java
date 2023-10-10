package com.bjoggis.mono.openai.adapter.in.web;

import com.bjoggis.mono.openai.application.AIAccountService;
import com.bjoggis.mono.openai.application.ChatThreadService;
import com.bjoggis.mono.openai.domain.Account;
import com.bjoggis.mono.openai.domain.ChatThread;
import com.bjoggis.mono.openai.domain.ChatThreadId;
import jakarta.persistence.EntityNotFoundException;
import java.security.Principal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/thread")
public class ChatThreadController {

  private final ChatThreadService chatThreadService;
  private final AIAccountService AIAccountService;

  public ChatThreadController(ChatThreadService chatThreadService,
      com.bjoggis.mono.openai.application.AIAccountService aiAccountService) {
    this.chatThreadService = chatThreadService;
    AIAccountService = aiAccountService;
  }

  @PostMapping
  ResponseEntity<ChatThreadResponse> createThread(Principal principal) {
    Account account = AIAccountService.findAccountByUsername(principal.getName())
        .orElseThrow(() -> new IllegalArgumentException("Account not found"));

    ChatThread thread = chatThreadService.createThread(account.getId());

    return new ResponseEntity<>(ChatThreadResponse.fromChatThread(thread), HttpStatus.CREATED);
  }

  @GetMapping("/{threadId}")
  ResponseEntity<ChatThreadResponse> FindThread(@PathVariable Long threadId) {
    ChatThreadResponse response = chatThreadService.findById(ChatThreadId.of(threadId))
        .map(ChatThreadResponse::fromChatThread)
        .orElseThrow(EntityNotFoundException::new);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/{threadId}")
  public ResponseEntity<?> deleteThread(@PathVariable Long threadId) {
    chatThreadService.deleteThreadById(ChatThreadId.of(threadId));
    return ResponseEntity.ok().build();
  }

  @ExceptionHandler
  ProblemDetail handleIllegalArgumentException(IllegalArgumentException e) {
    return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
  }
}
