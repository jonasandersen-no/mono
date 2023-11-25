package com.bjoggis.linode.adapter.out.database;

import com.bjoggis.linode.model.LinodeInstance;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

public class LinodeRepositoryImpl implements LinodeRepository {

  private final LinodeInstanceDboRepository instanceRepository;

  public LinodeRepositoryImpl(LinodeInstanceDboRepository instanceRepository) {
    this.instanceRepository = instanceRepository;
  }

  @Transactional
  @Override
  public LinodeInstanceDbo saveLinodeInstanceToDb(LinodeInstance linodeInstance) {
    LinodeInstanceDbo dbo = new LinodeInstanceDbo();

    dbo.setLinodeId(linodeInstance.id());
    dbo.setLabel(linodeInstance.label());
    dbo.setCreated(Date.from(linodeInstance.created().atZone(ZoneId.systemDefault()).toInstant()));
    dbo.setUpdated(Date.from(linodeInstance.updated().atZone(ZoneId.systemDefault()).toInstant()));
    dbo.setRegion(linodeInstance.region());
    dbo.setType(linodeInstance.type());
    if (!linodeInstance.ipv4().isEmpty()) {
      dbo.setIpv4(linodeInstance.ipv4().get(0));
    }
    dbo.setImage(linodeInstance.image());
    dbo.setMemory(linodeInstance.specs().memory());
    dbo.setCpus(linodeInstance.specs().vcpus());
    dbo.setStatus(linodeInstance.status());

    return instanceRepository.save(dbo);
  }

  @Override
  @Transactional(readOnly = true)
  public Long getNextAvailableId() {
    return instanceRepository.getMaxId() + 1;
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<LinodeInstanceDbo> findByLinodeId(Long linodeId) {
    return instanceRepository.findByLinodeId(linodeId);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<LinodeInstanceDbo> findByLinodeIdAndNotDeleted(Long linodeId) {
    return instanceRepository.findByLinodeIdAndNotDeleted(linodeId);
  }

  @Override
  @Transactional
  public void setAsDeleted(Long id) {
    Optional<LinodeInstanceDbo> dbo = instanceRepository.findById(id);

    if (dbo.isPresent()) {
      LinodeInstanceDbo linodeInstanceDbo = dbo.get();
      linodeInstanceDbo.setDeleted(true);
      linodeInstanceDbo.setDeletedAt(new Date());
      instanceRepository.save(linodeInstanceDbo);
    }
  }
}
