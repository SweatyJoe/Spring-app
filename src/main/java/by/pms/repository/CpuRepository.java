package by.pms.repository;

import by.pms.entity.CpuEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CpuRepository extends CrudRepository<CpuEntity, Long> {
    Optional<CpuEntity> findByNameLikeIgnoreCase(@NonNull String name);
}
