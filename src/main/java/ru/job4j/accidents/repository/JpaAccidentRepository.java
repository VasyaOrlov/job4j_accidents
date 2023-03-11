package ru.job4j.accidents.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

public interface JpaAccidentRepository extends CrudRepository<Accident, Integer> {
    List<Accident> findAllByOrderByIdAsc();
    @Query(value = "select distinct a from Accident a "
            + "join fetch a.type "
            + "join fetch a.rules "
            + "where a.id = ?1")
    Optional<Accident> findById(int id);
}