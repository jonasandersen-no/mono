package com.bjoggis.mono.openai.adapter.in.web;

import com.bjoggis.mono.openai.domain.ChatThread;
import com.bjoggis.mono.openai.domain.MessageId;
import java.util.List;

record CreateThreadResponse(String chatThreadId, List<String> messageIds, Long accountId) {

  public static CreateThreadResponse fromChatThread(ChatThread chatThread) {
    List<String> messageIds = chatThread.getMessageIds().stream()
        .map(MessageId::messageId)
        .toList();

    return new CreateThreadResponse(chatThread.getChatThreadId().chatThreadId(),
        messageIds,
        chatThread.getAccountId().id());
  }
}
