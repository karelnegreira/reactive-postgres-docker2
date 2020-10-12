package com.example.javarouterpostgres.config;

import com.example.javarouterpostgres.handler.RequestsHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class EstudiantesRouter {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(RequestsHandler handler) {
        return route().path("/estudiantes", builder -> builder
                        .GET("/all", accept(MediaType.APPLICATION_JSON), handler::getEstudiantes)
                        .GET("/{id}", accept(MediaType.APPLICATION_JSON), handler::getEstudiante)
                .POST("/save", accept(MediaType.APPLICATION_JSON), handler::createEstudiante))
                .build();
    }

}


