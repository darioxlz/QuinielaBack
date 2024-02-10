package com.josepadron.quinielaapp.repositories.team;

import com.josepadron.quinielaapp.models.team.Team;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamRowMapper implements RowMapper<Team> {
    @Override
    public Team mapRow(ResultSet rs, int rowNum) throws SQLException {
        Team user = new Team();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setCountryId(rs.getInt("country_id"));

        return user;
    }
}
