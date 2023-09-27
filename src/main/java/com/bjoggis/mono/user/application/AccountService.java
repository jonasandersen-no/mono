package com.bjoggis.mono.user.application;

import com.bjoggis.mono.user.application.port.AccountRepository;
import com.bjoggis.mono.user.domain.Account;
import com.bjoggis.mono.user.domain.AccountId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

  private final AccountRepository repository;

  public AccountService(AccountRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public Account registerAccount(String username, String password) {
    Account account = new Account();
    account.setUsername(username);
    account.setPassword(password);
    return repository.save(account);
  }

  @Transactional(readOnly = true)
  public Account findAccountById(AccountId accountId) {
    return repository.findById(accountId)
        .orElseThrow(() -> new RuntimeException("Account not found"));
  }

  @Transactional(readOnly = true)
  public Account findAccountByUsername(String username) {
    return repository.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("Account not found"));
  }

}
