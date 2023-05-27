package by.pms.repository;

import by.pms.entity.ZeonComponentsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ZeonComponentsRepository extends CrudRepository<ZeonComponentsEntity, Long> {
    List<ZeonComponentsEntity> findByNameLikeIgnoreCaseOrderByCostAsc(String name);
    Optional<ZeonComponentsEntity> findByNameLikeIgnoreCase(String name);
}
