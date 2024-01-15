package it.webformat.ticketsystem.config;

import it.webformat.ticketsystem.data.models.Employee;
import it.webformat.ticketsystem.enums.EmployeeRole;
import it.webformat.ticketsystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
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
@Order(Ordered.LOWEST_PRECEDENCE - 100)
@DependsOn("databaseSeeder")
public class SecurityConfig {

    private final WhiteListConfiguration whiteList;

    private final CorsFilter corsFilter;


    @Autowired
    public SecurityConfig(WhiteListConfiguration whiteList, CorsFilter corsFilter) {
        this.whiteList = whiteList;
        this.corsFilter = corsFilter;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(whiteList.getUrls()).permitAll()
                        .requestMatchers("/test/hello").permitAll()
                        .requestMatchers(HttpMethod.GET, "/employees/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/teams/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/projects/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/labours/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/employees/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/comments/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/badges/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/work-records/**").permitAll()
                        .requestMatchers("/ceo/**").hasRole(EmployeeRole.CEO.toString())
                        .requestMatchers("/project-manager/**").hasRole(EmployeeRole.PM.toString())
                        .requestMatchers("/dev/**").hasRole(EmployeeRole.DEV.toString())
                        .anyRequest()
                        .authenticated()

                )
                .formLogin(withDefaults())
                .httpBasic(withDefaults())
                .logout(logout -> logout.logoutSuccessUrl("/")
                        .permitAll());

        return http.build();

    }


}