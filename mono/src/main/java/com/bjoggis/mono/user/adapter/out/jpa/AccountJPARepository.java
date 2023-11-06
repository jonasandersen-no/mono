package com.bjoggis.mono.user.adapter.out.jpa;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
interface AccountJPARepository extends JpaRepository<AccountDbo, Long> {

  @Query("select a from AccountDbo a where a.username = ?1")
  Optional<AccountDbo> findByUsername(String username);


}
