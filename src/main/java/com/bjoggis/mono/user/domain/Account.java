package com.bjoggis.mono.user.domain;

public class Account {

  private AccountId id;

  private String username;

  private String password;

  public Account() {
  }

  public Account(AccountState memberState) {
    this.id = memberState.accountId();
    this.username = memberState.username();
    this.password = memberState.password();
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public AccountId getId() {
    return id;
  }

  public void setId(AccountId id) {
    this.id = id;
  }

  public AccountState memento() {
    return new AccountState(id, username, password);
  }
}
