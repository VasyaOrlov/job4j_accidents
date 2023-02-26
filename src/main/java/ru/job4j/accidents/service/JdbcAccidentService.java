package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.JdbcAccidentRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class JdbcAccidentService {

    private final JdbcAccidentRepository accidentRepository;
    private final JdbcAccidentTypeService typeService;
    private final JdbcRuleService ruleService;

    public JdbcAccidentService(JdbcAccidentRepository accidentRepository,
                               JdbcAccidentTypeService typeService,
                               JdbcRuleService ruleService) {
        this.accidentRepository = accidentRepository;
        this.typeService = typeService;
        this.ruleService = ruleService;
    }

    public boolean add(Accident accident, String[] ids) {
        Set<Rule> ruleList = ruleService.findByIds(ids);
        Optional<AccidentType> at = typeService.findById(accident.getType().getId());
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
        Set<Rule> ruleList = ruleService.findByIds(ids);
        Optional<AccidentType> at = typeService.findById(accident.getType().getId());
        if (ruleList.size() != ids.length || at.isEmpty()) {
            return false;
        }
        accident.setRules(ruleList);
        accident.setType(at.get());
        return accidentRepository.replace(accident);
    }
}
