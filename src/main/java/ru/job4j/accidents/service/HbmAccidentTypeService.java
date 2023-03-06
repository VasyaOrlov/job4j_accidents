package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.HbmAccidentTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HbmAccidentTypeService {

    private final HbmAccidentTypeRepository accidentTypeRepository;

    public List<AccidentType> getTypes() {
        return accidentTypeRepository.getTypes();
    }

    public Optional<AccidentType> findById(int id) {
        return accidentTypeRepository.findById(id);
    }
}
