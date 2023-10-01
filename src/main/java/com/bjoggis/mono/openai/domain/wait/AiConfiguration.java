package com.bjoggis.mono.openai.domain.wait;

public class AiConfiguration {

  private AiConfigurationId aiConfigurationId;
  private String systemMessage;
  private Double temperature;
  private Integer requestMaxTokens;
  private Integer responseMaxTokens;
  private String model;
  private Integer maxMessages;
  private Integer numberOfMessages;

  public AiConfigurationId getAiConfigurationId() {
    return aiConfigurationId;
  }

  public void setAiConfigurationId(AiConfigurationId aiConfigurationId) {
    this.aiConfigurationId = aiConfigurationId;
  }

  public String getSystemMessage() {
    return systemMessage;
  }

  public void setSystemMessage(String systemMessage) {
    this.systemMessage = systemMessage;
  }

  public Double getTemperature() {
    return temperature;
  }

  public void setTemperature(Double temperature) {
    this.temperature = temperature;
  }

  public Integer getRequestMaxTokens() {
    return requestMaxTokens;
  }

  public void setRequestMaxTokens(Integer requestMaxTokens) {
    this.requestMaxTokens = requestMaxTokens;
  }

  public Integer getResponseMaxTokens() {
    return responseMaxTokens;
  }

  public void setResponseMaxTokens(Integer responseMaxTokens) {
    this.responseMaxTokens = responseMaxTokens;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public Integer getMaxMessages() {
    return maxMessages;
  }

  public void setMaxMessages(Integer maxMessages) {
    this.maxMessages = maxMessages;
  }

  public Integer getNumberOfMessages() {
    return numberOfMessages;
  }

  public void setNumberOfMessages(Integer numberOfMessages) {
    this.numberOfMessages = numberOfMessages;
  }
}
