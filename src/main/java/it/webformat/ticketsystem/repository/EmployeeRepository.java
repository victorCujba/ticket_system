package it.webformat.ticketsystem.repository;

import it.webformat.ticketsystem.data.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
