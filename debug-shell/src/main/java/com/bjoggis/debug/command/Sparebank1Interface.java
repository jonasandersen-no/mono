package com.bjoggis.debug.command;

import org.springframework.web.service.annotation.GetExchange;

public interface Sparebank1Interface {

  @GetExchange("/accounts")
  String getAccounts();
}
