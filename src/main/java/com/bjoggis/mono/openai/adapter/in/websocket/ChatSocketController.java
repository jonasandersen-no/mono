package com.bjoggis.mono.openai.adapter.in.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatSocketController {

  private final SimpMessagingTemplate simpMessagingTemplate;

  public ChatSocketController(SimpMessagingTemplate simpMessagingTemplate) {
    this.simpMessagingTemplate = simpMessagingTemplate;
  }

  @MessageMapping("/hello")
  public void message(ChatMessageRequest message) {
    System.out.println("Received message: " + message.toString());
    String sample = "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Est a sint, dignissimos voluptas et quas assumenda accusantium sequi illum minus, cumque nesciunt quia impedit sit magnam temporibus, reprehenderit quidem placeat!";
//    sample.split(" ");
//    new Thread(() -> {
//      for (int i = 0; i < message.message().length(); i++) {
    simpMessagingTemplate.convertAndSend("/topic/chat",
        new ChatMessageResponse(message.threadId(), sample));

//    simpMessagingTemplate.convertAndSendToUser();
//
//        try {
//          Thread.sleep(100L);
//        } catch (InterruptedException e) {
//          throw new RuntimeException(e);
//        }
//
//      }
//    }).start();
  }
}
