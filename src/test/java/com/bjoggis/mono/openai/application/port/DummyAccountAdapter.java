package com.bjoggis.mono.openai.application.port;

import com.bjoggis.mono.openai.domain.Account;
import com.bjoggis.mono.openai.domain.AccountId;

public class DummyAccountAdapter implements AccountAdapter {

  @Override
  public Account findAccountByUsername(String username) {
    Account account = new Account();
    account.setId(AccountId.of(1L));
    account.setUsername(username);
    return account;
  }
}
