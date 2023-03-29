package by.pms.repository;

import by.pms.entity.CpuEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CpuRepository extends CrudRepository<CpuEntity, Long> {
}
