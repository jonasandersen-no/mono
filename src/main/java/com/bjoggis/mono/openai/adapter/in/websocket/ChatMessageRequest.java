package com.bjoggis.mono.openai.adapter.in.websocket;

public record ChatMessageRequest(Long threadId, String message) {

}
