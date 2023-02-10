package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccidentRepository {

    private final Map<Integer, Accident> repository = new ConcurrentHashMap<>();

    public AccidentRepository() {
        Accident accident1 = new Accident(1, "name", "text", "address");
        Accident accident2 = new Accident(2, "NAME", "TEXT", "ADDRESS");
        repository.put(accident1.getId(), accident1);
        repository.put(accident2.getId(), accident2);
    }

    public void add(Accident accident) {
        repository.put(accident.getId(), accident);
    }

    public Collection<Accident> getAll() {
        return repository.values();
    }
}
