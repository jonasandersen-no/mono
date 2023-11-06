package com.bjoggis.mono.openai.domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ChatThread {

  private ChatThreadId chatThreadId;
  private List<ChatMessage> messages = new ArrayList<>();
  private AccountId accountId;

  public ChatThread() {
  }

  public ChatThread(ChatThreadState chatThreadState) {
    this.chatThreadId = chatThreadState.chatThreadId();
    this.messages = chatThreadState.messages();
    this.accountId = chatThreadState.accountId();
  }

  public ChatThread(AccountId accountId) {
    this.accountId = accountId;
  }

  public ChatThreadId getChatThreadId() {
    return chatThreadId;
  }

  public void setChatThreadId(ChatThreadId chatThreadId) {
    this.chatThreadId = chatThreadId;
  }

  public List<ChatMessage> getMessages() {
    return messages;
  }

  public void setMessages(List<ChatMessage> messages) {
    this.messages = messages;
  }

  public AccountId getAccountId() {
    return accountId;
  }

  public void setAccountId(AccountId accountId) {
    this.accountId = accountId;
  }

  public ChatThreadState memento() {
    return new ChatThreadState(chatThreadId, messages, accountId);
  }

  public boolean isOwner(AccountId accountId) {
    if (this.accountId == null) {
      return false;
    }

    return this.accountId.equals(accountId);
  }

  public void addMessage(ChatMessage message) {
    messages.add(message);
  }
}
