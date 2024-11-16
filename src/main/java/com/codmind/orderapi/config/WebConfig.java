package com.codmind.orderapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/v1/**") // Ajusta la ruta si es necesario
                .allowedOrigins("http://localhost:4200") // Permite solicitudes desde Angular
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos permitidos
                .allowedHeaders("*") // Permite cualquier tipo de cabecera
                .allowCredentials(true); // Permite el envío de credenciales como cookies, cabeceras de autorización, etc.
    }
}

