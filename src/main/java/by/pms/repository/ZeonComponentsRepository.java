package by.pms.repository;

import by.pms.entity.ZeonComponentsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ZeonComponentsRepository extends CrudRepository<ZeonComponentsEntity, Long> {
    List<ZeonComponentsEntity> findByNameLikeIgnoreCaseOrderByCostAsc(String name);
    Optional<ZeonComponentsEntity> findByNameLikeIgnoreCase(String name);
}
