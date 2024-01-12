package it.webformat.ticketsystem.repository;

import it.webformat.ticketsystem.data.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Team findByName(String name);
}
