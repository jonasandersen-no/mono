package com.bjoggis.mono.openai.application;

import com.bjoggis.mono.openai.application.port.ChatThreadRepository;
import com.bjoggis.mono.openai.domain.AccountId;
import com.bjoggis.mono.openai.domain.ChatThread;
import com.bjoggis.mono.openai.domain.ChatThreadId;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ChatThreadService {

  private final ChatThreadRepository chatThreadRepository;

  public ChatThreadService(ChatThreadRepository chatThreadRepository) {
    this.chatThreadRepository = chatThreadRepository;
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
}
