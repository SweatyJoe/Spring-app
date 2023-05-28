package by.pms.repository;

import by.pms.entity.ProofficeComponentsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProofficeComponentsRepository extends CrudRepository<ProofficeComponentsEntity, Long> {
    Optional<ProofficeComponentsEntity> findByNameLikeIgnoreCase(String name);
}
