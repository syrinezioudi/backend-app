package tn.esprit.spring.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // Set the exact origin where your frontend (Angular) is running
        config.addAllowedOrigin("*"); // Allow only localhost:4200
        config.setAllowedMethods(Collections.singletonList("*")); // Allow all HTTP methods (GET, POST, etc.)
        config.setAllowedHeaders(Collections.singletonList("*")); // Allow all headers
        source.registerCorsConfiguration("/**", config); // Apply CORS configuration to all endpoints
        return new CorsFilter(source);
    }
}
