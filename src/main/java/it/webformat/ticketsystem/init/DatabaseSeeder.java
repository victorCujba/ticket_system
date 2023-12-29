package it.webformat.ticketsystem.init;

import it.webformat.ticketsystem.data.dto.CeoDto;
import it.webformat.ticketsystem.data.dto.DevDto;
import it.webformat.ticketsystem.data.dto.PmDto;
import it.webformat.ticketsystem.enums.EmployeeRole;
import it.webformat.ticketsystem.service.BadgeService;
import it.webformat.ticketsystem.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static it.webformat.ticketsystem.utility.DataConversionUtils.employeeRoleToString;

@Component
@AllArgsConstructor
public class DatabaseSeeder {

    private final EmployeeService employeeService;
    private final BadgeService badgeService;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedEmployeeTableWithCeo();
        seedEmployeeTableWithPm();
        seedEmployeeTableWithDev();
    }

    private void seedEmployeeTableWithCeo() {
        if (employeeService.getCountCeoEmployee() == 0) {

            CeoDto ceoDto = CeoDto.builder()
                    .fullName("Mario Rossi")
                    .birthDate(String.valueOf(LocalDate.now().minusYears(30)))
                    .employeeRole("CEO")
                    .build();
            employeeService.insert(ceoDto.toModel());

//            List<Employee> ceoList = employeeService.getEmployeeByRole(EmployeeRole.CEO);
//
//            for (Employee ceo : ceoList) {
//                Badge ceoBadge = ceo.getBadge();
//                ceoBadge.setEmployee(ceo);
//                badgeService.update(ceoBadge);
//            }
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


//            List<Employee> projectManagers = employeeService.getEmployeeByRole(EmployeeRole.PM);
//
//            for (Employee projectManager : projectManagers) {
//                Badge projactManagerBadge = projectManager.getBadge();
//                projactManagerBadge.setEmployee(projectManager);
//                badgeService.update(projactManagerBadge);
//            }
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
}
