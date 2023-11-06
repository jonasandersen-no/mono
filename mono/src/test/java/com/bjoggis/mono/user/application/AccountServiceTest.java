package com.bjoggis.mono.user.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.bjoggis.mono.user.application.port.AccountRepository;
import com.bjoggis.mono.user.domain.Account;
import com.bjoggis.mono.user.domain.AccountBuilder;
import com.bjoggis.mono.user.domain.AccountId;
import org.junit.jupiter.api.Test;

class AccountServiceTest {

  @Test
  void registerAccount() {
    TestAccountServiceBuilder builder = new TestAccountServiceBuilder();

    AccountService service = builder.build();

    Account account = service.registerAccount("username", "password");

    assertNotNull(account.getId());
    assertEquals("username", account.getUsername());
    assertEquals("*".repeat(16), account.getPassword());
  }

  @Test
  void findAccountById() {
    Account account = new AccountBuilder().withDefaults().build();

    TestAccountServiceBuilder builder = new TestAccountServiceBuilder()
        .saveAccount(account);
    AccountRepository repository = builder.getAccountRepository();
    AccountService accountService = builder.build();

    AccountId lastAccountId = builder.getLastAccountId();

    accountService.findAccountById(lastAccountId);

    Account foundAccount = repository.findById(lastAccountId).get();

    assertNotNull(foundAccount);
    assertEquals(lastAccountId, foundAccount.getId());
  }

  @Test
  void findAccountByUsername() {
    Account account = new AccountBuilder().withDefaults().withUsername("other").build();

    TestAccountServiceBuilder builder = new TestAccountServiceBuilder()
        .saveAccount(account);
    AccountRepository repository = builder.getAccountRepository();
    AccountService accountService = builder.build();

    String username = account.getUsername();
    accountService.findAccountByUsername(username);

    Account foundAccount = repository.findByUsername(username).get();

    assertEquals(username, foundAccount.getUsername());
  }
}