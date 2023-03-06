package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmAccidentTypeRepository {

    private static final String GET_TYPES = "from AccidentType";
    private static final String FIND_BY_ID = "from AccidentType where id = :atId";
    private HbmCrudRepository crudRepository;

    public List<AccidentType> getTypes() {
        return crudRepository.list(GET_TYPES, AccidentType.class);
    }

    public Optional<AccidentType> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, AccidentType.class, Map.of("atId", id));
    }
}
