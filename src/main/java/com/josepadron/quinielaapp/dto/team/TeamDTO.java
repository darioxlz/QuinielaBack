package com.josepadron.quinielaapp.dto.team;
import com.josepadron.quinielaapp.models.team.Team;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class TeamDTO {
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 150)
    private String name;

    @NotNull
    @NotBlank
    private int countryId;

    public TeamDTO() {}

    public TeamDTO(Long id, String name, int countryId) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
    }

    public static TeamDTO toDTO(Team team) {
        return new TeamDTO(team.getId(), team.getName(), team.getCountryId());
    }

    public static Team toModel(TeamDTO teamDTO) {
        return new Team(teamDTO.getId(), teamDTO.getName(), teamDTO.getCountryId());
    }
}
