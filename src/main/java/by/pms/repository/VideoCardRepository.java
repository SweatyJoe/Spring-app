package by.pms.repository;

import by.pms.entity.VideoCardEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VideoCardRepository extends CrudRepository<VideoCardEntity, Long> {
    Optional<VideoCardEntity> findByNameLikeIgnoreCase(String name);
}
