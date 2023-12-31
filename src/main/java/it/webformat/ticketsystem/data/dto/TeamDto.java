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

import static it.webformat.ticketsystem.utility.DataConversionUtils.stringToLong;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamDto implements Dto {

    private Long id;
    private String name;
    private List<EmployeeDto> employeeDtoList;
    private String projectId;

    @Override
    public Team toModel() {

        List<Employee> employeeList;
        if (!employeeDtoList.isEmpty()) {
            employeeList = employeeDtoList.stream()
                    .map(EmployeeDto::toModel).toList();
        } else {
            employeeList = new ArrayList<>();
        }

        return Team.builder()
                .id(id)
                .name(name)
                .employeeList(employeeList)
                .project(Project.builder().id(stringToLong(projectId)).build())
                .build();
    }
}
