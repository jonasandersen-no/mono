package com.bjoggis.debug.command;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.shell.command.annotation.Command;

@Command(command = "spare")
public class Sparebank1Commands implements ApplicationListener<ApplicationReadyEvent> {

  private final Sparebank1Interface sparebank1Interface;

  public Sparebank1Commands(Sparebank1Interface sparebank1Interface) {
    this.sparebank1Interface = sparebank1Interface;
  }

    @Command(command = "accounts")
  public void getAccounts() {
    System.out.println(sparebank1Interface.getAccounts());
  }

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
  }
}
