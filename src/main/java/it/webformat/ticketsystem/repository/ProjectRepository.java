package it.webformat.ticketsystem.repository;

import it.webformat.ticketsystem.data.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
