package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.JdbcAccidentRepository;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        return accidentRepository.getAll().stream().peek(accident -> {
            Set<Rule> rules = ruleService.findAllForAccident(accident.getId());
            accident.setRules(rules);
        }).collect(Collectors.toList());
    }

    public Optional<Accident> findById(int id) {
        Optional<Accident> accident = accidentRepository.findById(id);
        if (accident.isPresent()) {
            Accident rsl = accident.get();
            Set<Rule> rules = ruleService.findAllForAccident(rsl.getId());
            if (rules.isEmpty()) {
                return Optional.empty();
            }
            rsl.setRules(rules);
            return Optional.of(rsl);
        }
        return accident;
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
