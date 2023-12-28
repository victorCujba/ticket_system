package it.webformat.ticketsystem.data.models;

import it.webformat.ticketsystem.data.archetypes.Model;
import it.webformat.ticketsystem.data.dto.EmployeeDto;
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
@Table(name = "team")
public class Team implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "team")
    private List<Employee> employeeList;

    @ManyToOne
    @JoinColumn(name = "id_project", nullable = false)
    private Project project;


    @Override
    public TeamDto toDto() {

        List<EmployeeDto> employeeDtoList;
        if (!employeeList.isEmpty()) {
            employeeDtoList = employeeList.stream()
                    .map(Employee::toDto).toList();
        } else {
            employeeDtoList = new ArrayList<>();
        }

        return TeamDto.builder()
                .id(id)
                .name(name)
                .employeeDtoList(employeeDtoList)
                .projectId(project.getId().toString())
                .build();
    }
}
