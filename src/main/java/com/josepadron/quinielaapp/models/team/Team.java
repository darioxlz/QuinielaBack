package com.josepadron.quinielaapp.models.team;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "teams")
public class Team {
    @Id
    private Long id;
    private String name;
    private int countryId;

    public Team() {}

    public Team(Long id, String name, int countryId) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
    }

    public Team(String name, int countryId) {
        this.name = name;
        this.countryId = countryId;
    }
}
