package com.bjoggis.mono.openai.adapter.out.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bjoggis.mono.openai.domain.AccountId;
import com.bjoggis.mono.openai.domain.ChatThread;
import com.bjoggis.mono.openai.domain.ChatThreadId;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(ChatThreadRepositoryAdapter.class)
class ChatThreadRepositoryAdapterTest {

  @Autowired
  ChatThreadJpaRepository repository;

  @Autowired
  ChatThreadRepositoryAdapter adapter;

  @Test
  void returnsChatThreadWhenSaveIsCalled() {
    ChatThread chatThread = new ChatThread();
    chatThread.setAccountId(AccountId.of(1L));

    ChatThread saved = adapter.save(chatThread);

    assertEquals(AccountId.of(1L), saved.getAccountId());
    assertNotNull(saved.getChatThreadId());
  }

  @Test
  void returnChatThreadWhenFindByIdIsCalled() {
    ChatThreadDbo chatThreadDbo = new ChatThreadDbo();
    chatThreadDbo.setAccountId(1L);
    ChatThreadDbo saved = repository.save(chatThreadDbo);

    Optional<ChatThread> foundChatThread = adapter.findById(ChatThreadId.of(saved.getId()));

    assertTrue(foundChatThread.isPresent());
    assertEquals(AccountId.of(1L), foundChatThread.get().getAccountId());
    assertNotNull(foundChatThread.get().getChatThreadId());
  }

  @Test
  void deletesChatThreadWhenDeleteByIdIsCalled() {
    ChatThreadDbo chatThreadDbo = new ChatThreadDbo();
    chatThreadDbo.setAccountId(1L);
    ChatThreadDbo saved = repository.save(chatThreadDbo);

    adapter.deleteById(ChatThreadId.of(saved.getId()));

    Optional<ChatThreadDbo> notFound = repository.findById(saved.getId());

    assertTrue(notFound.isEmpty());
  }

  @Test
  void throwsWhenTryingToDeleteChatThreadThatDoesntExist() {
    assertThrows(IllegalArgumentException.class, () -> adapter.deleteById(ChatThreadId.of(9999L)));
  }
}