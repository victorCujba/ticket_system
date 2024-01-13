package it.webformat.ticketsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.webformat.ticketsystem.data.dto.LabourDto;
import it.webformat.ticketsystem.data.models.Employee;
import it.webformat.ticketsystem.data.models.Labour;
import it.webformat.ticketsystem.enums.EmployeeRole;
import it.webformat.ticketsystem.enums.TaskStatus;
import it.webformat.ticketsystem.exceprions.IdMustBeNullException;
import it.webformat.ticketsystem.exceprions.IdMustNotBeNullException;
import it.webformat.ticketsystem.repository.EmployeeRepository;
import it.webformat.ticketsystem.repository.LabourRepository;
import it.webformat.ticketsystem.repository.ProjectRepository;
import it.webformat.ticketsystem.service.EmployeeService;
import it.webformat.ticketsystem.service.LabourService;
import it.webformat.ticketsystem.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@RestController
@SecurityRequirement(name = "authentication")
@RequestMapping("/project-manager")
public class ProjectManagerController {

    private LabourService labourService;
    private LabourRepository labourRepository;
    private EmployeeService employeeService;
    private EmployeeRepository employeeRepository;
    private ProjectRepository projectRepository;
    private ProjectService projectService;

    @PostMapping("/create-labour")
    @Operation(description = """
            This method is used to insert by Project Manager new Labour<br>
            """)
    public LabourDto createLabour(@RequestParam String desc, @RequestParam LocalDate deadline, @RequestParam TaskStatus taskStatus) {
        try {
            Labour labour = Labour.builder()
                    .description(desc)
                    .deadline(deadline)
                    .taskStatus(taskStatus)
                    .build();
            return labourService.insert(labour).toDto();
        } catch (IdMustBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    @PutMapping("/update-labour")
    @Operation(description = """
            This method is used to update by Project Manager existing  Labour<br>
            """)
    public LabourDto updateLabour(@RequestBody LabourDto labourDto) {
        try {
            Labour labour = labourDto.toModel();
            return labourService.update(labour).toDto();
        } catch (IdMustNotBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }


    @PostMapping("/assign-task-to-dev")
    @Operation(description = """
            This method is used to assign a task to developer
            """)
    public void assignLabourToDeveloper(@RequestParam Long labourId, @RequestParam Long developerId) {
        Labour labour = labourService.findById(labourId);

        if (labour.getId() == null) {
            System.out.println("Labour with ID: " + labourId + "not found. Check labour ID please.");
            return;
        }

        Employee developer = employeeService.findById(developerId);
        if (developer.getId() == null) {
            System.out.println("Developer with ID: " + developerId + " not found. Check developer ID please");
            return;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedPmFullName = authentication.getName();
        Employee authenticatedPm = employeeService.findByFullName(authenticatedPmFullName);

        if (authenticatedPm == null || !authenticatedPm.getEmployeeRole().equals(EmployeeRole.PM)) {
            System.out.println("Authenticated user is not a Project Manager. Insufficient roots.");
            return;
        }
        labour.setProject(authenticatedPm.getProject());

        labour.setEmployee(developer);
        labour.setDescription("Labour assigned to " + developer.getFullName());
        labourService.update(labour);

        developer.getLabourList().add(labour);
        employeeService.update(developer);
    }


    @PostMapping("/remove-task-from-dev")
    @Operation(description = """
            This method is used to remove a task from developer
            """)
    public void removeLabourFromDeveloper(@RequestParam Long labourId, @RequestParam Long developerId) {
        Employee developer = employeeService.findById(developerId);

        if (developer.getId() == null) {
            System.out.println("Developer not found with ID: " + developerId);
            return;
        }
        List<Labour> developerLabourList = developer.getLabourList();
        List<Labour> updatedLabourList = developerLabourList.stream()
                .filter(labour -> !labour.getId().equals(labourId))
                .collect(Collectors.toList());

        developerLabourList.clear();
        developerLabourList.addAll(updatedLabourList);

        labourService.deleteById(labourId);
        employeeService.update(developer);
    }


}
