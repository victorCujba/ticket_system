package it.webformat.ticketsystem.repository;

import it.webformat.ticketsystem.data.models.Employee;
import it.webformat.ticketsystem.enums.EmployeeRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Long countByEmployeeRole(EmployeeRole employeeRole);

    List<Employee> getByEmployeeRole(EmployeeRole employeeRole);
}
