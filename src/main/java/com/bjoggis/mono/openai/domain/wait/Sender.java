package com.bjoggis.mono.openai.domain.wait;

public enum Sender {
  USER("User"),
  ASSISTANT("Assistant"),
  SYSTEM("System");

  private final String comment;

  Sender(String comment) {
    this.comment = comment;
  }

  public String getComment() {
    return comment;
  }
}
