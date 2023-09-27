package com.bjoggis.mono.user.adapter.out.jpa;

import com.bjoggis.mono.user.application.port.AccountRepository;
import com.bjoggis.mono.user.domain.Account;
import com.bjoggis.mono.user.domain.AccountId;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
class AccountRepositoryAdapter implements AccountRepository {

  private final AccountJPARepository repository;

  public AccountRepositoryAdapter(AccountJPARepository repository) {
    this.repository = repository;
  }

  @Override
  public Account save(Account account) {
    AccountDbo accountDbo = AccountDbo.from(account);
    AccountDbo saved = repository.save(accountDbo);
    return saved.asAccount();
  }

  @Override
  public Optional<Account> findById(AccountId accountId) {
    return repository.findById(accountId.id())
        .map(AccountDbo::asAccount);
  }

  @Override
  public Optional<Account> findByUsername(String username) {
    return repository.findByUsername(username)
        .map(AccountDbo::asAccount);
  }
}
