package com.bjoggis.mono.openai.domain;

import java.util.List;

public record ChatThreadState(ChatThreadId chatThreadId,
                              List<ChatMessageId> chatMessageIds,
                              AccountId accountId) {

}
