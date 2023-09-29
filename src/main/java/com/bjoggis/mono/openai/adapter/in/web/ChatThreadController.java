package com.bjoggis.mono.openai.adapter.in.web;

import com.bjoggis.mono.openai.application.ChatThreadService;
import com.bjoggis.mono.openai.domain.AccountId;
import com.bjoggis.mono.openai.domain.ChatThread;
import com.bjoggis.mono.openai.domain.ChatThreadId;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/thread")
public class ChatThreadController {

  private final ChatThreadService chatThreadService;

  public ChatThreadController(ChatThreadService chatThreadService) {
    this.chatThreadService = chatThreadService;
  }

  @PostMapping
  ResponseEntity<ChatThreadResponse> createThread(
      @RequestBody CreateThreadRequest createThreadRequest) {
    ChatThread thread = chatThreadService.createThread(
        AccountId.of(createThreadRequest.accountId()));

    return new ResponseEntity<>(ChatThreadResponse.fromChatThread(thread), HttpStatus.CREATED);
  }

  @GetMapping("/{threadId}")
  ResponseEntity<ChatThreadResponse> FindThread(@PathVariable Long threadId) {
    ChatThreadResponse response = chatThreadService.findById(ChatThreadId.of(threadId))
        .map(ChatThreadResponse::fromChatThread)
        .orElseThrow(EntityNotFoundException::new);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
