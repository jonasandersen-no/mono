package com.bjoggis.mono.openai.adapter.in.web;

import com.bjoggis.mono.openai.application.ThreadService;
import com.bjoggis.mono.openai.domain.AccountId;
import com.bjoggis.mono.openai.domain.ChatThread;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/thread")
class ThreadController {

  private final ThreadService threadService;

  public ThreadController(ThreadService threadService) {
    this.threadService = threadService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  CreateThreadResponse createThread(@RequestBody CreateThreadRequest createThreadRequest) {
    ChatThread thread = threadService.createThread(AccountId.of(createThreadRequest.accountId()));

    return CreateThreadResponse.fromChatThread(thread);
  }

}
