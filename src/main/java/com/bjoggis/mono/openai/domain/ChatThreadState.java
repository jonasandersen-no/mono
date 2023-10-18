package com.bjoggis.mono.openai.domain;

import java.util.List;

public record ChatThreadState(ChatThreadId chatThreadId,
                              List<ChatMessage> messages,
                              AccountId accountId) {

}
