package it.webformat.ticketsystem.repository;

import it.webformat.ticketsystem.data.models.WorkRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRecordRepository extends JpaRepository<WorkRecord, Long> {
}
