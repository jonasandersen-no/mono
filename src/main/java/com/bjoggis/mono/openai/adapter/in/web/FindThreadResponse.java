package com.bjoggis.mono.openai.adapter.in.web;

import com.bjoggis.mono.openai.domain.ChatThread;
import org.springframework.http.ResponseEntity;

public record FindThreadResponse(Long threadId) {

  public static ResponseEntity<FindThreadResponse> fromChatThread(ChatThread thread) {
    return ResponseEntity.ok(new FindThreadResponse(thread.getChatThreadId().chatThreadId()));
  }

}
