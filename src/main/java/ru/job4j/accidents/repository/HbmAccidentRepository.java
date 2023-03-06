package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmAccidentRepository {
    private static final String GET_ALL = "select distinct a from Accident a "
            + "join fetch a.type "
            + "join fetch a.rules "
            + "order by a.id";
    private static final String FIND_BY_ID = "select distinct a from Accident a "
            + "join fetch a.type "
            + "join fetch a.rules "
            + "where a.id = :aId";
    private HbmCrudRepository crudRepository;

    public void add(Accident accident) {
        crudRepository.run(session -> session.save(accident));
    }

    public List<Accident> getAll() {
        return crudRepository.list(GET_ALL, Accident.class);
    }

    public Optional<Accident> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Accident.class, Map.of("aId", id));
    }

    public boolean replace(Accident accident) {
        return crudRepository.total(session -> session.merge(accident).equals(accident));
    }
}
