package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.HbmAccidentRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class HbmAccidentService {
    private final HbmAccidentRepository accidentRepository;
    private final HbmAccidentTypeService accidentTypeService;
    private final HbmRuleService ruleService;

    public boolean add(Accident accident, String[] ids) {
        Optional<AccidentType> at = accidentTypeService.findById(accident.getType().getId());
        Set<Rule> ruleList = ruleService.findByIds(ids);
        if (ruleList.size() != ids.length || at.isEmpty()) {
            return false;
        }
        accident.setRules(ruleList);
        accident.setType(at.get());
        accidentRepository.add(accident);
        return true;
    }

    public Collection<Accident> getAll() {
        return accidentRepository.getAll();
    }

    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    public boolean replace(Accident accident, String[] ids) {
        Optional<AccidentType> at = accidentTypeService.findById(accident.getType().getId());
        System.out.println("тип нашли");
        Set<Rule> ruleList = ruleService.findByIds(ids);
        System.out.println("равила нашли");
        if (ruleList.size() != ids.length || at.isEmpty()) {
            return false;
        }
        accident.setRules(ruleList);
        accident.setType(at.get());
        return accidentRepository.replace(accident);
    }
}
