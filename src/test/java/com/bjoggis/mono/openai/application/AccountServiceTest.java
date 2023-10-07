package com.bjoggis.mono.openai.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.bjoggis.mono.openai.application.port.DummyAccountAdapter;
import com.bjoggis.mono.openai.domain.Account;
import org.junit.jupiter.api.Test;

class AccountServiceTest {

  @Test
  void testFindAccountByUsername() {
    DummyAccountAdapter accountAdapter = new DummyAccountAdapter();

    AccountService accountService = new AccountService(accountAdapter);
    Account account = accountService.findAccountByUsername("test");
    assertNotNull(account);
    assertEquals("test", account.getUsername());
  }
}