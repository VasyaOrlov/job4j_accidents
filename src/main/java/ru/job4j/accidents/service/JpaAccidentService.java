package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.JpaAccidentRepository;

import java.util.*;

@Service
@AllArgsConstructor
public class JpaAccidentService {
    private final JpaAccidentRepository accidentRepository;
    private final JpaAccidentTypeService accidentTypeService;
    private final JpaRuleService ruleService;

    public boolean add(Accident accident, String[] ids) {
        Optional<AccidentType> at = accidentTypeService.findById(accident.getType().getId());
        Set<Rule> rules = ruleService.findByIds(ids);
        if (rules.size() != ids.length || at.isEmpty()) {
            return false;
        }
        accident.setType(at.get());
        accident.setRules(rules);
        accidentRepository.save(accident);
        return true;
    }

    public Collection<Accident> getAll() {
        return accidentRepository.findAllByOrderByIdAsc();
    }

    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    public boolean replace(Accident accident, String[] ids) {
        Optional<AccidentType> at = accidentTypeService.findById(accident.getType().getId());
        Set<Rule> rules = ruleService.findByIds(ids);
        if (rules.size() != ids.length || at.isEmpty()) {
            return false;
        }
        accident.setType(at.get());
        accident.setRules(rules);
        accidentRepository.save(accident);
        return true;
    }
}
