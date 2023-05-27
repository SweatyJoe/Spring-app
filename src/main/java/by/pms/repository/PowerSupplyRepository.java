package by.pms.repository;

import by.pms.entity.baseEntity.PowerSupplyEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PowerSupplyRepository extends CrudRepository<PowerSupplyEntity, Long> {
    Optional<PowerSupplyEntity> findByNameLikeIgnoreCase(String name);
}
