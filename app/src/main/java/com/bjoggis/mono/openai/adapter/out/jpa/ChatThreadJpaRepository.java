package com.bjoggis.mono.openai.adapter.out.jpa;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ChatThreadJpaRepository extends CrudRepository<ChatThreadDbo, Long> {

  @Query("select c from ChatThreadDbo c where c.accountId = ?1 order by c.id desc")
  List<ChatThreadDbo> findByAccountId(Long id);
}
