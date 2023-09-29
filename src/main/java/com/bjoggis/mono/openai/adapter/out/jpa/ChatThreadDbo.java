package com.bjoggis.mono.openai.adapter.out.jpa;

import com.bjoggis.mono.openai.domain.AccountId;
import com.bjoggis.mono.openai.domain.ChatThread;
import com.bjoggis.mono.openai.domain.ChatThreadId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "chat_thread", catalog = "main")
public class ChatThreadDbo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  private Long accountId;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public static ChatThreadDbo from(ChatThread thread) {
    ChatThreadDbo dbo = new ChatThreadDbo();

    if (thread.getChatThreadId() != null) {
      dbo.setId(thread.getChatThreadId().chatThreadId());
    }

    if (thread.getAccountId() != null) {
      dbo.setAccountId(thread.getAccountId().id());
    }

    return dbo;
  }

  public ChatThread asChatThread() {
    ChatThread chatThread = new ChatThread();

    chatThread.setChatThreadId(ChatThreadId.of(id));
    chatThread.setAccountId(AccountId.of(accountId));

    return chatThread;
  }
}
