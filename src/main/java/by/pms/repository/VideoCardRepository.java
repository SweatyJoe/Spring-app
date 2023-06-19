package by.pms.repository;

import by.pms.entity.baseEntity.VideoCardEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface VideoCardRepository extends CrudRepository<VideoCardEntity, Long> {
    Optional<VideoCardEntity> findByNameLikeIgnoreCase(String name);
}
