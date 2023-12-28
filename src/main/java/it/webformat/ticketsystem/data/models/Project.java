package it.webformat.ticketsystem.data.models;

import it.webformat.ticketsystem.data.archetypes.Model;
import it.webformat.ticketsystem.data.dto.ProjectDto;
import it.webformat.ticketsystem.data.dto.TeamDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToOne
    @JoinColumn(name = "id_employee", referencedColumnName = "id")
    private Employee employee;

    @OneToMany(mappedBy = "project")
    private List<Team> teams = new ArrayList<>();

    @Override
    public ProjectDto toDto() {

        List<TeamDto> teamDtoList;
        if (!teams.isEmpty()) {
            teamDtoList = teams.stream()
                    .map(Team::toDto).toList();
        } else {
            teamDtoList = new ArrayList<>();
        }

        return ProjectDto.builder()
                .id(id)
                .title(title)
                .employeeId(employee.getId().toString())
                .teamDtoList(teamDtoList)
                .build();
    }
}
