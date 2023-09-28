package com.bjoggis.mono.openai.application.port;

import com.bjoggis.mono.openai.domain.ChatThread;
import com.bjoggis.mono.openai.domain.ChatThreadId;

public interface ChatThreadRepository {

  ChatThread findById(ChatThreadId chatThreadId);

  ChatThread save(ChatThread thread);
}
