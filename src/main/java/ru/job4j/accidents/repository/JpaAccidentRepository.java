package ru.job4j.accidents.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Accident;

import java.util.List;

public interface JpaAccidentRepository extends CrudRepository<Accident, Integer> {
    @Query("from Accident a "
            + "order by a.id")
    List<Accident> findAllOrderByIdAsc();
}