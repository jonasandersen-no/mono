package com.bjoggis.mono.user.application.port;

import com.bjoggis.mono.user.domain.Account;
import com.bjoggis.mono.user.domain.AccountId;
import com.bjoggis.mono.user.domain.AccountState;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.security.crypto.password.PasswordEncoder;

public class InMemoryAccountRepository implements AccountRepository {

  private final Map<String, AccountState> usernameToMemberMap = new ConcurrentHashMap<>();
  private final Map<AccountId, AccountState> idToMemberMap = new ConcurrentHashMap<>();
  private final AtomicLong sequence = new AtomicLong(0);
  private final PasswordEncoder passwordEncoder = new DummyPasswordEncoder();

  @Override
  public Account save(final Account account) {
    account.setPassword(passwordEncoder.encode(account.getPassword()));
    AccountState accountState = account.memento();
    if (accountState.accountId() == null) {
      AccountId newId = AccountId.of(sequence.getAndIncrement());
      Account copy = new Account(accountState);
      copy.setId(newId);
      accountState = copy.memento();
    }
    usernameToMemberMap.put(accountState.username(), accountState);
    idToMemberMap.put(accountState.accountId(), accountState);
    return new Account(accountState);
  }

  @Override
  public Optional<Account> findById(AccountId accountId) {
    return Optional.ofNullable(idToMemberMap.get(accountId))
        .map(Account::new);
  }

  @Override
  public Optional<Account> findByUsername(String username) {
    return Optional.ofNullable(usernameToMemberMap.get(username))
        .map(Account::new);
  }

}
