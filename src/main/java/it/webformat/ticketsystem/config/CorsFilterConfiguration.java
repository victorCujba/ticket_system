//package it.webformat.ticketsystem.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//import java.util.Collections;
//import java.util.List;
//
//@Configuration
//public class CorsFilterConfiguration {
//
//    @Bean
//    public CorsFilter crossFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//
//        config.setAllowCredentials(false);
//        config.setAllowedOrigins(List.of("*"));
//        config.setAllowedMethods(List.of("GET", "POST", "PUT"));
//        config.addAllowedHeader(CorsConfiguration.ALL);
//        config.setExposedHeaders(Collections.singletonList("X-CSRF-TOKEN"));
//        config.setMaxAge(3600L);
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
//}
