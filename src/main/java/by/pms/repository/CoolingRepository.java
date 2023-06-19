package by.pms.repository;

import by.pms.entity.baseEntity.CoolingEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoolingRepository extends CrudRepository<CoolingEntity, Long> {
    Optional<CoolingEntity> findByNameLikeIgnoreCase(String name);
}
