package it.webformat.ticketsystem.data.models;

import it.webformat.ticketsystem.data.archetypes.Model;
import it.webformat.ticketsystem.data.dto.CommentsDto;
import it.webformat.ticketsystem.data.dto.LabourDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "id_employee")
    private Employee employee;

    @OneToMany(mappedBy = "labour")
    private List<Comments> commentsList = new ArrayList<>();

    @Override
    public LabourDto toDto() {

        List<CommentsDto> commentsDtoList;
        if (!commentsList.isEmpty()) {
            commentsDtoList = commentsList.stream()
                    .map(Comments::toDto).toList();
        } else {
            commentsDtoList = new ArrayList<>();
        }

        return LabourDto.builder()
                .id(id)
                .desc(description)
                .deadline(String.valueOf(deadline))
                .devId(employee.getId().toString())
                .commentsDtoList(commentsDtoList)
                .build();
    }
}
