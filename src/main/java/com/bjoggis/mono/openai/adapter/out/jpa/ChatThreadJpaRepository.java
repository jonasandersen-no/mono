package com.bjoggis.mono.openai.adapter.out.jpa;

import org.springframework.data.repository.CrudRepository;

public interface ChatThreadJpaRepository extends CrudRepository<ChatThreadDbo, Long> {

}
