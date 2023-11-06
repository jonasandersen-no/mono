package com.bjoggis.mono.user.adapter.out.jpa;

import com.bjoggis.mono.user.application.port.AccountRepository;
import com.bjoggis.mono.user.domain.Account;
import com.bjoggis.mono.user.domain.AccountId;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
class AccountRepositoryAdapter implements AccountRepository {

  private final AccountJPARepository repository;
  private final BCryptPasswordEncoder passwordEncoder;


  public AccountRepositoryAdapter(AccountJPARepository repository,
      BCryptPasswordEncoder passwordEncoder) {
    this.repository = repository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Account save(Account account) {
    account.setPassword("{bcrypt}"+ passwordEncoder.encode(account.getPassword()));
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
