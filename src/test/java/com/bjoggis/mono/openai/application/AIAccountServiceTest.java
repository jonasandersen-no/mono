package com.bjoggis.mono.openai.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.bjoggis.mono.openai.application.port.DummyAccountAdapter;
import com.bjoggis.mono.openai.domain.Account;
import org.junit.jupiter.api.Test;

class AIAccountServiceTest {

  @Test
  void testFindAccountByUsername() {
    DummyAccountAdapter accountAdapter = new DummyAccountAdapter();

    AIAccountService AIAccountService = new AIAccountService(accountAdapter);
    Account account = AIAccountService.findAccountByUsername("test").get();
    assertNotNull(account);
    assertEquals("test", account.getUsername());
  }
}