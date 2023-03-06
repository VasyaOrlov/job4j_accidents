package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
@AllArgsConstructor
public class HbmRuleRepository {
    private static final String GET_RULES = "from Rule";
    private static final String FIND_BY_ID = "from Rule where id in :rIds";
    private final HbmCrudRepository crudRepository;

    public List<Rule> getRules() {
        return crudRepository.list(GET_RULES, Rule.class);
    }

    public Set<Rule> findByIds(List<Integer> ids) {
        return crudRepository.set(FIND_BY_ID, Rule.class, Map.of("rIds", ids));
    }
}
