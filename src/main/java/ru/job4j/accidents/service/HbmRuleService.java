package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.HbmRuleRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class HbmRuleService {
    private final HbmRuleRepository ruleRepository;

    public List<Rule> getRules() {
        return ruleRepository.getRules();
    }

    public Set<Rule> findByIds(String[] ids) {
        return ruleRepository.findByIds(Stream.of(ids).map(Integer::valueOf).toList());
    }
}