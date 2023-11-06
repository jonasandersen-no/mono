package com.bjoggis.mono.openai.domain.wait;

public class ActiveAiConfiguration {

  private ActiveAiConfigurationId activeAiConfigurationId;
  private AiConfigurationId aiConfigurationId;

  public ActiveAiConfigurationId getActiveAiConfigurationId() {
    return activeAiConfigurationId;
  }

  public void setActiveAiConfigurationId(
      ActiveAiConfigurationId activeAiConfigurationId) {
    this.activeAiConfigurationId = activeAiConfigurationId;
  }

  public AiConfigurationId getAiConfigurationId() {
    return aiConfigurationId;
  }

  public void setAiConfigurationId(AiConfigurationId aiConfigurationId) {
    this.aiConfigurationId = aiConfigurationId;
  }
}
