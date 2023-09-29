package com.bjoggis.mono.openai.application.port;

import com.bjoggis.mono.openai.domain.ChatThread;
import com.bjoggis.mono.openai.domain.ChatThreadId;
import com.bjoggis.mono.openai.domain.ChatThreadState;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryChatThreadRepository implements ChatThreadRepository {

  //  private final Map<String, ChatThreadState> usernameToMemberMap = new ConcurrentHashMap<>();
  private final Map<ChatThreadId, ChatThreadState> idToMemberMap = new ConcurrentHashMap<>();
  private final AtomicLong sequence = new AtomicLong(0);


  @Override
  public Optional<ChatThread> findById(ChatThreadId chatThreadId) {
    ChatThreadState state = idToMemberMap.get(chatThreadId);

    if (state == null) {
      return Optional.empty();
    }
    return Optional.of(new ChatThread(state));
  }

  @Override
  public ChatThread save(ChatThread thread) {
    ChatThreadState chatThreadState = thread.memento();
    if (chatThreadState.chatThreadId() == null) {
      ChatThreadId newId = ChatThreadId.of(sequence.getAndIncrement());
      ChatThread copy = new ChatThread(chatThreadState);
      copy.setChatThreadId(newId);
      chatThreadState = copy.memento();
    }
//    usernameToMemberMap.put(chatThreadState.username(), chatThreadState);
    idToMemberMap.put(chatThreadState.chatThreadId(), chatThreadState);
    return new ChatThread(chatThreadState);
  }

  @Override
  public void deleteById(ChatThreadId chatThreadId) {
    ChatThreadState removedState = idToMemberMap.remove(chatThreadId);

    if (removedState == null) {
      throw new IllegalArgumentException("No such chat thread: " + chatThreadId);
    }
  }
}
