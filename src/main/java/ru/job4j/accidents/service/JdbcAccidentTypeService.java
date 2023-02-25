package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.JdbcAccidentTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class JdbcAccidentTypeService {
    private final JdbcAccidentTypeRepository typeRepository;

    public JdbcAccidentTypeService(JdbcAccidentTypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public List<AccidentType> getTypes() {
        return typeRepository.getTypes();
    }

    public Optional<AccidentType> findById(int id) {
        return typeRepository.findById(id);
    }
}
