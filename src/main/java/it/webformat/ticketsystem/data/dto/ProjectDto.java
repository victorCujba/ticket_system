package it.webformat.ticketsystem.data.dto;

import it.webformat.ticketsystem.data.archetypes.Dto;
import it.webformat.ticketsystem.data.models.Employee;
import it.webformat.ticketsystem.data.models.Project;
import it.webformat.ticketsystem.data.models.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDto implements Dto {

    private Long id;
    private String title;
    private String employeeId;
    private List<TeamDto> teamDtoList;

    @Override
    public Project toModel() {

        List<Team> teamList;
        if (!teamDtoList.isEmpty()) {
            teamList = teamDtoList.stream()
                    .map(TeamDto::toModel).toList();
        } else {
            teamList = new ArrayList<>();
        }

        return Project.builder()
                .id(id)
                .title(title)
                .employee(Employee.builder().id(Long.valueOf(employeeId)).build())
                .teams(teamList)
                .build();
    }
}
