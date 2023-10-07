package com.bjoggis.mono.openai.application;

import com.bjoggis.mono.openai.application.port.ChatThreadRepository;
import com.bjoggis.mono.openai.application.port.OpenAIAdapter;
import com.bjoggis.mono.openai.domain.AccountId;
import com.bjoggis.mono.openai.domain.ChatThread;
import com.bjoggis.mono.openai.domain.ChatThreadId;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ChatThreadService {

  private final ChatThreadRepository chatThreadRepository;
  private final OpenAIAdapter openAIAdapter;

  public ChatThreadService(ChatThreadRepository chatThreadRepository, OpenAIAdapter openAIAdapter) {
    this.chatThreadRepository = chatThreadRepository;
    this.openAIAdapter = openAIAdapter;
  }

  public ChatThread createThread(AccountId accountId) {
    ChatThread thread = new ChatThread();
    thread.setAccountId(accountId);

    return chatThreadRepository.save(thread);
  }

  public Optional<ChatThread> findById(ChatThreadId chatThreadId) {
    return chatThreadRepository.findById(chatThreadId);
  }

  public void deleteThreadById(ChatThreadId chatThreadId) {
    chatThreadRepository.deleteById(chatThreadId);
  }

  public boolean validThread(ChatThreadId chatThreadId, AccountId accountId) {
    ChatThread chatThread = chatThreadRepository.findById(chatThreadId)
        .orElseThrow(() -> new EntityNotFoundException("Chat Thread not found"));

    return chatThread.isOwner(accountId);
  }

  public void addMessage(ChatThreadId chatThreadId, AccountId accountId, String message) {
    ChatThread chatThread = chatThreadRepository.findById(chatThreadId)
        .orElseThrow(() -> new EntityNotFoundException("Chat Thread not found"));

    if (!chatThread.isOwner(accountId)) {
      throw new IllegalArgumentException("Account is not owner of chat thread");
    }

    chatThread.addMessage(message);
    chatThreadRepository.save(chatThread);
  }

  public String sendMessage(String message, String username) {
    return openAIAdapter.sendMessage(message, username);
  }
}
