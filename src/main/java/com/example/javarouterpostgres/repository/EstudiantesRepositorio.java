package com.example.javarouterpostgres.repository;

import com.example.javarouterpostgres.model.Estudiantes;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EstudiantesRepositorio extends ReactiveCrudRepository<Estudiantes, Integer> {
}
