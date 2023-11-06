package com.bjoggis.mono.user.adapter.in;

import com.bjoggis.mono.user.domain.Account;

public record AccountResource(Long id, String username) {

  public static AccountResource from(Account account) {
    return new AccountResource(account.getId().id(), account.getUsername());
  }
}
