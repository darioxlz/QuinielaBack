package com.josepadron.quinielaapp.repositories.user;

import com.josepadron.quinielaapp.models.user.User;
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
public class UserRepository implements CrudRepositoryI<User, Long>{
    private final NamedParameterJdbcOperations jdbc;
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(NamedParameterJdbcOperations jdbc, JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbc;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * from users;";
        return jdbc.query(sql, new UserRowMapper());
    }

    @Override
    public User findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = :id";
        SqlParameterSource paramMap = new MapSqlParameterSource("id", id);

        return jdbc.queryForObject(sql, paramMap, new UserRowMapper());
    }

    public Optional<User> findByEmail(String email) {
        try {
            String sql = "SELECT * FROM users WHERE email = :email";
            SqlParameterSource paramMap = new MapSqlParameterSource("email", email);

            return Optional.ofNullable(jdbc.queryForObject(sql, paramMap, new UserRowMapper()));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public User save(User user) {
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());

            return ps;
        }, keyHolder);

        Integer id = (Integer) keyHolder.getKeys().get("id");
        user.setId(id.longValue());

        return findById(user.getId());
    }

    @Override
    public void delete(User user) {
        this.deleteById(user.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM users WHERE id = :id;";
        SqlParameterSource paramMap = new MapSqlParameterSource("id", id);

        jdbc.update(sql, paramMap);
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE users SET name = :name, email = :email WHERE id = :id;";

        SqlParameterSource paramMap = new BeanPropertySqlParameterSource(user);
        jdbc.update(sql, paramMap);
    }
}
