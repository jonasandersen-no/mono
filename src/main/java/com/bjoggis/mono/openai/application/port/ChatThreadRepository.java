package com.bjoggis.mono.openai.application.port;

import com.bjoggis.mono.openai.domain.ChatThread;
import com.bjoggis.mono.openai.domain.ChatThreadId;
import java.util.Optional;

public interface ChatThreadRepository {

  Optional<ChatThread> findById(ChatThreadId chatThreadId);

  ChatThread save(ChatThread thread);
}
