package com.bjoggis.mono.openai.adapter.in.web;

import com.bjoggis.mono.openai.application.AIAccountService;
import com.bjoggis.mono.openai.application.ChatThreadService;
import com.bjoggis.mono.openai.domain.Account;
import com.bjoggis.mono.openai.domain.ChatThread;
import com.bjoggis.mono.openai.domain.ChatThreadId;
import jakarta.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
  @CacheEvict(value = "threads", key = "#principal.name")
  public ResponseEntity<ChatThreadResponse> createThread(Principal principal) {
    Account account = AIAccountService.findAccountByUsername(principal.getName())
        .orElseThrow(() -> new IllegalArgumentException("Account not found"));

    ChatThread thread = chatThreadService.createThread(account.getId());

    return new ResponseEntity<>(ChatThreadResponse.fromChatThread(thread), HttpStatus.CREATED);
  }

  @GetMapping("/{threadId}")
  ResponseEntity<ChatThreadResponse> findThread(@PathVariable Long threadId) {
    ChatThreadResponse response = chatThreadService.findById(ChatThreadId.of(threadId))
        .map(ChatThreadResponse::fromChatThread)
        .orElseThrow(EntityNotFoundException::new);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping
  @Cacheable(value = "threads", key = "#principal.name")
  public ResponseEntity<List<ChatThreadResponse>> findAllThreadsByPrincipal(Principal principal) {
    Account account = AIAccountService.findAccountByUsername(principal.getName())
        .orElseThrow(() -> new IllegalArgumentException("Account not found"));

    List<ChatThreadResponse> response = chatThreadService.findAllThreads(account.getId()).stream()
        .map(ChatThreadResponse::fromChatThreadNoMessages)
        .toList();

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/{threadId}")
  @CacheEvict(value = "threads", key = "#principal.name")
  public ResponseEntity<?> deleteThread(@PathVariable Long threadId, Principal principal) {
    Account account = AIAccountService.findAccountByUsername(principal.getName())
        .orElseThrow(() -> new IllegalArgumentException("Account not found"));

    try {
      chatThreadService.validThread(ChatThreadId.of(threadId), account.getId());
    } catch (EntityNotFoundException e) {
      throw new IllegalArgumentException("No such chat thread: " + threadId);
    }

    chatThreadService.deleteThreadById(ChatThreadId.of(threadId));
    return ResponseEntity.ok().build();
  }

  @ExceptionHandler
  ProblemDetail handleIllegalArgumentException(IllegalArgumentException e) {
    return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
  }
}
