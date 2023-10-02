package com.bjoggis.mono.openai.adapter.in.websocket;

public class DummyWebSocketSender implements WebSocketSender {

  private String lastMessage;


  @Override
  public void send(Long threadId, String message) {
    lastMessage = message;
  }

  public String getLastMessage() {
    return lastMessage;
  }
}
