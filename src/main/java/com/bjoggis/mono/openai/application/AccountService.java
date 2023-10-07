package com.bjoggis.mono.openai.application;

import com.bjoggis.mono.openai.application.port.AccountAdapter;
import com.bjoggis.mono.openai.domain.Account;

public class AccountService {

  private final AccountAdapter accountAdapter;

  public AccountService(AccountAdapter accountAdapter) {
    this.accountAdapter = accountAdapter;
  }

  Account findAccountByUsername(String username) {
    return accountAdapter.findAccountByUsername(username);
  }
}
