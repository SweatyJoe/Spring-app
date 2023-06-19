package by.pms.repository;

import by.pms.entity.baseEntity.PowerSupplyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PowerSupplyRepository extends CrudRepository<PowerSupplyEntity, Long> {
    Optional<PowerSupplyEntity> findByNameLikeIgnoreCase(String name);
}
