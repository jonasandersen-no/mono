package com.bjoggis.mono.openai.domain;

public class AccountBuilder {

  private final Account account;

  public AccountBuilder() {
    account = new Account();
  }

  public AccountBuilder withDefaults() {
    account.setUsername("username");
    return this;
  }

  public AccountBuilder withId(AccountId id) {
    account.setId(id);
    return this;
  }

  public AccountBuilder withUsername(String username) {
    account.setUsername(username);
    return this;
  }

  public Account build() {
    return account;
  }
}
