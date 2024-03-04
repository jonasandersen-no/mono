package com.bjoggis.mono.user.application.port;

import com.bjoggis.mono.user.domain.Account;
import com.bjoggis.mono.user.domain.AccountId;
import java.util.Optional;

public interface AccountRepository {

  Account save(Account account);

  Optional<Account> findById(AccountId accountId);

  Optional<Account> findByUsername(String username);
}
