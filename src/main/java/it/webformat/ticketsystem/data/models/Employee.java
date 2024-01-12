package it.webformat.ticketsystem.data.models;

import it.webformat.ticketsystem.data.archetypes.*;
import it.webformat.ticketsystem.data.dto.*;
import it.webformat.ticketsystem.enums.EmployeeRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static it.webformat.ticketsystem.utility.DataConversionUtils.employeeRoleToString;
import static it.webformat.ticketsystem.utility.IdCheckUtils.getIdOrNull;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee implements ProjectManagerModel, DeveloperModel, ChiefExecutiveOfficerModel, Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_role")
    private EmployeeRole employeeRole;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_team")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "id_project")
    private Project project;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_badge", referencedColumnName = "id")
    private Badge badge;

    @OneToMany(mappedBy = "employee")
    private List<Labour> labourList;

    @Column(name = "referenced_project_manager")
    private String referencedPM;

    @Override
    public CeoDto toCeoDto() {
        return CeoDto.builder()
                .id(id)
                .fullName(fullName)
                .birthDate(String.valueOf(birthDate))
                .employeeRole(String.valueOf(employeeRole))
                .badgeId(getIdOrNull(badge))
                .build();
    }

    @Override
    public DevDto toDevDto() {

        List<LabourDto> labourDtoList;
        if (!(labourList == null)) {
            labourDtoList = labourList.stream()
                    .map(Labour::toDto).toList();
        } else {
            labourDtoList = new ArrayList<>();
        }


        return DevDto.builder()
                .id(id)
                .fullName(fullName)
                .birthDate(String.valueOf(birthDate))
                .employeeRole(String.valueOf(employeeRole))
                .teamId(getIdOrNull(team))
                .projectId(getIdOrNull(project))
                .badgeId(getIdOrNull(badge))
                .labourDtoList(labourDtoList)
                .referencedPM(referencedPM)
                .build();
    }

    @Override
    public PmDto toPmDto() {
        return PmDto.builder()
                .id(id)
                .fullName(fullName)
                .birthDate(String.valueOf(birthDate))
                .employeeRole(String.valueOf(employeeRole))
                .teamId(getIdOrNull(team))
                .projectId(getIdOrNull(project))
                .badgeId(getIdOrNull(badge))
                .refPM(referencedPM)
                .build();
    }

    @Override
    public EmployeeDto toDto() {

        List<LabourDto> labourDtoList;
        if (!(labourList == null)) {
            labourDtoList = labourList.stream()
                    .map(Labour::toDto).toList();
        } else {
            labourDtoList = new ArrayList<>();
        }


        return EmployeeDto.builder()
                .id(id)
                .fullName(fullName)
                .birthDate(String.valueOf(birthDate))
                .employeeRole(employeeRoleToString(employeeRole))
                .teamId(getIdOrNull(team))
                .projectId(getIdOrNull(project))
                .badgeId(getIdOrNull(badge))
                .labourDtoList(labourDtoList)
                .build();
    }
}
