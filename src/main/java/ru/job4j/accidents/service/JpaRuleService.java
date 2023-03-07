package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.JpaRuleRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JpaRuleService {
    private final JpaRuleRepository ruleRepository;

    public List<Rule> getRules() {
        List<Rule> rules = new ArrayList<>();
        ruleRepository.findAll().forEach(rules::add);
        return rules;
    }

    public Set<Rule> findByIds(String[] ids) {
        Set<Rule> rules = new HashSet<>();
        ruleRepository
                .findAllById(Arrays.stream(ids).map(Integer::valueOf).collect(Collectors.toList()))
                .forEach(rules::add);
        return rules;
    }
}
