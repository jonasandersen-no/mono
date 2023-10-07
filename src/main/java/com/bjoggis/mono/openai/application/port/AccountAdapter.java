package com.bjoggis.mono.openai.application.port;

import com.bjoggis.mono.openai.domain.Account;

public interface AccountAdapter {

  Account findAccountByUsername(String username);
}
