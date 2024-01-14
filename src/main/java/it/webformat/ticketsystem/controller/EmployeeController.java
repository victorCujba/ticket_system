package it.webformat.ticketsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.webformat.ticketsystem.data.dto.EmployeeDto;
import it.webformat.ticketsystem.data.models.Employee;
import it.webformat.ticketsystem.enums.EmployeeRole;
import it.webformat.ticketsystem.exceptions.IdMustBeNullException;
import it.webformat.ticketsystem.exceptions.IdMustNotBeNullException;
import it.webformat.ticketsystem.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
@SecurityRequirement(name = "authentication")
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    @GetMapping("/v1")
    @Operation(description = """
            This method is used to retrieve all Employees from database<br>
                """)
    public List<EmployeeDto> getAllEmployees() {
        return employeeService.findAll().stream().map(Employee::toDto).toList();
    }

    @PostMapping("/v1")
    @Operation(description = """
            This method is used to insert new Employee<br>
            """)
    public EmployeeDto createNewEmployee(@RequestParam String fullName, @RequestParam String birthDate, @RequestParam EmployeeRole employeeRole) {
        try {
            Employee employee = Employee.builder()
                    .fullName(fullName)
                    .birthDate(LocalDate.parse(birthDate))
                    .employeeRole(employeeRole)
                    .build();
            return employeeService.insert(employee).toDto();
        } catch (IdMustBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    @PutMapping("/v1")
    @Operation(description = """
            This method is used to update existing  Employee<br>
            """)
    public EmployeeDto updateEmployee(@RequestBody EmployeeDto employeeDto) {
        try {
            Employee employee = employeeDto.toModel();
            return employeeService.update(employee).toDto();
        } catch (IdMustNotBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    @DeleteMapping("/v1/{id}")
    @Operation(description = """
            This method is used to delete Employee by id<br>
            """)
    public Boolean deleteEmployee(@PathVariable("id") Long id) {
        return employeeService.deleteById(id);
    }

    @GetMapping("/v1/{id}")
    @Operation(description = """
            This method is used to find Employee by id<br>
            """)
    public EmployeeDto getEmployeeById(@PathVariable("id") Long id) {
        return employeeService.findById(id).toDto();
    }


}
