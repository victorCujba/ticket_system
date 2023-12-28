package it.webformat.ticketsystem.data.dto;

import it.webformat.ticketsystem.data.archetypes.Dto;
import it.webformat.ticketsystem.data.models.Badge;
import it.webformat.ticketsystem.data.models.Employee;
import it.webformat.ticketsystem.data.models.Project;
import it.webformat.ticketsystem.data.models.Team;
import it.webformat.ticketsystem.enums.EmployeeRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static it.webformat.ticketsystem.utility.DataConversionUtils.stringToLong;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PmDto implements Dto {

    private Long id;
    private String fullName;
    private String birthDate;
    private String employeeRole;
    private String teamId;
    private String projectId;
    private String badgeId;

    @Override
    public Employee toModel() {
        return Employee.builder()
                .id(id)
                .fullName(fullName)
                .birthDate(LocalDate.parse(birthDate))
                .employeeRole(EmployeeRole.valueOf(employeeRole))
                .team(Team.builder().id(stringToLong(teamId)).build())
                .project(Project.builder().id(stringToLong(projectId)).build())
                .badge(Badge.builder().id(stringToLong(badgeId)).build())
                .build();
    }
}
