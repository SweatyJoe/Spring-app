package by.pms.repository;

import by.pms.entity.baseEntity.DramEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface DramRepository extends CrudRepository<DramEntity, Long> {
    Optional<DramEntity> findByNameLikeIgnoreCase(@NonNull String name);
}
