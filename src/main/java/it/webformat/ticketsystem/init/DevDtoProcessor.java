package it.webformat.ticketsystem.init;

import it.webformat.ticketsystem.data.dto.DevDto;
import it.webformat.ticketsystem.data.models.Employee;
import it.webformat.ticketsystem.service.EmployeeService;

import java.util.List;

public class DevDtoProcessor {

    private final EmployeeService employeeService;

    public DevDtoProcessor(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void processDevDtos(List<DevDto> developerDtoList) {
        for (DevDto developerDtos : developerDtoList) {
            String fullName = developerDtos.getFullName();

            Employee existingEmployee = employeeService.findByFullName(fullName);

            if (existingEmployee != null) {
                employeeService.update(existingEmployee);
            } else {
                employeeService.insert(developerDtos.toModel());
            }

        }
    }
}


