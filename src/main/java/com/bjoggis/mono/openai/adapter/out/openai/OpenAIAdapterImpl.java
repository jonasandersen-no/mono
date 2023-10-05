package com.bjoggis.mono.openai.adapter.out.openai;

import com.bjoggis.mono.openai.application.port.OpenAIAdapter;
import com.bjoggis.mono.openai.application.port.WebSocketSender;
import com.theokanning.openai.completion.chat.ChatCompletionChunk;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import io.reactivex.Flowable;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.stereotype.Component;

@Component
public class OpenAIAdapterImpl implements OpenAIAdapter {

  private final OpenaiProperties properties;
  private final WebSocketSender webSocketSender;
  private final HasherFunction hasherFunction;

  private final String userId = UUID.randomUUID().toString();

  public OpenAIAdapterImpl(OpenaiProperties properties, WebSocketSender webSocketSender,
      HasherFunction hasherFunction) {
    this.properties = properties;
    this.webSocketSender = webSocketSender;
    this.hasherFunction = hasherFunction;
  }

  @Override
  public String sendMessage(String message, String username) {
    OpenAiService service = new OpenAiService(properties.token(), Duration.ofMinutes(1));

    ChatCompletionRequest request = ChatCompletionRequest.builder()
        .model("gpt-4")
        .messages(List.of(new ChatMessage("user", message)))
        .n(1)
        .maxTokens(500)
        .temperature(1.2)
        .user(hasherFunction.apply(username))
        .build();

    Flowable<ChatCompletionChunk> flowable = service.streamChatCompletion(
        request);

    AtomicBoolean isFirst = new AtomicBoolean(true);
    ChatMessage chatMessage = service.mapStreamToAccumulator(flowable)
        .doOnNext(accumulator -> {
          if (accumulator.isFunctionCall()) {
            if (isFirst.getAndSet(false)) {
              System.out.println(
                  "Executing function " + accumulator.getAccumulatedChatFunctionCall().getName()
                      + "...");
            }
          } else {
            if (isFirst.getAndSet(false)) {
              System.out.print("Response: ");
            }
            if (accumulator.getMessageChunk().getContent() != null) {
              webSocketSender.send(1L, accumulator.getMessageChunk().getContent(), username);
              System.out.print(accumulator.getMessageChunk().getContent());
            }
          }
        })
        .doOnComplete(System.out::println)
        .lastElement()
        .blockingGet()
        .getAccumulatedMessage();
    return chatMessage.getContent();
  }
}
