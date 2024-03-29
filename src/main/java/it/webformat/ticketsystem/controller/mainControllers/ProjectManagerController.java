package it.webformat.ticketsystem.controller.mainControllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.webformat.ticketsystem.data.dto.CommentsDto;
import it.webformat.ticketsystem.data.dto.LabourDto;
import it.webformat.ticketsystem.data.dto.LabourWithCommentsDto;
import it.webformat.ticketsystem.data.dto.ProjectDto;
import it.webformat.ticketsystem.data.models.*;
import it.webformat.ticketsystem.enums.EmployeeRole;
import it.webformat.ticketsystem.enums.TaskStatus;
import it.webformat.ticketsystem.exceptions.IdMustBeNullException;
import it.webformat.ticketsystem.exceptions.IdMustNotBeNullException;
import it.webformat.ticketsystem.exceptions.NoProjectFoundException;
import it.webformat.ticketsystem.repository.*;
import it.webformat.ticketsystem.service.EmployeeService;
import it.webformat.ticketsystem.service.LabourService;
import it.webformat.ticketsystem.service.ProjectService;
import it.webformat.ticketsystem.service.TeamService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
    private CommentsRepository commentsRepository;
    private TeamRepository teamRepository;
    private TeamService teamService;

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

    // TODO: Update to ensure that a Project Manager (PM) can assign tasks only to Developers from its (PM's) team.
    @PostMapping("/assign-task-to-dev")
    @Operation(description = """
            This method is used to assign a task to developer
            """)
    public ResponseEntity<String> assignLabourToDeveloper(@RequestParam Long labourId, @RequestParam Long developerId) {
        Labour labour = labourService.findById(labourId);

        if (labour.getId() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Labour with ID: " + labourId + "not found. Check labour ID please.");
        }

        Employee developer = employeeService.findById(developerId);
        if (developer.getId() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Developer with ID: " + developerId + " not found. Check developer ID please");

        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedPmFullName = authentication.getName();
        Employee authenticatedPm = employeeService.findByFullName(authenticatedPmFullName);

        if (authenticatedPm == null || !authenticatedPm.getEmployeeRole().equals(EmployeeRole.PM)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authenticated user is not a Project Manager. Insufficient roots.");

        }
        labour.setProject(authenticatedPm.getProject());

        labour.setEmployee(developer);
        labour.setDescription("Labour assigned to " + developer.getFullName());
        labourService.update(labour);

        developer.getLabourList().add(labour);
        employeeService.update(developer);

        return ResponseEntity.status(HttpStatus.OK).body("Task assigned successfully!");
    }


    @DeleteMapping("/remove-task-from-dev")
    @Operation(description = """
            This method is used to remove a task from developer
            """)
    public ResponseEntity<String> removeLabourFromDeveloper(@RequestParam Long labourId, @RequestParam Long developerId) {
        Employee developer = employeeService.findById(developerId);

        if (developer.getId() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Developer not found with ID: " + developerId + " please check ID");

        }
        List<Labour> developerLabourList = developer.getLabourList();
        List<Labour> updatedLabourList = developerLabourList.stream()
                .filter(labour -> !labour.getId().equals(labourId))
                .collect(Collectors.toList());

        if (developerLabourList.size() == updatedLabourList.size()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task with ID " + labourId + " not found in developer tasks.");
        }

        developerLabourList.clear();
        developerLabourList.addAll(updatedLabourList);

        labourService.deleteById(labourId);
        employeeService.update(developer);

        return ResponseEntity.status(HttpStatus.OK).body("Task removed successfully!");
    }


    //TODO Can be transformed in generic , passing TaskStatus parameter retrieve desired list of tasks(Labours)
    @GetMapping("/show-working-on-labours")
    @Operation(description = """
            This method is used to show all working on Labours assigned to Developer
            """)
    public List<LabourDto> showWorkingOnLabours(@RequestParam Long developerId) {

        Employee developer = employeeService.findById(developerId);

        if (developer.getId() == null) {
            System.out.println("Developer not found with ID: " + developerId);
            return Collections.emptyList();
        } else {
            List<Labour> labourList = developer.getLabourList();

            if (labourList == null || labourList.isEmpty()) {
                System.out.println("Labours for Developer with ID: " + developer + "not found.");
                return Collections.emptyList();
            } else {
                return labourList.stream()
                        .filter(labour -> labour.getTaskStatus() == TaskStatus.WORKING_ON)
                        .map(Labour::toDto)
                        .collect(Collectors.toList());
            }
        }
    }

    @GetMapping("/show-expired-labours-with-commits")
    @Operation(description = """
                  This method show all expired Labours of a Developer along with their commits
            """)
    public List<LabourWithCommentsDto> showExpiredLaboursWithComment(@RequestParam Long developerId) {
        try {
            Optional<Employee> optionalDeveloper = employeeRepository.findById(developerId);

            if (optionalDeveloper.isPresent()) {
                Employee developer = optionalDeveloper.get();
                List<Labour> expiredLabours = labourRepository.findExpiredLabourByEmployee(developer.getId());

                List<LabourWithCommentsDto> result = new ArrayList<>();

                for (Labour labour : expiredLabours) {
                    List<CommentsDto> commentsDtos = commentsRepository.findCommentsByLabour(labour)
                            .stream().map(Comments::toDto).collect(Collectors.toList());

                    LabourWithCommentsDto labourWithCommentsDto = new LabourWithCommentsDto(
                            labour.getId(),
                            labour.getDeadline(),
                            commentsDtos,
                            labour.getEmployee().getId(),
                            labour.getEmployee().getFullName());

                    result.add(labourWithCommentsDto);
                }
                return result;
            } else {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Developer with ID: " + developerId + " not found. Please insert a valid ID."
                );
            }
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e
            );
        }

    }


    @GetMapping("/show-cross-team-projects")
    @Operation(description = """
                  This method show all expired Labours of a Developer along with their commits
            """)
    public List<ProjectDto> showCrossTeamProjects() {
        List<Project> crossTeamProjects = projectService.getProjectsWithManyTeams();

        if (crossTeamProjects.isEmpty()) {
            throw new NoProjectFoundException("Cross team projects not found.");
        }
        return crossTeamProjects.stream()
                .map(Project::toDto).collect(Collectors.toList());
    }

}
