package com.bjoggis.mono.user.application.port;

import com.bjoggis.mono.user.domain.Account;
import com.bjoggis.mono.user.domain.AccountId;
import com.bjoggis.mono.user.domain.AccountState;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryAccountRepository implements AccountRepository {

  private final Map<String, AccountState> usernameToMemberMap = new ConcurrentHashMap<>();
  private final Map<AccountId, AccountState> idToMemberMap = new ConcurrentHashMap<>();
  private final AtomicLong sequence = new AtomicLong(0);

  @Override
  public Account save(final Account member) {
    AccountState memberState = member.memento();
    if (memberState.accountId() == null) {
      AccountId newId = AccountId.of(sequence.getAndIncrement());
      Account copy = new Account(memberState);
      copy.setId(newId);
      memberState = copy.memento();
    }
    usernameToMemberMap.put(memberState.username(), memberState);
    idToMemberMap.put(memberState.accountId(), memberState);
    return new Account(memberState);
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
