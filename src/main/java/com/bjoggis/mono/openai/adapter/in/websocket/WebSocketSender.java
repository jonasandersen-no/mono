package com.bjoggis.mono.openai.adapter.in.websocket;

public interface WebSocketSender {

  void send(Long threadId, String message);
}
