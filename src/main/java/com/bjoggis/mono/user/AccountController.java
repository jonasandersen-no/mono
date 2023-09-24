package com.bjoggis.mono.user;

import jakarta.persistence.EntityNotFoundException;
import java.security.Principal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/account")
@RestController
class AccountController {

  private final AccountService service;
  private final AccountAssembler assembler;

  public AccountController(AccountService service, AccountAssembler assembler) {
    this.service = service;
    this.assembler = assembler;
  }

  @GetMapping("/me")
  AccountResource getMe(Principal principal) {
    return service.findAccountByUsername(principal.getName())
        .map(assembler)
        .orElseThrow(() -> new EntityNotFoundException("Account not found"));
  }

  @GetMapping("/{id}")
  AccountResource getAccount(@PathVariable Long id) {
    return service.findAccountById(id)
        .map(assembler)
        .orElseThrow(() -> new EntityNotFoundException("Account not found"));
  }
}
