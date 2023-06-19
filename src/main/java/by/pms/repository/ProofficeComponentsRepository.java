package by.pms.repository;

import by.pms.entity.ProofficeComponentsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ProofficeComponentsRepository extends CrudRepository<ProofficeComponentsEntity, Long> {
    Optional<ProofficeComponentsEntity> findByNameLikeIgnoreCase(String name);
}
