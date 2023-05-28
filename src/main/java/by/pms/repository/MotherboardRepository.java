package by.pms.repository;

import by.pms.entity.baseEntity.MotherboardEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MotherboardRepository extends CrudRepository<MotherboardEntity, Long> {
    Optional<MotherboardEntity> findByNameLikeIgnoreCase(String name);

}
