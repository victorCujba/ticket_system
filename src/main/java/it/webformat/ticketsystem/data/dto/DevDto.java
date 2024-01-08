package it.webformat.ticketsystem.data.dto;

import com.mysql.cj.util.StringUtils;
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

import static it.webformat.ticketsystem.utility.DataConversionUtils.stringToLong;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DevDto implements Dto {

    private Long id;
    private String fullName;
    private String birthDate;
    private String employeeRole;
    private String teamId;
    private String projectId;
    private String badgeId;
    private String labourId;
    private String referencedPM;

    @Override
    public Employee toModel() {


        return Employee.builder()
                .id(id)
                .fullName(fullName)
                .birthDate(LocalDate.parse(birthDate))
                .employeeRole(EmployeeRole.valueOf(employeeRole))
                .team(StringUtils.isNullOrEmpty(teamId) ? null : Team.builder().id(stringToLong(teamId)).build())
                .project(StringUtils.isNullOrEmpty(projectId) ? null : Project.builder().id(stringToLong(projectId)).build())
                .badge(StringUtils.isNullOrEmpty(badgeId) ? null : Badge.builder().id(stringToLong(badgeId)).build())
                .labour(StringUtils.isNullOrEmpty(labourId) ? null : Labour.builder().id(stringToLong(badgeId)).build())
                .referencedPM(referencedPM)
                .build();
    }
}
