package it.webformat.ticketsystem.repository;

import it.webformat.ticketsystem.data.models.Labour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabourRepository extends JpaRepository<Labour, Long> {
}
