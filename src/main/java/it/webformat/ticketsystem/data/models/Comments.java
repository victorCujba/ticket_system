package it.webformat.ticketsystem.data.models;

import it.webformat.ticketsystem.data.archetypes.Dto;
import it.webformat.ticketsystem.data.archetypes.Model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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

    @Column
    private LocalDateTime localDateTime;

    @Override
    public Dto toDto() {
        return null;
    }
}
