package com.bjoggis.linode.adapter.out.database;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LinodeInstanceDboRepository extends JpaRepository<LinodeInstanceDbo, Long> {

  @Query("select max(l.id) from LinodeInstanceDbo l")
  Long getMaxId();

  @Query("select l from LinodeInstanceDbo l where l.linodeId = ?1")
  Optional<LinodeInstanceDbo> findByLinodeId(Long id);

  @Query("select l from LinodeInstanceDbo l where l.linodeId = ?1 and l.deleted = false")
  Optional<LinodeInstanceDbo> findByLinodeIdAndNotDeleted(Long linodeId);
}