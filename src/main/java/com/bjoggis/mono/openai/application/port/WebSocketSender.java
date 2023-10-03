package com.bjoggis.mono.openai.application.port;

public interface WebSocketSender {

  void send(Long threadId, String message);
}
