package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;
import java.util.List;
import java.util.Set;

@Service
public class RuleService {

    private final RuleRepository ruleRepository;

    public RuleService(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    public List<Rule> getRules() {
        return ruleRepository.getRules();
    }

    public Set<Rule> findByIds(String[] ids) {
        return ruleRepository.findByIds(ids);
    }
}
