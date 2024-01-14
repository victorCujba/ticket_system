package it.webformat.ticketsystem.repository;

import it.webformat.ticketsystem.data.models.Employee;
import it.webformat.ticketsystem.data.models.Labour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LabourRepository extends JpaRepository<Labour, Long> {
    @Query(value = "SELECT * FROM labour l WHERE l.id_employee = :employeeId AND l.deadline < CURRENT_DATE", nativeQuery = true)
    List<Labour> findExpiredLabourByEmployee(@Param("employeeId") Long employeeId);
}
