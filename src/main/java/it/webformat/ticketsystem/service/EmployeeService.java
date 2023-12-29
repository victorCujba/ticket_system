package it.webformat.ticketsystem.service;


import it.webformat.ticketsystem.data.models.Employee;
import it.webformat.ticketsystem.enums.EmployeeRole;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();

    Employee insert(Employee employee);

    Employee update(Employee employee);

    Boolean deleteById(Long id);

    Employee findById(Long id);

    Long getCountCeoEmployee();

    List<Employee> getEmployeeByRole(EmployeeRole employeeRole);

    Employee findByFullName(String fullName);
}
