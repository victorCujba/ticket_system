package it.webformat.ticketsystem.repository;

import it.webformat.ticketsystem.data.models.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
}
