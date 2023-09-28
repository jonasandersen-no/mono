package com.bjoggis.mono.openai.application;

import com.bjoggis.mono.openai.application.port.ChatThreadRepository;
import com.bjoggis.mono.openai.domain.AccountId;
import com.bjoggis.mono.openai.domain.ChatThread;
import org.springframework.stereotype.Service;

@Service
public class ThreadService {

  private final ChatThreadRepository chatThreadRepository;

  public ThreadService(ChatThreadRepository chatThreadRepository) {
    this.chatThreadRepository = chatThreadRepository;
  }

  public ChatThread createThread(AccountId accountId) {
    ChatThread thread = new ChatThread();
    thread.setAccountId(accountId);

    return chatThreadRepository.save(thread);
  }

}
