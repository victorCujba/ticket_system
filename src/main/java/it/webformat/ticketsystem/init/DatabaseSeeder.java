package it.webformat.ticketsystem.init;

import it.webformat.ticketsystem.data.dto.CeoDto;
import it.webformat.ticketsystem.data.dto.DevDto;
import it.webformat.ticketsystem.data.dto.PmDto;
import it.webformat.ticketsystem.data.models.*;
import it.webformat.ticketsystem.enums.EmployeeRole;
import it.webformat.ticketsystem.enums.TaskStatus;
import it.webformat.ticketsystem.service.*;
import lombok.AllArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

import static it.webformat.ticketsystem.utility.DataConversionUtils.employeeRoleToString;

@Component
@AllArgsConstructor
public class DatabaseSeeder {

    private final EmployeeService employeeService;
    private final BadgeService badgeService;
    private final TeamService teamService;
    private final ProjectService projectService;
    private final LabourService labourService;


    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedEmployeeTableWithCeo();
        seedEmployeeTableWithPm();
        seedEmployeeTableWithDev();
        seedBadgeTableWithData();
        seedTeamTableWithData();
        seedProjectTableWithData();
        setProjectIdToTeam();
        seedLabourTableWithData();
    }

    private void seedEmployeeTableWithCeo() {
        if (employeeService.getCountCeoEmployee() == 0) {

            CeoDto ceoDto = CeoDto.builder()
                    .fullName("Mario Rossi")
                    .birthDate(String.valueOf(LocalDate.now().minusYears(30)))
                    .employeeRole("CEO")
                    .build();
            employeeService.insert(ceoDto.toModel());
        }
    }

    private void seedEmployeeTableWithPm() {


        List<PmDto> projectManagersListDtos = Arrays.asList(
                createPmDto("John Doe", "1985-01-01"),
                createPmDto("Jahn Smith", "1986-02-01"),
                createPmDto("Bob Johnson", "1987-03-01"),
                createPmDto("Alice Williams", "1988-04-01"),
                createPmDto("JCharlie Brown", "1989-05-01")
        );

        new PmDtoProcessor(employeeService).processPmDtos(projectManagersListDtos);

    }

    private PmDto createPmDto(String fullName, String birthDate) {
        return PmDto.builder()
                .fullName(fullName)
                .birthDate(birthDate)
                .employeeRole(employeeRoleToString(EmployeeRole.PM))
                .build();
    }

    private void seedEmployeeTableWithDev() {
        List<DevDto> developerListDtos = Arrays.asList(
                createDevDto("Ella Davis", "1990-01-01"),
                createDevDto("Liam Miller", "1991-02-01"),
                createDevDto("Ava Johnson", "1992-03-01"),
                createDevDto("Noah Williams", "1993-04-01"),
                createDevDto("Sophia Brown", "1994-05-01"),
                createDevDto("Jackson Smith", "1995-06-01"),
                createDevDto("Olivia Wilson", "1996-07-01"),
                createDevDto("Lucas Taylor", "1997-08-01"),
                createDevDto("Emma Davis", "1998-09-01"),
                createDevDto("Aiden Johnson", "1999-10-01"),
                createDevDto("Aria White", "2000-11-01"),
                createDevDto("Carter Wilson", "2001-12-01"),
                createDevDto("Mia Brown", "2002-01-01"),
                createDevDto("Grayson Taylor", "2003-02-01"),
                createDevDto("Lily Wilson", "2004-03-01")
        );

        new DevDtoProcessor(employeeService).processDevDtos(developerListDtos);


    }

    private DevDto createDevDto(String fullName, String birthDate) {
        return DevDto.builder()
                .fullName(fullName)
                .birthDate(birthDate)
                .employeeRole(employeeRoleToString(EmployeeRole.DEV))
                .build();
    }

    private void seedBadgeTableWithData() {

        List<Employee> employeeList = employeeService.findAll();

        for (Employee employee : employeeList) {
            if (employee.getBadge() == null) {
                Badge badge = Badge.builder().employee(employee).build();
                badgeService.insert(badge);
                employee.setBadge(badge);
                employeeService.update(employee);
            }
        }
    }

    private void seedTeamTableWithData() {

        List<Employee> pmEmployeeList = employeeService.getEmployeeByRole(EmployeeRole.PM);
        List<Employee> devEmployeeList = employeeService.getEmployeeByRole(EmployeeRole.DEV);

        if (pmEmployeeList != null && devEmployeeList != null) {

            List<List<Employee>> sublist = divideEmployeeIntoSublist(new ArrayList<>(devEmployeeList));

            for (Employee pm : pmEmployeeList) {
                Team existingTeam = pm.getTeam();

                if (existingTeam == null) {
                    String teamName = pm.getFullName() + " team.";

                    List<Employee> teamList = sublist.remove(0);
                    teamList.add(pm);


                    Team team = Team.builder()
                            .name(teamName)
                            .employeeList(teamList)
                            .build();
                    teamService.insert(team);
                    for (Employee employee : teamList) {
                        employee.setTeam(team);
                        employeeService.update(employee);
                    }

                }
            }
        }

    }

    private List<List<Employee>> divideEmployeeIntoSublist(List<Employee> employeeList) {
        int devPerTeam = 3;

        Collections.shuffle(employeeList);
        List<List<Employee>> sublists = new ArrayList<>();

        for (int i = 0; i < employeeList.size(); i += devPerTeam) {
            int finalIndex = Math.min(i + devPerTeam, employeeList.size());
            List<Employee> sublist = new ArrayList<>(employeeList.subList(i, finalIndex));
            sublists.add(sublist);
        }
        return sublists;
    }

    private void seedProjectTableWithData() {

        List<Employee> ceoList = employeeService.getEmployeeByRole(EmployeeRole.CEO);
        List<Employee> pmList = employeeService.getEmployeeByRole(EmployeeRole.PM);

        Optional<Employee> ceo = ceoList.stream().findFirst();

        if (pmList != null && ceo.isPresent()) {
            for (Employee pm : pmList) {
                Project existingProject = pm.getProject();

                if (existingProject == null) {
                    List<Employee> employeeList = teamService.findById(pm.getTeam().getId()).getEmployeeList();

                    Project project = Project.builder()
                            .title(ceo.get().getFullName() + " assigned project to " + pm.getFullName())
                            .assignedPM(pm.getFullName())
                            .employees(employeeList)
                            .build();
                    projectService.insert(project);

                    for (Employee employee : employeeList) {
                        if (employee.getEmployeeRole() == EmployeeRole.DEV) {
                            employee.setProject(project);
                            employee.setReferencedPM(pm.getFullName());
                            employeeService.update(employee);
                        } else {
                            employee.setProject(project);
                            employee.setReferencedPM("Project manager is not assigned at himself.");
                            employeeService.update(employee);
                        }
                    }
                }
            }
        }
    }

    private void setProjectIdToTeam() {
        List<Employee> pmList = employeeService.getEmployeeByRole(EmployeeRole.PM);

        if (pmList != null) {
            for (Employee employee : pmList) {
                assignProjectToTeam(employee.getFullName());
            }
        } else {
            System.out.println("PM does not exist in database");
        }

    }

    private void assignProjectToTeam(String projectManager) {
        Team team = teamService.findTeamByName(projectManager + " team.");

        if (team != null) {
            Employee projectManagerEmployee = team.getEmployeeList().stream()
                    .filter(employee -> employee.getEmployeeRole() == EmployeeRole.PM)
                    .findFirst()
                    .orElse(null);

            if (projectManagerEmployee != null) {
                Project project = projectService.findByAssignedPM(projectManagerEmployee.getFullName());

                if (project != null) {
                    team.setProject(project);
                    teamService.update(team);
                }
            }
        }
    }

    private void seedLabourTableWithData() {
        List<Employee> devList = employeeService.getEmployeeByRole(EmployeeRole.DEV);
        List<Project> projects = projectService.findAll();

        if (devList != null && !devList.isEmpty() && projects != null && !projects.isEmpty()) {
            Random random = new Random();

            for (Employee developer : devList) {
                Project project = projects.get(random.nextInt(projects.size()));

                for (TaskStatus status : TaskStatus.values()) {
                    Employee attachedEmployee = employeeService.findById(developer.getId());

                    Labour labour = Labour.builder()
                            .description("Task for " + attachedEmployee.getFullName() + " - " + status.name())
                            .deadline(LocalDate.now().plusDays(random.nextInt(30)))
                            .taskStatus(status)
                            .employee(attachedEmployee)
                            .project(project)
                            .build();

                    labourService.insert(labour);
                }

                Employee attachedEmployee = employeeService.findById(developer.getId());

                Labour expiredLabour = Labour.builder()
                        .description("Expired Task for " + attachedEmployee.getFullName())
                        .deadline(LocalDate.now().minusDays(random.nextInt(30)))
                        .taskStatus(TaskStatus.TO_DO)
                        .employee(attachedEmployee)
                        .project(project)
                        .build();

                labourService.insert(expiredLabour);
            }
        }
    }


}
