package com.josepadron.quinielaapp.repositories.team;

import com.josepadron.quinielaapp.models.team.Team;
import com.josepadron.quinielaapp.repositories.CrudRepositoryI;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class TeamRepository implements CrudRepositoryI<Team, Long> {
    private final NamedParameterJdbcOperations jdbc;
    private final JdbcTemplate jdbcTemplate;

    public TeamRepository(NamedParameterJdbcOperations jdbc, JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbc;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Team> findAll() {
        String sql = "SELECT * from teams;";
        return jdbc.query(sql, new TeamRowMapper());
    }

    @Override
    public Optional<Team> findById(Long id) {
        try {
            String sql = "SELECT * FROM teams WHERE id = :id";
            SqlParameterSource paramMap = new MapSqlParameterSource("id", id);

            return Optional.ofNullable(jdbc.queryForObject(sql, paramMap, new TeamRowMapper()));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Team save(Team team) {
        String sql = "INSERT INTO teams (name, country_id) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, team.getName());
            ps.setInt(2, team.getCountryId());

            return ps;
        }, keyHolder);

        Integer id = (Integer) keyHolder.getKeys().get("id");
        team.setId(id.longValue());

        return team;
    }

    @Override
    public void delete(Team team) {
        this.deleteById(team.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM teams WHERE id = :id;";
        SqlParameterSource paramMap = new MapSqlParameterSource("id", id);

        jdbc.update(sql, paramMap);
    }

    @Override
    public void update(Team team) {
        String sql = "UPDATE teams SET name = :name, country_id = :countryId WHERE id = :id;";

        SqlParameterSource paramMap = new BeanPropertySqlParameterSource(team);
        jdbc.update(sql, paramMap);
    }
}
