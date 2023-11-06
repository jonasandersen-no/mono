package com.bjoggis.mono.openai.adapter.out.user;

import com.bjoggis.mono.openai.application.port.AccountAdapter;
import com.bjoggis.mono.openai.domain.Account;
import com.bjoggis.mono.openai.domain.AccountId;
import com.bjoggis.mono.user.adapter.in.AccountController;
import com.bjoggis.mono.user.adapter.in.AccountResource;
import org.springframework.stereotype.Component;

@Component
public class AccountAdapterImpl implements AccountAdapter {

  private final AccountController accountController;

  public AccountAdapterImpl(AccountController accountController) {
    this.accountController = accountController;
  }

  @Override
  public Account findAccountByUsername(String username) {
    AccountResource resource = accountController.findAccountByUsername(username);

    Account account = new Account();
    account.setId(AccountId.of(resource.id()));
    account.setUsername(resource.username());
    return account;
  }
}
