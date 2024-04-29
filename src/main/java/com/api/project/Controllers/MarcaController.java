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
import com.api.project.exceptions.MarcaDuplicadaException;
import com.api.project.model.Marca;
import com.api.project.services.MarcaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.ConstraintViolationException;

@RestController
@RequestMapping("/api/concessionaria/marca")
@CrossOrigin(origins = "http://localhost:4200")
public class MarcaController {

	private final MarcaService marcaService;

	public MarcaController(MarcaService marcaService) {
		this.marcaService = marcaService;
	}

	@GetMapping
	public List<Marca> getMarcas() {
		return marcaService.getMarcas();
	}

	@Operation(description = "marca por id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retorna a marca"),
			@ApiResponse(responseCode = "400", description = "id nao existe") })
	@GetMapping("/{id}")
	public ResponseEntity<Marca> getMarcaById(@PathVariable("id") Long id) {
		try {
			Marca marcaId = marcaService.getMarcaById(id);
			return ResponseEntity.ok(marcaId);
		} catch (IdNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}

	}

	@Operation(description = "Cadastra marca")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Marca cadastrada "),
			@ApiResponse(responseCode = "422", description = "Erro de validação ou marca duplicada") })
	@PostMapping
	public ResponseEntity<Marca> cadastrarMarca(@RequestBody Marca marca) throws MarcaDuplicadaException {

		try {
			Marca marcaSalva = marcaService.salvarMarca(marca);
			return ResponseEntity.created(URI.create("/api/concessionaria/marca/" + marcaSalva.getId()))
					.body(marcaSalva);

		} catch (MarcaDuplicadaException e) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), e);
		} catch (ConstraintViolationException e) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), e);
		}

	}

	@Operation(description = "Atualiza uma marca")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Marca atualizada "),
			@ApiResponse(responseCode = "400", description = "id nao existe"),
			@ApiResponse(responseCode = "422", description = "Erro de validação") })
	@PutMapping("/{id}")
	public ResponseEntity<Marca> updateMarca(@PathVariable Long id, @RequestBody Marca marca) {

		try {
			Marca marcaAtualizada = marcaService.updateMarca(id, marca);
			return ResponseEntity.ok(marcaAtualizada);
		} catch (ConstraintViolationException e) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), e);
		} catch (IdNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}

	}

	@Operation(description = "Exclui uma marca existente")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Marca excluída"),
			@ApiResponse(responseCode = "400", description = "id não existe") })
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteMarca(@PathVariable("id") Long id) {

		try {
			Marca marca = marcaService.getMarcaById(id);

			if (marca.isDeletado()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Marca com o id " + id + " já foi deletada");
			}

			marcaService.deleteMarca(marca);
			return ResponseEntity.noContent().build();
		} catch (IdNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
}
