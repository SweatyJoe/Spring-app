package by.pms.repository;

import by.pms.entity.SsdEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SsdRepository extends CrudRepository<SsdEntity, Long> {
    Optional<SsdEntity> findByNameLikeIgnoreCase(String name);
}
