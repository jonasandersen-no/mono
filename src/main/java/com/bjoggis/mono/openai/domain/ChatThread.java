package com.bjoggis.mono.openai.domain;

import java.util.ArrayList;
import java.util.List;

public class ChatThread {

  private ChatThreadId chatThreadId;
  private List<ChatMessageId> chatMessageIds = new ArrayList<>();
  private AccountId accountId;

  public ChatThread() {
  }

  public ChatThread(ChatThreadState chatThreadState) {
    this.chatThreadId = chatThreadState.chatThreadId();
    this.chatMessageIds = chatThreadState.chatMessageIds();
    this.accountId = chatThreadState.accountId();
  }

  public ChatThreadId getChatThreadId() {
    return chatThreadId;
  }

  public void setChatThreadId(ChatThreadId chatThreadId) {
    this.chatThreadId = chatThreadId;
  }

  public List<ChatMessageId> getMessageIds() {
    return chatMessageIds;
  }

  public void setMessageIds(List<ChatMessageId> chatMessageIds) {
    this.chatMessageIds = chatMessageIds;
  }

  public AccountId getAccountId() {
    return accountId;
  }

  public void setAccountId(AccountId accountId) {
    this.accountId = accountId;
  }

  public ChatThreadState memento() {
    return new ChatThreadState(chatThreadId, chatMessageIds, accountId);
  }

  public boolean isOwner(AccountId accountId) {
    if (this.accountId == null) {
      return false;
    }

    return this.accountId.equals(accountId);
  }
}
