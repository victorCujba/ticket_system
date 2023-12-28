package it.webformat.ticketsystem.data.models;

import it.webformat.ticketsystem.data.archetypes.Model;
import it.webformat.ticketsystem.data.dto.CommentsDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comments implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "body", columnDefinition = "VARCHAR(5000)")
    private String body;

    @ManyToOne
    @JoinColumn(name = "id_labour", nullable = false)
    private Labour labour;

    @Column(name = "comment_date")
    private LocalDateTime dateOfComment;

    @Override
    public CommentsDto toDto() {
        return CommentsDto.builder()
                .id(id)
                .body(body)
                .labourId(String.valueOf(labour.getId()))
                .commentDate(String.valueOf(dateOfComment))
                .build();
    }
}
