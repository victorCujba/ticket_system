package it.webformat.ticketsystem.data.models;

import it.webformat.ticketsystem.data.archetypes.Model;
import it.webformat.ticketsystem.data.dto.EmployeeDto;
import it.webformat.ticketsystem.data.dto.LabourDto;
import it.webformat.ticketsystem.data.dto.ProjectDto;
import it.webformat.ticketsystem.data.dto.TeamDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static it.webformat.ticketsystem.utility.IdCheckUtils.getIdOrNull;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "project")
public class Project implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "project")
    private List<Employee> employees = new ArrayList<>();

    @OneToMany(mappedBy = "project")
    private List<Team> teams = new ArrayList<>();

    @OneToMany(mappedBy = "project")
    private List<Labour> labours = new ArrayList<>();

    @Column(name = "assigned_project_manager")
    private String assignedPM;

    @Override
    public ProjectDto toDto() {

        List<TeamDto> teamDtoList;
        if (!teams.isEmpty()) {
            teamDtoList = teams.stream()
                    .map(Team::toDto).toList();
        } else {
            teamDtoList = new ArrayList<>();
        }

        List<LabourDto> labourDtoList;
        if (!labours.isEmpty()) {
            labourDtoList = labours.stream()
                    .map(Labour::toDto).toList();
        } else {
            labourDtoList = new ArrayList<>();
        }

        List<EmployeeDto> employeeDtoList;
        if (!employees.isEmpty()) {
            employeeDtoList = employees.stream()
                    .map(Employee::toDto).toList();
        } else {
            employeeDtoList = new ArrayList<>();
        }

        return ProjectDto.builder()
                .id(id)
                .title(title)
                .employeeDtoList(employeeDtoList)
                .teamDtoList(teamDtoList)
                .assignedPM(assignedPM)
                .build();
    }
}
