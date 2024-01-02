package it.webformat.ticketsystem.config;

import it.webformat.ticketsystem.data.models.Employee;
import it.webformat.ticketsystem.enums.EmployeeRole;
import it.webformat.ticketsystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
public class SecurityConfig {

    private final WhiteListConfiguration whiteList;

    private final CorsFilter corsFilter;

    private final EmployeeRepository employeeRepository;

    @Autowired
    public SecurityConfig(WhiteListConfiguration whiteList, CorsFilter corsFilter, EmployeeRepository employeeRepository) {
        this.whiteList = whiteList;
        this.corsFilter = corsFilter;
        this.employeeRepository = employeeRepository;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.PUT, "/ceo/**").hasRole(EmployeeRole.CEO.toString())
                        .requestMatchers(HttpMethod.POST, "/ceo/**").hasRole(EmployeeRole.CEO.toString())
                        .requestMatchers(HttpMethod.GET, "/employees/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/project-manager/**").hasRole(EmployeeRole.PM.toString())
                        .requestMatchers(HttpMethod.POST, "/project-manager/**").hasRole(EmployeeRole.PM.toString())
                        .requestMatchers(HttpMethod.POST, "/dev/**").hasRole(EmployeeRole.DEV.toString())

                        .requestMatchers("/labours/**").hasRole(EmployeeRole.PM.toString())
                        .anyRequest()
                        .authenticated()

                )
                .httpBasic(withDefaults())
                .formLogin(withDefaults());
        return http.build();

    }

    @Bean
    public UserDetailsService userDetailsService() {


        List<Employee> ceoList = employeeRepository.getByEmployeeRole(EmployeeRole.CEO);
        List<Employee> pmList = employeeRepository.getByEmployeeRole(EmployeeRole.PM);

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


        return new InMemoryUserDetailsManager(userDetailsList);

    }

}