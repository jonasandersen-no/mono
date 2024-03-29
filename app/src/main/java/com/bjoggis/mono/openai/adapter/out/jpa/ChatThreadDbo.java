package com.bjoggis.mono.openai.adapter.out.jpa;

import com.bjoggis.mono.openai.domain.AccountId;
import com.bjoggis.mono.openai.domain.ChatMessage;
import com.bjoggis.mono.openai.domain.ChatThread;
import com.bjoggis.mono.openai.domain.ChatThreadId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "chat_thread", catalog = "main")
public class ChatThreadDbo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  private Long accountId;

  @Column(name = "message", nullable = false)
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "chatThreadDbo", cascade = CascadeType.ALL)
  private List<ChatMessageDbo> messages = new LinkedList<>();


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

  public List<ChatMessageDbo> getMessages() {
    return messages;
  }

  public void setMessages(List<ChatMessageDbo> messages) {
    this.messages = messages;
  }

  public static ChatThreadDbo from(ChatThread thread) {
    ChatThreadDbo dbo = new ChatThreadDbo();

    if (thread.getChatThreadId() != null) {
      dbo.setId(thread.getChatThreadId().chatThreadId());
    }

    if (thread.getAccountId() != null) {
      dbo.setAccountId(thread.getAccountId().id());
    }

    List<ChatMessageDbo> chatMessages = thread.getMessages().stream()
        .map(ChatMessageDbo::from)
        .peek(chatMessageDbo -> chatMessageDbo.setChatThreadDbo(dbo))
        .collect(Collectors.toList());

    dbo.setMessages(chatMessages);

    return dbo;
  }

  public ChatThread asChatThread() {
    ChatThread chatThread = new ChatThread();

    chatThread.setChatThreadId(ChatThreadId.of(id));
    chatThread.setAccountId(AccountId.of(accountId));

    List<ChatMessage> chatMessages = messages.stream()
        .map(ChatMessageDbo::asChatMessage)
        .collect(Collectors.toList());
    chatThread.setMessages(chatMessages);

    return chatThread;
  }
}
