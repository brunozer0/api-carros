package com.api.project.Controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.api.project.exceptions.IdNotFoundException;
import com.api.project.exceptions.CarroDuplicadoException;
import com.api.project.model.Carro;
import com.api.project.services.CarroService;
import com.fasterxml.jackson.annotation.JacksonInject.Value;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.ConstraintViolationException;

@RestController
@RequestMapping("/api/concessionaria")
@CrossOrigin(origins = "http://localhost:4200")
public class CarroController {

	private final CarroService carroService;

	public CarroController(CarroService carroService) {
		this.carroService = carroService;
	}

	@GetMapping
	public List<Carro> getCarros() {
		return carroService.getCarros();
	}

	@Operation(description = "busca o carro por id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "retorna o carro"),
			@ApiResponse(responseCode = "404", description = "não existe carro com o id informado") })
	@GetMapping("/{id}")
	public ResponseEntity<Carro> getCarroById(@PathVariable("id") Long id) {
		try {
			Carro idCarro = carroService.getCarroById(id);
			return ResponseEntity.ok(idCarro);
		} catch (IdNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}

	}

	@Operation(description = "cadastra carro")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "carro cadastrado"),
			@ApiResponse(responseCode = "422", description = "Erro validação ou carro duplicado") })
	@PostMapping
	public ResponseEntity<Carro> cadastrarCarro(@RequestBody Carro carro) {

		try {
			Carro carroSalvo = carroService.salvarCarro(carro);
			return ResponseEntity.created(URI.create("/api/livros/" + carroSalvo.getId())).body(carroSalvo);

		} catch (CarroDuplicadoException e) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), e);
		} catch (ConstraintViolationException e) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), e);
		}

	}

	@Operation(description = "Atualiza carro")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "atualiza carro"),
			@ApiResponse(responseCode = "404", description = "id não existe"),
			@ApiResponse(responseCode = "422", description = "Erro de validação ou carro duplicado") })
	@PutMapping("/{id}")
	public ResponseEntity<Carro> updateCarro(@PathVariable Long id, @RequestBody Carro carro) {

		try {
			Carro carroUpdated = carroService.updateCarro(id, carro);
			return ResponseEntity.ok(carroUpdated);
		} catch (ConstraintViolationException e) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), e);
		} catch (CarroDuplicadoException e) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), e);
		} catch (IdNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}

	}

	@Operation(description = "Exclui carro ")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Carro excluído "),
			@ApiResponse(responseCode = "404", description = "Id não encontrado") })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCaro(@PathVariable("id") Long id) {

		try {
			carroService.deleteCarro(id);
			return ResponseEntity.noContent().build();
		} catch (IdNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
}
