package com.bjoggis.mono.openai.domain;

import java.util.List;

public record ChatThreadState(ChatThreadId chatThreadId,
                              List<String> messages,
                              AccountId accountId) {

}
