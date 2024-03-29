package it.webformat.ticketsystem.data.models;

import it.webformat.ticketsystem.data.archetypes.Model;
import it.webformat.ticketsystem.data.dto.CommentsDto;
import it.webformat.ticketsystem.data.dto.LabourDto;
import it.webformat.ticketsystem.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static it.webformat.ticketsystem.utility.IdCheckUtils.getIdOrNull;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "labour")
public class Labour implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description", columnDefinition = "VARCHAR(5000)")
    private String description;

    @Column(name = "deadline")
    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_status")
    private TaskStatus taskStatus;

    @ManyToOne
    @JoinColumn(name = "id_employee")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "id_project")
    private Project project;

    @OneToMany(mappedBy = "labour")
    private List<Comments> commentsList;

    @Override
    public LabourDto toDto() {

        List<CommentsDto> commentsDtoList;
        if (!(commentsList == null)) {
            commentsDtoList = commentsList.stream()
                    .map(Comments::toDto).toList();
        } else {
            commentsDtoList = new ArrayList<>();
        }


        return LabourDto.builder()
                .id(id)
                .desc(description)
                .deadline(String.valueOf(deadline))
                .employeeId(getIdOrNull(employee))
                .commentsDtoList(commentsDtoList)
                .projectId(getIdOrNull(project))
                .taskStatus(taskStatus)
                .build();
    }
}
