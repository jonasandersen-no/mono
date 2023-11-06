package com.bjoggis.mono.user.adapter.in;

import java.security.Principal;
import javax.security.auth.Subject;

public class TestPrincipal implements Principal {

  private final String name;

  public TestPrincipal(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean implies(Subject subject) {
    return Principal.super.implies(subject);
  }
}
