package com.bjoggis.mono.user.domain;

public record AccountId(Long id) {

  public static AccountId of(Long id) {
    return new AccountId(id);
  }
}
