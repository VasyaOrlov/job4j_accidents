package ru.job4j.accidents.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.PreparedStatement;
import java.util.*;

@Repository
public class JdbcAccidentRepository {
    private static final String ADD_ACCIDENT = "insert into accident (name, text, "
            + "address, accident_type_id) values (?, ?, ?, ?)";
    private static final String GET_ALL = "select "
            + "accident.id acc_id, "
            + "accident.name acc_name, "
            + "accident.text acc_text, "
            + "accident.address acc_address, "
            + "accident_type.id acc_type_id, "
            + "accident_type.name acc_type_name, "
            + "rule.id rule_id, "
            + "rule.name rule_name "
            + "from accident "
            + "join accident_type on accident.accident_type_id = accident_type.id "
            + "left join accident_rule on accident.id = accident_rule.accident_id "
            + "left join rule on accident_rule.rule_id = rule.id "
            + "order by accident.id asc";
    private static final String FIND_BY_ID = "select "
            + "accident.id acc_id, "
            + "accident.name acc_name, "
            + "accident.text acc_text, "
            + "accident.address acc_address, "
            + "accident_type.id acc_type_id, "
            + "accident_type.name acc_type_name, "
            + "rule.id rule_id, "
            + "rule.name rule_name "
            + "from accident "
            + "join accident_type on accident.accident_type_id = accident_type.id "
            + "left join accident_rule on accident.id = accident_rule.accident_id "
            + "left join rule on accident_rule.rule_id = rule.id "
            + "where accident.id = ?";
    private static final String UPDATE_ACCIDENT = "update accident set name = ?, text = ?, "
            + "address = ?, accident_type_id = ? where id = ?";
    private static final String DELETE_ACCIDENT_RULE = "delete from accident_rule where accident_id = ?";
    private static final String ADD_ACCIDENT_RULE = "insert into accident_rule (accident_id,"
            + " rule_id) values (?, ?)";
    private final JdbcTemplate jdbc;

    public JdbcAccidentRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void add(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(cn -> {
            PreparedStatement ps = cn.prepareStatement(
                    ADD_ACCIDENT,
                    new String[]{"id"});
                    ps.setString(1, accident.getName());
                    ps.setString(2, accident.getText());
                    ps.setString(3, accident.getAddress());
                    ps.setInt(4, accident.getType().getId());
                    return ps;
        }, keyHolder);
        if (keyHolder.getKey() != null) {
            int id = (Integer) keyHolder.getKey();
            accident.setId(id);
        }
        accident.getRules().stream().map(Rule::getId).
                forEach(idRule -> jdbc.update(ADD_ACCIDENT_RULE, accident.getId(), idRule));
    }

    public List<Accident> getAll() {
        return jdbc.query(GET_ALL,
                (rs) -> {
                    List<Accident> accidents = new ArrayList<>();
                    while (rs.next()) {
                        Accident accident = new Accident();
                        accident.setId(rs.getInt("acc_id"));
                        accident.setName(rs.getString("acc_name"));
                        accident.setText(rs.getString("acc_text"));
                        accident.setAddress(rs.getString("acc_address"));
                        accident.setType(new AccidentType(rs.getInt(
                                "acc_type_id"),
                                rs.getString("acc_type_name")));
                        accident.setRules(new HashSet<>());
                        Rule rule = new Rule(rs.getInt("rule_id"), rs.getString("rule_name"));
                        int id = accidents.indexOf(accident);
                        if (id == -1) {
                            accident.getRules().add(rule);
                            accidents.add(accident);
                        } else {
                            accidents.get(id).getRules().add(rule);
                        }
                    }
                    return accidents;
                });
    }

    public Optional<Accident> findById(int id) {
        Accident rsl = jdbc.query(FIND_BY_ID,
                (rs) -> {
            Accident accident = null;
            while (rs.next()) {
                if (accident == null) {
                    accident = new Accident();
                    accident.setId(rs.getInt("acc_id"));
                    accident.setName(rs.getString("acc_name"));
                    accident.setText(rs.getString("acc_text"));
                    accident.setAddress(rs.getString("acc_address"));
                    accident.setType(new AccidentType(rs.getInt(
                            "acc_type_id"),
                            rs.getString("acc_type_name")));
                    accident.setRules(new HashSet<>());
                }
                Rule rule = new Rule(rs.getInt("rule_id"), rs.getString("rule_name"));
                Set<Rule> rules = accident.getRules();
                rules.add(rule);
                accident.setRules(rules);
            }
            return accident;
            },
                id);
        return Optional.ofNullable(rsl);
    }

    public boolean replace(Accident accident) {
        int idAccident = accident.getId();
        jdbc.update(UPDATE_ACCIDENT,
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                idAccident);
        jdbc.update(DELETE_ACCIDENT_RULE, idAccident);
        accident.getRules().stream().map(Rule::getId).
                forEach(idRule -> jdbc.update(ADD_ACCIDENT_RULE, idAccident, idRule));
        return true;
    }
}
