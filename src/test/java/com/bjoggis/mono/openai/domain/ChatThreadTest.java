package com.bjoggis.mono.openai.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ChatThreadTest {

  @Test
  void isOwnerReturnTrueIfAccountIdsMatch() {
    ChatThread chatThread = new ChatThread();
    chatThread.setAccountId(AccountId.of(1L));

    boolean equals = chatThread.isOwner(new AccountId(1L));
    assertTrue(equals);
  }

  @Test
  void isOwnerReturnsFalseIfAccountIdsDontMatch() {
    ChatThread chatThread = new ChatThread();
    chatThread.setAccountId(AccountId.of(1L));

    boolean equals = chatThread.isOwner(AccountId.of(2L));
    assertFalse(equals);
  }

  @Test
  void isOwnerReturnsFalseIfAccountOnThreadIsNull() {
    ChatThread chatThread = new ChatThread();

    boolean equals = chatThread.isOwner(AccountId.of(1L));
    assertFalse(equals);
  }
}