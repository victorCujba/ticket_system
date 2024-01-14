package it.webformat.ticketsystem.service.impl;

import it.webformat.ticketsystem.data.models.Employee;
import it.webformat.ticketsystem.enums.EmployeeRole;
import it.webformat.ticketsystem.exceptions.IdMustBeNullException;
import it.webformat.ticketsystem.exceptions.IdMustNotBeNullException;
import it.webformat.ticketsystem.repository.EmployeeRepository;
import it.webformat.ticketsystem.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee insert(Employee employee) {
        if (employee.getId() != null) {
            throw new IdMustBeNullException();
        }
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(Employee employee) {
        if (employee.getId() == null) {
            throw new IdMustNotBeNullException();
        }
        return employeeRepository.save(employee);
    }

    @Override
    public Boolean deleteById(Long id) {
        employeeRepository.deleteById(id);
        return employeeRepository.findById(id).isEmpty();
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElse(Employee.builder().build());
    }

    @Override
    public Long getCountCeoEmployee() {
        return employeeRepository.countByEmployeeRole(EmployeeRole.CEO);
    }

    @Override
    public List<Employee> getEmployeeByRole(EmployeeRole employeeRole) {
        return employeeRepository.getByEmployeeRole(employeeRole);
    }

    @Override
    public Employee findByFullName(String fullName) {
        return employeeRepository.findByFullName(fullName);
    }
}
