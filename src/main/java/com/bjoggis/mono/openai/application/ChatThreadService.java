package com.bjoggis.mono.openai.application;

import com.bjoggis.mono.openai.application.port.ChatThreadRepository;
import com.bjoggis.mono.openai.domain.AccountId;
import com.bjoggis.mono.openai.domain.ChatThread;
import com.bjoggis.mono.openai.domain.ChatThreadId;
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

}
