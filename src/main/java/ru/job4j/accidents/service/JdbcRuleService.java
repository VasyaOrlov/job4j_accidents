package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.JdbcRuleRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class JdbcRuleService {
    private final JdbcRuleRepository ruleRepository;

    public JdbcRuleService(JdbcRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    public List<Rule> getRules() {
        return ruleRepository.getRules();
    }

    public Set<Rule> findByIds(String[] ids) {
        return ruleRepository.findByIds(Stream.of(ids).map(Integer::valueOf).toList());
    }

    public Set<Rule> findAllForAccident(int idAccident) {
        return ruleRepository.findRulesByIdAccident(idAccident);
    }
}
