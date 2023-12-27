package it.webformat.ticketsystem.data.models;

import it.webformat.ticketsystem.data.archetypes.Dto;
import it.webformat.ticketsystem.data.archetypes.Model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    public Dto toDto() {
        return null;
    }
}
