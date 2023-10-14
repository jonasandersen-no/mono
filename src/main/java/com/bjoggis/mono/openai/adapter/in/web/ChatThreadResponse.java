package com.bjoggis.mono.openai.adapter.in.web;

import com.bjoggis.mono.openai.domain.ChatThread;
import java.util.List;

public record ChatThreadResponse(Long chatThreadId, List<String> messages, Long accountId) {

  public static ChatThreadResponse fromChatThread(ChatThread chatThread) {
    Long accountId = null;
    if (chatThread.getAccountId() != null) {
      accountId = chatThread.getAccountId().id();
    }

    return new ChatThreadResponse(chatThread.getChatThreadId().chatThreadId(),
        chatThread.getMessages(),
        accountId);
  }

  public static ChatThreadResponse fromChatThreadNoMessages(ChatThread chatThread) {
    Long accountId = null;
    if (chatThread.getAccountId() != null) {
      accountId = chatThread.getAccountId().id();
    }

    return new ChatThreadResponse(chatThread.getChatThreadId().chatThreadId(),
        null,
        accountId);
  }
}
