package by.pms.repository;

import by.pms.entity.ImarketParseComponents;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImarketParseComponentsRepository extends CrudRepository<ImarketParseComponents, Long> {
    Optional<ImarketParseComponents> findByNameIgnoreCase(String name);
}
