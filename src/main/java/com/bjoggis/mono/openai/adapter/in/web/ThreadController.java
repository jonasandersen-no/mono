package com.bjoggis.mono.openai.adapter.in.web;

import com.bjoggis.mono.openai.application.ChatThreadService;
import com.bjoggis.mono.openai.domain.AccountId;
import com.bjoggis.mono.openai.domain.ChatThread;
import com.bjoggis.mono.openai.domain.ChatThreadId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/thread")
public class ThreadController {

  private final ChatThreadService chatThreadService;

  public ThreadController(ChatThreadService chatThreadService) {
    this.chatThreadService = chatThreadService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  CreateThreadResponse createThread(@RequestBody CreateThreadRequest createThreadRequest) {
    ChatThread thread = chatThreadService.createThread(
        AccountId.of(createThreadRequest.accountId()));

    return CreateThreadResponse.fromChatThread(thread);
  }

  @GetMapping
  ResponseEntity<FindThreadResponse> FindThread(@RequestBody FindThreadRequest threadRequest) {
    return chatThreadService.findById(ChatThreadId.of(threadRequest.threadId()))
        .map(FindThreadResponse::fromChatThread)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

}
