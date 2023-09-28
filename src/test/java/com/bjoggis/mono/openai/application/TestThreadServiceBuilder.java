package com.bjoggis.mono.openai.application;

import com.bjoggis.mono.openai.application.port.ChatThreadRepository;
import com.bjoggis.mono.openai.application.port.InMemoryChatThreadRepository;
import com.bjoggis.mono.openai.domain.ChatThread;
import com.bjoggis.mono.openai.domain.ChatThreadId;

public class TestThreadServiceBuilder {

  private final ChatThreadRepository chatThreadRepository;

  private ChatThread lastThread;
  private ChatThreadId lastThreadId;

  public TestThreadServiceBuilder() {
    chatThreadRepository = new InMemoryChatThreadRepository();
  }

  public ThreadService build() {
    return new ThreadService(chatThreadRepository);
  }

  public ChatThreadRepository getChatThreadRepository() {
    return chatThreadRepository;
  }

  public ChatThread getLastThread() {
    return lastThread;
  }

  public void setLastThread(ChatThread lastThread) {
    this.lastThread = lastThread;
  }

  public ChatThreadId getLastThreadId() {
    return lastThreadId;
  }

  public void setLastThreadId(ChatThreadId lastThreadId) {
    this.lastThreadId = lastThreadId;
  }
}
