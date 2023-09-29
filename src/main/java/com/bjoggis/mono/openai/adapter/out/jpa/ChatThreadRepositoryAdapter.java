package com.bjoggis.mono.openai.adapter.out.jpa;

import com.bjoggis.mono.openai.application.port.ChatThreadRepository;
import com.bjoggis.mono.openai.domain.ChatThread;
import com.bjoggis.mono.openai.domain.ChatThreadId;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ChatThreadRepositoryAdapter implements ChatThreadRepository {

  private final ChatThreadJpaRepository repository;

  public ChatThreadRepositoryAdapter(ChatThreadJpaRepository repository) {
    this.repository = repository;
  }

  @Override
  public Optional<ChatThread> findById(ChatThreadId chatThreadId) {
    return repository.findById(chatThreadId.chatThreadId())
        .map(ChatThreadDbo::asChatThread);
  }

  @Override
  public ChatThread save(ChatThread thread) {
    ChatThreadDbo entity = ChatThreadDbo.from(thread);

    ChatThreadDbo saved = repository.save(entity);

    return saved.asChatThread();
  }

  @Override
  public void deleteById(ChatThreadId chatThreadId) {
    boolean exists = repository.existsById(chatThreadId.chatThreadId());

    if (exists) {
      repository.deleteById(chatThreadId.chatThreadId());
      return;
    }
    throw new IllegalArgumentException("No such chat thread: " + chatThreadId);

  }
}
