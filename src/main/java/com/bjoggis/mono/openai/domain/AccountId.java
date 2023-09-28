package com.bjoggis.mono.openai.domain;

public record AccountId(Long id) {

  public static AccountId of(Long id) {
    return new AccountId(id);
  }
}
