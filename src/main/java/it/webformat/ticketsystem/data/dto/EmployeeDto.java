package it.webformat.ticketsystem.data.dto;

import it.webformat.ticketsystem.data.archetypes.Dto;
import it.webformat.ticketsystem.data.models.*;
import it.webformat.ticketsystem.enums.EmployeeRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDto implements Dto {

    private Long id;
    private String fullName;
    private String birthDate;
    private String employeeRole;
    private String teamId;
    private String projectId;
    private String badgeId;
    private List<LabourDto> labourDtoList;

    @Override
    public Employee toModel() {

        List<Labour> labourList;
        if (!labourDtoList.isEmpty()) {
            labourList = labourDtoList.stream()
                    .map(LabourDto::toModel).toList();
        } else {
            labourList = new ArrayList<>();
        }

        return Employee.builder()
                .id(id)
                .fullName(fullName)
                .birthDate(LocalDate.parse(birthDate))
                .employeeRole(EmployeeRole.valueOf(employeeRole))
                .team(Team.builder().id(Long.valueOf(teamId)).build())
                .project(Project.builder().id(Long.valueOf(projectId)).build())
                .badge(Badge.builder().id(Long.valueOf(badgeId)).build())
                .labourList(labourList)
                .build();
    }
}
