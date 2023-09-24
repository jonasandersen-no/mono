package com.bjoggis.mono.user;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class AccountService {

  private final AccountRepository repository;

  public AccountService(AccountRepository repository) {
    this.repository = repository;
  }

  @Transactional(readOnly = true)
  public Optional<Account> findAccountById(Long id) {
    return repository.findById(id);
  }

  @Transactional(readOnly = true)
  public Optional<Account> findAccountByUsername(String username) {
    return repository.findByUsername(username);
  }

}
