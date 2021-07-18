package sber.cource.repository;

import org.springframework.data.repository.CrudRepository;
import sber.cource.entity.Counteragent;

import java.util.Optional;

public interface CounteragentCrudRepository extends CrudRepository<Counteragent, Long> {
    Optional<Counteragent> findCounteragentByName(String name);
    void deleteByName(String name);
}
