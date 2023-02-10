package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AccidentTypeService {

    private final AccidentTypeRepository typeRepository;

    public AccidentTypeService(AccidentTypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public List<AccidentType> getTypes() {
        return typeRepository.getTypes();
    }

    public Optional<AccidentType> findById(int id) {
        return typeRepository.findById(id);
    }
}
