package com.bjoggis.mono.user.adapter.in;

import com.bjoggis.mono.user.application.AccountService;
import com.bjoggis.mono.user.domain.Account;
import com.bjoggis.mono.user.domain.AccountId;
import java.security.Principal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/account")
@RestController
class AccountController {

  private final AccountService service;

  public AccountController(AccountService service) {
    this.service = service;
  }

  @GetMapping("/me")
  AccountResource getMe(Principal principal) {
    Account account = service.findAccountByUsername(principal.getName());
    return AccountResource.from(account);
  }

  @GetMapping("/{id}")
  AccountResource getAccount(@PathVariable Long id) {
    Account account = service.findAccountById(AccountId.of(id));
    return AccountResource.from(account);
  }
}