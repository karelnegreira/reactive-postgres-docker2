package com.example.javarouterpostgres.handler;

import com.example.javarouterpostgres.model.Estudiantes;
import com.example.javarouterpostgres.repository.EstudiantesRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class RequestsHandler {
    private EstudiantesRepositorio repository;

    @Autowired
    public RequestsHandler(EstudiantesRepositorio repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> getEstudiantes(ServerRequest request) {
        return ok().contentType(MediaType.APPLICATION_JSON)
                .body(fromPublisher(repository.findAll(), Estudiantes.class));
    }

    public Mono<ServerResponse> getEstudiante(ServerRequest request) {
        final int id = Integer.parseInt(request.pathVariable("id"));
        final Mono<Estudiantes> estudiante = repository.findById(id);
        return estudiante.flatMap(estudiante2 -> ok().contentType(MediaType.APPLICATION_JSON)
        .body(fromPublisher(estudiante, Estudiantes.class)))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> createEstudiante(ServerRequest request) {
        final Mono<Estudiantes> estudiante = request.bodyToMono(Estudiantes.class);
        System.out.println(estudiante.toString());
        return ok().contentType(MediaType.APPLICATION_JSON)
                .body(fromPublisher(estudiante.flatMap(repository::save), Estudiantes.class));
    }
}
