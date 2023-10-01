package com.bjoggis.mono.openai.adapter.in.web;

import com.bjoggis.mono.openai.domain.ChatThread;
import com.bjoggis.mono.openai.domain.wait.ChatMessageId;
import java.util.List;

record ChatThreadResponse(Long chatThreadId, List<Long> messageIds, Long accountId) {

  public static ChatThreadResponse fromChatThread(ChatThread chatThread) {
    List<Long> messageIds = chatThread.getMessageIds().stream()
        .map(ChatMessageId::messageId)
        .toList();

    Long accountId = null;
    if (chatThread.getAccountId() != null) {
      accountId = chatThread.getAccountId().id();
    }

    return new ChatThreadResponse(chatThread.getChatThreadId().chatThreadId(),
        messageIds,
        accountId);
  }
}
