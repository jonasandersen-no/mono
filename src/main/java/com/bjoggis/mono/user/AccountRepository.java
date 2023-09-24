package com.bjoggis.mono.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Long> {

  @Query("select a from Account a where a.username = ?1")
  Optional<Account> findByUsername(String username);


}
