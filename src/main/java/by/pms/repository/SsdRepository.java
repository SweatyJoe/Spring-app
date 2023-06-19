package by.pms.repository;

import by.pms.entity.baseEntity.SsdEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface SsdRepository extends CrudRepository<SsdEntity, Long> {
    Optional<SsdEntity> findByNameLikeIgnoreCase(String name);
}
