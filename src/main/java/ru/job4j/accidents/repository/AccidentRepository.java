package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentRepository {

    private final Map<Integer, Accident> repository = new ConcurrentHashMap<>();
    private final AtomicInteger value = new AtomicInteger();

    public void add(Accident accident) {
        accident.setId(value.incrementAndGet());
        repository.put(accident.getId(), accident);
    }

    public Collection<Accident> getAll() {
        return repository.values();
    }

    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(repository.get(id));
    }

    public boolean replace(Accident accident) {
        try {
            repository.replace(accident.getId(), accident);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
