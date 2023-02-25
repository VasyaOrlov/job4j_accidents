package ru.job4j.accidents.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcAccidentTypeRepository {
    private static final String GET_TYPES = "select * from accident_type";
    private static final String GET_TYPE_BY_ID = "select * from accident_type where id = ?";
    private final JdbcTemplate jdbc;

    public JdbcAccidentTypeRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<AccidentType> getTypes() {
        return jdbc.query(GET_TYPES,
                (rs, row) -> new AccidentType(rs.getInt("id"), rs.getString("name")));
    }

    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(jdbc.queryForObject(GET_TYPE_BY_ID,
                (rs, row) -> new AccidentType(rs.getInt("id"), rs.getString("name")),
                id));
    }
}
