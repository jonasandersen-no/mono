package com.bjoggis.mono.openai.adapter.out.user;

import com.bjoggis.mono.openai.application.port.AccountAdapter;
import com.bjoggis.mono.openai.domain.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountAdapterImpl implements AccountAdapter {

  @Override
  public Account findAccountByUsername(String username) {
    return null;
  }
}
