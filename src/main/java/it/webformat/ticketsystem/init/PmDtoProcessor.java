package it.webformat.ticketsystem.init;

import it.webformat.ticketsystem.data.dto.PmDto;
import it.webformat.ticketsystem.data.models.Employee;
import it.webformat.ticketsystem.service.EmployeeService;

import java.util.List;

public class PmDtoProcessor {

    private final EmployeeService employeeService;

    public PmDtoProcessor(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void processPmDtos(List<PmDto> projectManagersListDtos) {
        for (PmDto projectManagerDtos : projectManagersListDtos) {
            String fullName = projectManagerDtos.getFullName();

            Employee existingEmployee = employeeService.findByFullName(fullName);

            if (existingEmployee != null) {
                employeeService.update(existingEmployee);
            } else {
                employeeService.insert(projectManagerDtos.toModel());
            }

        }
    }
}
