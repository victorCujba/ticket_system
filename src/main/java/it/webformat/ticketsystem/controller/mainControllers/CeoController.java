package it.webformat.ticketsystem.controller.mainControllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.webformat.ticketsystem.data.dto.*;
import it.webformat.ticketsystem.data.models.Badge;
import it.webformat.ticketsystem.data.models.Employee;
import it.webformat.ticketsystem.data.models.Project;
import it.webformat.ticketsystem.data.models.Team;
import it.webformat.ticketsystem.enums.EmployeeRole;
import it.webformat.ticketsystem.exceptions.IdMustBeNullException;
import it.webformat.ticketsystem.repository.EmployeeRepository;
import it.webformat.ticketsystem.repository.ProjectRepository;
import it.webformat.ticketsystem.repository.TeamRepository;
import it.webformat.ticketsystem.service.BadgeService;
import it.webformat.ticketsystem.service.EmployeeService;
import it.webformat.ticketsystem.service.ProjectService;
import it.webformat.ticketsystem.service.TeamService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static it.webformat.ticketsystem.utility.DataConversionUtils.numberToString;

@AllArgsConstructor
@RestController
@SecurityRequirement(name = "authentication")
@RequestMapping("/ceo")
public class CeoController {

    private ProjectService projectService;
    private ProjectRepository projectRepository;
    private EmployeeService employeeService;
    private EmployeeRepository employeeRepository;
    private BadgeService badgeService;
    private TeamRepository teamRepository;
    private TeamService teamService;


    @PutMapping("/assign-project")
    @Operation(description = """
            This method is used to assign PM to Project<br>
            """)
    public ResponseEntity<String> assignProjectToPM(@RequestParam Long projectId, @RequestParam Long projectManagerId) {

        Optional<ProjectDto> projectDto = projectRepository.findById(projectId).map(Project::toDto);
        Optional<PmDto> pmDto = employeeRepository.findById(projectManagerId).map(Employee::toPmDto);

        if ((projectDto.isPresent()) && (pmDto.isPresent())) {
            Employee pm = pmDto.get().toModel();

            Project project = projectDto.get().toModel();
            project.setAssignedPM(pm.getFullName());
            pm.setProject(project);
            projectService.update(project);
            employeeService.update(pm);

            return ResponseEntity.ok("Project was assigned to Project Manager successfully!");

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project or Project Manager not found");
        }
    }

    @PostMapping("/assume-pm")
    @Operation(description = """
            This method is used to assume PM by CEO<br>
            """)
    public EmployeeDto assumeProjectManager(@RequestParam String fullName, @RequestParam LocalDate birthDate) {
        try {
            Badge badge = new Badge();
            Employee pm = Employee.builder()
                    .fullName(fullName)
                    .birthDate(birthDate)
                    .badge(badge)
                    .employeeRole(EmployeeRole.PM)
                    .build();
            return employeeService.insert(pm).toDto();
        } catch (IdMustBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    @PostMapping("/assume-dev")
    @Operation(description = """
            This method is used to assume DEV by CEO<br>
            """)
    public EmployeeDto assumeDeveloper(@RequestParam String fullName, @RequestParam LocalDate birthDate) {
        try {
            Badge badge = new Badge();
            Employee dev = Employee.builder()
                    .employeeRole(EmployeeRole.DEV)
                    .fullName(fullName)
                    .badge(badge)
                    .birthDate(birthDate)
                    .build();
            badge.setEmployee(dev);
            return employeeService.insert(dev).toDto();


        } catch (IdMustBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    @GetMapping("/show-referenced-pm")
    @Operation(description = """
            This method is used to show referenced PM (Project Manager) to DEV (Developer)
            """)
    public String showReferencedProjectManager(@RequestParam Long developerId) {

        Optional<Employee> optionalEmployee = employeeRepository.findById(developerId);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            return employee.getReferencedPM();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Developer with ID: " + developerId + " not found. Please insert a valid ID."
            );
        }
    }

    @PutMapping("/assign-team-to-project")
    @Operation(description = """
            This method is used to assign team to a project.
            """)
    public ResponseEntity<String> assignTeamToProject(@RequestParam Long teamId, @RequestParam Long projectId) {
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        Optional<Project> optionalProject = projectRepository.findById(projectId);

        if (optionalTeam.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Team with ID " + teamId + " not found");
        }

        if (optionalProject.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project with ID " + projectId + " not found");
        }
        Team team = optionalTeam.get();
        Project project = optionalProject.get();
        String projectManager = team.getEmployeeList().stream().filter(employee -> employee.getEmployeeRole() == EmployeeRole.PM).findFirst().get().getFullName();

        List<Employee> fullEmployeeList = new ArrayList<>(project.getEmployees());
        fullEmployeeList.addAll(team.getEmployeeList());

        project.getTeams().add(team);
        project.setEmployees(fullEmployeeList);
        project.setTitle(project.getTitle() + " and " + projectManager);
        project.setAssignedPM(project.getAssignedPM() + " and " + projectManager);
        projectService.update(project);

        List<Employee> employeesToUpdate = new ArrayList<>(fullEmployeeList);

        for (Employee employee : employeesToUpdate) {
            employee.setProject(project);
            employeeService.update(employee);
        }
        team.setProject(project);
        teamService.update(team);

        return ResponseEntity.ok("Team assigned to project successfully!");

    }

    @PutMapping("/assign-developer-to-team")
    @Operation(description = """
                  This method is use to assign new Developer to Team.
            """)
    public ResponseEntity<String> assignDeveloperToTeam(@RequestParam Long developerId, @RequestParam Long teamId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(developerId);
        Optional<Team> optionalTeam = teamRepository.findById(teamId);

        if (optionalEmployee.isPresent() && optionalTeam.isPresent()) {
            Employee developer = optionalEmployee.get();
            Team team = optionalTeam.get();

            Employee pm = team.getEmployeeList().stream()
                    .filter(employee -> employee.getEmployeeRole() == EmployeeRole.PM)
                    .findFirst().orElse(Employee.builder().build());

            developer.setTeam(team);
            developer.setReferencedPM(pm.getFullName());
            developer.setProject(team.getProject());
            employeeService.update(developer);

            team.getEmployeeList().add(developer);
            teamService.update(team);

            return ResponseEntity.ok("Developer assigned to the team successfully!");

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee or Team not found with provided IDs.");
        }
    }

    @PostMapping("/create-and-assign-developer-to-team")
    @Operation(description = """
                  This method is use to create new Developer and assign him to Team.
            """)
    public ResponseEntity<String> createDeveloperAndAssignHimToTeam(@RequestParam String fullName, @RequestParam LocalDate birthDate, @RequestParam Long teamId) {
        EmployeeDto employeeDto = assumeDeveloper(fullName, birthDate);
        return assignDeveloperToTeam((employeeDto.getId()), teamId);
    }


}
