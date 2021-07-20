package sber.cource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sber.cource.entity.Counteragent;

import java.util.List;
import java.util.Optional;

public interface CounteragentCrudRepository extends JpaRepository<Counteragent, Long> {
    void deleteByName(String name);
    Optional<Counteragent> findCounteragentByName(String name);
    Optional<List<Counteragent>> findCounteragentsByAccountNumberAndBik(String accountNumber, String bik);
}
