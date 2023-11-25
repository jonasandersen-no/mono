package com.bjoggis.linode.adapter.out.database;

import com.bjoggis.linode.model.LinodeInstance;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

public interface LinodeRepository {


  @Transactional
  LinodeInstanceDbo saveLinodeInstanceToDb(LinodeInstance linodeInstance);

  Long getNextAvailableId();

  Optional<LinodeInstanceDbo> findByLinodeId(Long id);

  @Transactional(readOnly = true)
  Optional<LinodeInstanceDbo> findByLinodeIdAndNotDeleted(Long linodeId);

  void setAsDeleted(Long id);
}
