//package it.webformat.ticketsystem.config;
//
//import it.webformat.ticketsystem.enums.EmployeeRole;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.filter.CorsFilter;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//
//@Configuration
//public class SecurityConfig {
//
//    private final WhiteListConfiguration whiteList;
//
//    private final CorsFilter corsFilter;
//
//    public SecurityConfig(WhiteListConfiguration whiteList, CorsFilter corsFilter) {
//        this.whiteList = whiteList;
//        this.corsFilter = corsFilter;
//    }
//
//    @Bean
//    public static PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
//                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers(whiteList.getUrls()).permitAll()
//                        .requestMatchers(HttpMethod.GET, "/").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/customers/**").permitAll()
//                        .requestMatchers("/customers/**").hasRole(UserRole.MANAGER.toString())
//                        .requestMatchers("/orders/**").hasAnyRole(
//                                UserRole.MANAGER.toString(),
//                                UserRole.USER.toString())
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(withDefaults())
//                .formLogin(withDefaults());
//        return http.build();
//
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        UserDetails ceo = User.builder()
//                .username("CEO")
//                .password(passwordEncoder().encode("ceopassword"))
//                .roles(EmployeeRole.CEO.toString())
//                .build();
//
//        UserDetails pm = User.builder()
//                .username("PM")
//                .password(passwordEncoder().encode("pmpassword"))
//                .roles(EmployeeRole.PM.toString())
//                .build();
//
//        UserDetails dev = User.builder()
//                .username("DEV")
//                .password(passwordEncoder().encode("devpassword"))
//                .roles(EmployeeRole.DEV.toString())
//                .build();
//
//        return new InMemoryUserDetailsManager(ceo, pm, dev);
//
//    }
//
//}