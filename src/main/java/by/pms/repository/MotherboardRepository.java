package by.pms.repository;

import by.pms.entity.baseEntity.MotherboardEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MotherboardRepository extends CrudRepository<MotherboardEntity, Long> {
    List<MotherboardEntity> findByMemoryTypeLikeIgnoreCase(String memoryType);
    List<MotherboardEntity> findBySocketLikeIgnoreCase(String socket);
    Optional<MotherboardEntity> findByNameLikeIgnoreCase(String name);
}