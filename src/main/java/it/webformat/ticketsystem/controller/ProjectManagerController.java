package it.webformat.ticketsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.webformat.ticketsystem.data.dto.LabourDto;
import it.webformat.ticketsystem.data.models.Labour;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;


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

}
