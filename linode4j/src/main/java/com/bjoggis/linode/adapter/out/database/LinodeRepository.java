package com.bjoggis.linode.adapter.out.database;

import com.bjoggis.linode.model.LinodeInstance;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

public interface LinodeRepository {


  @Transactional
  LinodeInstanceDbo saveLinodeInstanceToDb(LinodeInstance linodeInstance);

  Long getNextAvailableId();

  Optional<LinodeInstanceDbo> findByLinodeId(Long id);

  Optional<LinodeInstanceDbo> findByLinodeIdAndNotDeleted(Long linodeId);

  List<LinodeInstanceDbo> findAllWithStatusNotReady();

  List<LinodeInstanceDbo> findAllNotDeleted();

  void setAsDeleted(Long id);

  void updateStatus(Long id, String status);
}
