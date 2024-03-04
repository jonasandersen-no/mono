package com.bjoggis.mono.openai.application.port;

import com.bjoggis.mono.openai.domain.ChatMessage;

public interface OpenAIAdapter {

  ChatMessage sendMessage(String message, String username);
}
