package it.webformat.ticketsystem.config;

import it.webformat.ticketsystem.data.models.Employee;
import it.webformat.ticketsystem.enums.EmployeeRole;
import it.webformat.ticketsystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.List;

@Configuration
@DependsOn("databaseSeeder")
public class UserDetailsConfig {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public UserDetailsConfig(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Bean
    public UserDetailsService userDetailsService() {


        List<Employee> ceoList = employeeRepository.getByEmployeeRole(EmployeeRole.CEO);
        List<Employee> pmList = employeeRepository.getByEmployeeRole(EmployeeRole.PM);
        List<Employee> devList = employeeRepository.getByEmployeeRole(EmployeeRole.DEV);

        List<UserDetails> userDetailsList = new ArrayList<>();

        for (Employee ceo : ceoList) {
            UserDetails ceoEmployee = User.builder()
                    .username(ceo.getFullName())
                    .password(passwordEncoder().encode("ceo"))
                    .roles(EmployeeRole.CEO.toString())
                    .build();
            userDetailsList.add(ceoEmployee);
        }

        for (Employee pm : pmList) {
            UserDetails pmEmployee = User.builder()
                    .username(pm.getFullName())
                    .password(passwordEncoder().encode("pm"))
                    .roles(EmployeeRole.PM.toString())
                    .build();
            userDetailsList.add(pmEmployee);
        }

        for (Employee dev : devList) {
            UserDetails devEmployee = User.builder()
                    .username(dev.getFullName())
                    .password(passwordEncoder().encode("dev"))
                    .roles(EmployeeRole.DEV.toString())
                    .build();
            userDetailsList.add(devEmployee);
        }


        return new InMemoryUserDetailsManager(userDetailsList);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
