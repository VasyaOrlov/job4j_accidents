package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RuleRepository {

    private final List<Rule> rules = new ArrayList<>();

    public RuleRepository() {
        rules.add(new Rule(1, "Статья 1"));
        rules.add(new Rule(2, "Статья 2"));
        rules.add(new Rule(3, "Статья 3"));
    }

    public List<Rule> getRules() {
        return rules;
    }

    public Set<Rule> findByIds(String[] ids) {
        Set<Rule> rsl = new HashSet<>();
        for (String stringId : ids) {
            int id = Integer.parseInt(stringId);
            rsl.add(rules.get(id - 1));
        }
        return rsl;
    }
}
