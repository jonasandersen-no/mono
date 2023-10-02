package com.bjoggis.mono.openai.adapter.out.openai;

import com.bjoggis.mono.openai.application.port.OpenAIAdapter;
import org.springframework.stereotype.Component;

@Component
public class OpenAIAdapterImpl implements OpenAIAdapter {

  @Override
  public String sendMessage(String message) {
    return null;
  }
}
