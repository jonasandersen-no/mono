package com.bjoggis.mono.openai.adapter.out.jpa;

import com.bjoggis.mono.openai.domain.ChatMessage;
import com.bjoggis.mono.openai.domain.ChatMessageId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "chat_message_dbo", catalog = "main")
public class ChatMessageDbo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  private String message;

  @ManyToOne
  @JoinColumn(name = "chat_thread_dbo_id")
  private ChatThreadDbo chatThreadDbo;

  public ChatThreadDbo getChatThreadDbo() {
    return chatThreadDbo;
  }

  public void setChatThreadDbo(ChatThreadDbo chatThreadDbo) {
    this.chatThreadDbo = chatThreadDbo;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ChatMessage asChatMessage() {
    ChatMessage chatMessage = new ChatMessage();
    chatMessage.setId(ChatMessageId.of(id));
    chatMessage.setMessage(message);
    return chatMessage;
  }

  public static ChatMessageDbo from(ChatMessage chatMessage) {
    ChatMessageDbo dbo = new ChatMessageDbo();
    if(chatMessage.getId() != null) {
      dbo.setId(chatMessage.getId().id());
    }
    dbo.setMessage(chatMessage.getMessage());
    return dbo;
  }
}