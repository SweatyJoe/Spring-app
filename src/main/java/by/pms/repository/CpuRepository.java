package by.pms.repository;

import by.pms.entity.baseEntity.CpuEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CpuRepository extends CrudRepository<CpuEntity, Long> {
    Optional<CpuEntity> findBySocketLikeIgnoreCase(String socket);
    Optional<CpuEntity> findByNameLikeIgnoreCase(@NonNull String name);

    @Query("select c from CpuEntity c where upper(c.name) like upper(concat('%', ?1, '%'))")
    List<CpuEntity> findByNameContainsIgnoreCase(String name);
}
