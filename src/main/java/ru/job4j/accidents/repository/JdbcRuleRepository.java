package ru.job4j.accidents.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class JdbcRuleRepository {
    private static final String GET_RULES = "select * from rule";
    private static final String FIND_BY_ID = "select * from rule where id = ?";
    private final JdbcTemplate jdbc;

    public JdbcRuleRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Rule> getRules() {
        return jdbc.query(GET_RULES,
                (rs, row) -> new Rule(rs.getInt("id"), rs.getString("name")));
    }

    public Set<Rule> findByIds(List<Integer> ids) {
        return ids.stream().map(idRule -> jdbc.queryForObject(FIND_BY_ID,
                (rs, row) -> new Rule(rs.getInt("id"), rs.getString("name")),
                idRule)).collect(Collectors.toSet());
    }
}
