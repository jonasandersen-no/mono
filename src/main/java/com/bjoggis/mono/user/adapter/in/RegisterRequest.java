package com.bjoggis.mono.user.adapter.in;

public record RegisterRequest(String username, String password) {

  @Override
  public String toString() {
    return "RegisterRequest{" +
        "username='" + username + '\'' +
        ", password='<masked>'" +
        '}';
  }
}
