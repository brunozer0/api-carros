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

import com.api.project.exceptions.CorDuplicadaException;
import com.api.project.exceptions.IdNotFoundException;
import com.api.project.model.Cor;
import com.api.project.services.CorService;

import jakarta.validation.ConstraintViolationException;

@RestController
@RequestMapping("/api/concessionaria/cores")
@CrossOrigin(origins = "http://localhost:4200")
public class CorController {

	private final CorService corService;

	public CorController(CorService corService) {
		this.corService = corService;
	}

	@GetMapping
	public List<Cor> getCores() {
		return corService.getCores();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cor> getCorById(@PathVariable("id") Long id) {
		try {
			Cor corId = corService.getCorById(id);
			return ResponseEntity.ok(corId);
		} catch (IdNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}

	}

	@PostMapping
	public ResponseEntity<Cor> cadastrarCor(@RequestBody Cor cor) throws CorDuplicadaException {

		try {
			Cor CorSalvo = corService.salvarCor(cor);
			return ResponseEntity.created(URI.create("/api/livros/" + CorSalvo.getId())).body(CorSalvo);

		} catch (CorDuplicadaException e) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), e);
		} catch (ConstraintViolationException e) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), e);
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<Cor> updateCor(@PathVariable Long id, @RequestBody Cor cor) {

		try {
			Cor CorUpdated = corService.updateCor(id, cor);
			return ResponseEntity.ok(CorUpdated);
		} catch (ConstraintViolationException e) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), e);
		} catch (IdNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCor(@PathVariable("id") Long id) {
		try {

			Cor cor = corService.getCorById(id);

			if (cor.isDeletado()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cor com o id " + id + " já foi deletada");
			}

			corService.deleteCor(cor);
			return ResponseEntity.noContent().build();
		} catch (IdNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

}