package com.bjoggis.mono.openai.domain;

public class Account {

  private AccountId id;

  private String username;


  public Account() {
  }

  public Account(AccountState memberState) {
    this.id = memberState.accountId();
    this.username = memberState.username();
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public AccountId getId() {
    return id;
  }

  public void setId(AccountId id) {
    this.id = id;
  }

  public AccountState memento() {
    return new AccountState(id, username);
  }
}
