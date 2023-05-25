package by.pms.repository;

import by.pms.entity.MotherboardEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface MotherboardRepository extends CrudRepository<MotherboardRepository, Long> {
    Optional<MotherboardEntity> findByNameLikeIgnoreCase(@NonNull String name);
}
