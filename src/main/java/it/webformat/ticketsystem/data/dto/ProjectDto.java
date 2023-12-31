package it.webformat.ticketsystem.data.dto;

import it.webformat.ticketsystem.data.archetypes.Dto;
import it.webformat.ticketsystem.data.models.Employee;
import it.webformat.ticketsystem.data.models.Labour;
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
    private List<EmployeeDto> employeeDtoList;
    private List<TeamDto> teamDtoList;
    private List<LabourDto> labourDtoList;
    private String assignedPM;

    @Override
    public Project toModel() {

        List<Team> teamList;
        if (!teamDtoList.isEmpty()) {
            teamList = teamDtoList.stream()
                    .map(TeamDto::toModel).toList();
        } else {
            teamList = new ArrayList<>();
        }

        List<Labour> labourList;
        if (!labourDtoList.isEmpty()) {
            labourList = labourDtoList.stream()
                    .map(LabourDto::toModel).toList();
        } else {
            labourList = new ArrayList<>();
        }

        List<Employee> employeeList;
        if (!employeeDtoList.isEmpty()) {
            employeeList = employeeDtoList.stream()
                    .map(EmployeeDto::toModel).toList();
        } else {
            employeeList = new ArrayList<>();
        }

        return Project.builder()
                .id(id)
                .title(title)
                .employees(employeeList)
                .teams(teamList)
                .labours(labourList)
                .assignedPM(assignedPM)
                .build();
    }
}
