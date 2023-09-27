package com.bjoggis.mono.user.adapter.in;

import com.bjoggis.mono.user.application.AccountService;
import com.bjoggis.mono.user.domain.Account;
import com.bjoggis.mono.user.domain.AccountId;
import jakarta.validation.Valid;
import java.security.Principal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @PostMapping("/register")
  AccountResource register(@RequestBody @Valid RegisterRequest request) {
    Account account = service.registerAccount(request.username(), request.password());
    return AccountResource.from(account);
  }
}