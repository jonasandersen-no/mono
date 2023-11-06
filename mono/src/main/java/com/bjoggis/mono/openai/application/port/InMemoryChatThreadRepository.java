package com.bjoggis.mono.openai.application.port;

import com.bjoggis.mono.openai.domain.AccountId;
import com.bjoggis.mono.openai.domain.ChatThread;
import com.bjoggis.mono.openai.domain.ChatThreadId;
import com.bjoggis.mono.openai.domain.ChatThreadState;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryChatThreadRepository implements ChatThreadRepository {

  private final Map<AccountId, List<ChatThreadState>> accountIdToStateMap = new ConcurrentHashMap<>();
  private final Map<ChatThreadId, ChatThreadState> idToStateMap = new ConcurrentHashMap<>();
  private final AtomicLong sequence = new AtomicLong(0);


  @Override
  public Optional<ChatThread> findById(ChatThreadId chatThreadId) {
    ChatThreadState state = idToStateMap.get(chatThreadId);

    if (state == null) {
      return Optional.empty();
    }
    return Optional.of(new ChatThread(state));
  }

  @Override
  public List<ChatThread> findAllByAccountId(AccountId accountId) {
    if (!accountIdToStateMap.containsKey(accountId)) {
      return List.of();
    }
    return accountIdToStateMap.get(accountId).stream()
        .map(ChatThread::new)
        .toList();
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

    if (chatThreadState.accountId() != null) {
      if (accountIdToStateMap.containsKey(
          chatThreadState.accountId())) {
        accountIdToStateMap.get(chatThreadState.accountId()).add(chatThreadState);
      } else {
        List<ChatThreadState> chatThreadStates = new ArrayList<>();
        chatThreadStates.add(chatThreadState);
        accountIdToStateMap.put(chatThreadState.accountId(), chatThreadStates);
      }
    }

    idToStateMap.put(chatThreadState.chatThreadId(), chatThreadState);
    return new ChatThread(chatThreadState);
  }

  @Override
  public void deleteById(ChatThreadId chatThreadId) {
    ChatThreadState removedState = idToStateMap.remove(chatThreadId);

    if (removedState == null) {
      throw new IllegalArgumentException("No such chat thread: " + chatThreadId);
    }
  }
}
