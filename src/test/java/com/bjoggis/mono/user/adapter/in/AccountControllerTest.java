package com.bjoggis.mono.user.adapter.in;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.bjoggis.mono.user.application.AccountService;
import com.bjoggis.mono.user.application.TestAccountServiceBuilder;
import com.bjoggis.mono.user.domain.Account;
import com.bjoggis.mono.user.domain.AccountBuilder;
import com.bjoggis.mono.user.domain.AccountId;
import org.junit.jupiter.api.Test;

class AccountControllerTest {

  @Test
  void getMe() {
    Account account = new AccountBuilder().withDefaults().withUsername("test").build();
    AccountService service = new TestAccountServiceBuilder().saveAccount(account).build();
    AccountController controller = new AccountController(service);

    AccountResource test = controller.getMe(new TestPrincipal("test"));

    assertEquals("test", test.username());
  }

  @Test
  void findAccountById() {
    Account account = new AccountBuilder().withDefaults().build();
    TestAccountServiceBuilder builder = new TestAccountServiceBuilder().saveAccount(account);
    AccountService service = builder.build();
    AccountController controller = new AccountController(service);

    AccountId lastAccountId = builder.getLastAccountId();
    AccountResource test = controller.getAccount(lastAccountId.id());

    assertEquals(lastAccountId.id(), test.id());
    assertEquals("username", test.username());

  }

  @Test
  void registerAccount() {
    AccountService service = new TestAccountServiceBuilder().build();
    AccountController controller = new AccountController(service);

    AccountResource test = controller.register(new RegisterRequest("username", "password"));

    assertNotNull(test.id());
    assertEquals("username", test.username());
  }
}