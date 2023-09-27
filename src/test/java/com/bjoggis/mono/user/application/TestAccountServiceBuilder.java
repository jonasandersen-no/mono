package com.bjoggis.mono.user.application;

import com.bjoggis.mono.user.application.port.AccountRepository;
import com.bjoggis.mono.user.application.port.InMemoryAccountRepository;
import com.bjoggis.mono.user.domain.Account;
import com.bjoggis.mono.user.domain.AccountId;
import jakarta.validation.constraints.NotNull;

public class TestAccountServiceBuilder {

  private final AccountRepository accountRepository;

  private Account lastAccount;
  private AccountId lastAccountId;

  public TestAccountServiceBuilder() {
    accountRepository = new InMemoryAccountRepository();
  }


  public AccountRepository getAccountRepository() {
    return accountRepository;
  }

  public AccountService build() {
    return new AccountService(accountRepository);
  }

  @NotNull
  public TestAccountServiceBuilder saveAccount(Account account) {
    lastAccount = accountRepository.save(account);
    lastAccountId = lastAccount.getId();
    return this;
  }

  public Account getLastAccount() {
    return lastAccount;
  }

  public AccountId getLastAccountId() {
    return lastAccountId;
  }
}
