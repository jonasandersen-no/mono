package com.bjoggis.mono.openai.domain;

import java.util.ArrayList;
import java.util.List;

public class ChatThread {

  private ChatThreadId chatThreadId;
  private List<MessageId> messageIds = new ArrayList<>();
  private AccountId accountId;

  public ChatThread() {
  }

  public ChatThread(ChatThreadState chatThreadState) {
    this.chatThreadId = chatThreadState.chatThreadId();
    this.messageIds = chatThreadState.messageIds();
    this.accountId = chatThreadState.accountId();
  }

  public ChatThreadId getChatThreadId() {
    return chatThreadId;
  }

  public void setChatThreadId(ChatThreadId chatThreadId) {
    this.chatThreadId = chatThreadId;
  }

  public List<MessageId> getMessageIds() {
    return messageIds;
  }

  public void setMessageIds(List<MessageId> messageIds) {
    this.messageIds = messageIds;
  }

  public AccountId getAccountId() {
    return accountId;
  }

  public void setAccountId(AccountId accountId) {
    this.accountId = accountId;
  }

  public ChatThreadState memento() {
    return new ChatThreadState(chatThreadId, messageIds, accountId);
  }
}
