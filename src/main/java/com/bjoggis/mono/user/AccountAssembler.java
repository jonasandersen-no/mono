package com.bjoggis.mono.user;

import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
class AccountAssembler implements Function<Account, AccountResource> {

  @Override
  public AccountResource apply(Account account) {
    return new AccountResource(account.getId(), account.getUsername());
  }
}
