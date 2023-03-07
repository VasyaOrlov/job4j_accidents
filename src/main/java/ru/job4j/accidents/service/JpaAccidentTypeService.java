package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.JpaAccidentTypeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class JpaAccidentTypeService {
    private final JpaAccidentTypeRepository accidentTypeRepository;

    public List<AccidentType> getTypes() {
        List<AccidentType> rsl = new ArrayList<>();
        accidentTypeRepository.findAll().forEach(rsl::add);
        return rsl;
    }

    public Optional<AccidentType> findById(int id) {
        return accidentTypeRepository.findById(id);
    }
}
