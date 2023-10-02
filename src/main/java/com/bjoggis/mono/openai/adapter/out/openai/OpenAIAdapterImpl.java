package com.bjoggis.mono.openai.adapter.out.openai;

import com.bjoggis.mono.openai.application.port.OpenAIAdapter;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class OpenAIAdapterImpl implements OpenAIAdapter {

  private final OpenaiProperties properties;

  private final String userId = UUID.randomUUID().toString();

  public OpenAIAdapterImpl(OpenaiProperties properties) {
    this.properties = properties;
  }

  @Override
  public String sendMessage(String message) {
    OpenAiService service = new OpenAiService(properties.token(), Duration.ofMinutes(1));

    ChatCompletionRequest request = ChatCompletionRequest.builder()
        .model("gpt-4")
        .messages(List.of(new ChatMessage("user", message)))
        .n(1)
        .maxTokens(500)
        .temperature(1.2)
        .user(userId)
        .build();

    ChatCompletionResult response = service.createChatCompletion(request);

    return response.getChoices().get(0).getMessage().getContent();
  }
}
