package com.example.javarouterpostgres;

import com.example.javarouterpostgres.config.EstudiantesRouter;
import com.example.javarouterpostgres.handler.RequestsHandler;
import com.example.javarouterpostgres.model.Estudiantes;
import com.example.javarouterpostgres.repository.EstudiantesRepositorio;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes=JavaRouterPostgresApplication.class)
public class JavaRouterPostgresApplicationTests {
	@Autowired
	private EstudiantesRouter router;
	@Autowired
	private RequestsHandler handler;
	@MockBean
	private EstudiantesRepositorio repositorio;

	@Test
	public void givenEmployeeId_whenGetEmployeeById_thenCorrectEmployee() {
		WebTestClient client = WebTestClient.bindToRouterFunction(router.routerFunction(handler)).build();
		Estudiantes estudiante = new Estudiantes(1, "Karel", "Negreira");

		given(repositorio.findById(1)).willReturn(Mono.just(estudiante));

		client.get()
				.uri("/estudiantes/1")
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody(Estudiantes.class)
				.isEqualTo(estudiante);

	}

	@Test
	public void whenGetAllEmployees_thenCorrectEmployees() {
		WebTestClient client = WebTestClient.bindToRouterFunction(router.routerFunction(handler)).build();
		List<Estudiantes> estudiantesList = Arrays.asList(
				new Estudiantes(1, "Karel", "Negreira"),
				new Estudiantes(2, "Carlos", "Perez")
		);
		Flux<Estudiantes> returningEstudiantesFlux = Flux.fromIterable(estudiantesList);
		given(repositorio.findAll()).willReturn(returningEstudiantesFlux);

		client.get()
				.uri("/estudiantes/all")
				.exchange()
				.expectStatus()
				.isOk()
				.expectBodyList(Estudiantes.class)
				.isEqualTo(estudiantesList);
	}

}
