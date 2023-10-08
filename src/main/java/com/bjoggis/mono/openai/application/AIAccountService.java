package com.bjoggis.mono.openai.application;

import com.bjoggis.mono.openai.application.port.AccountAdapter;
import com.bjoggis.mono.openai.domain.Account;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AIAccountService {

  private final AccountAdapter accountAdapter;

  public AIAccountService(AccountAdapter accountAdapter) {
    this.accountAdapter = accountAdapter;
  }

  public Optional<Account> findAccountByUsername(String username) {
    return Optional.ofNullable(accountAdapter.findAccountByUsername(username));
  }
}
