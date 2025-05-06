package com.ssma.sgrh.config;

import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String ruta = Paths.get("uploads/imagenes").toAbsolutePath().toUri().toString();

        registry.addResourceHandler("/imagenes/**")
                .addResourceLocations(ruta);
    }
}

