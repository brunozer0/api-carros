package com.api.project.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.project.exceptions.IdNotFoundException;
import com.api.project.exceptions.MarcaDuplicadaException;
import com.api.project.model.Marca;
import com.api.project.repository.MarcaRepository;
import com.api.project.services.MarcaService;

@Service
public class MarcaServiceImpl implements MarcaService {

	@Autowired
	private MarcaRepository marcaRepository;

	@Override
	public Marca salvarMarca(Marca marca) throws MarcaDuplicadaException {
		if (marcaRepository.existsByMarca(marca.getMarca())) {
			throw new MarcaDuplicadaException("A marca " + marca.getMarca() + " já foi cadastrada");
		}
		return marcaRepository.save(marca);
	}

	@Override
	public Marca updateMarca(Long id, Marca marca) {
		Optional<Marca> marcaOptional = marcaRepository.findById(id);
		if (marcaOptional.isPresent()) {
			Marca marcaAtualizada = marcaOptional.get();
			marcaAtualizada.setMarca(marca.getMarca());
			return marcaRepository.save(marcaAtualizada);
		} else {
			throw new IdNotFoundException("Marca com o ID " + id + " não encontrada");
		}
	}

	@Override
	public void deleteMarca(Marca marca) {
		Optional<Marca> idMarca = marcaRepository.findById(marca.getId());
		if (idMarca.isPresent()) {
			marca.setDeletado(true);
			marca.setMarca("indisponível");
			marcaRepository.save(marca);
		}
	}

	@Override
	public Marca getMarcaById(Long id) {
		return marcaRepository.findById(id)
				.orElseThrow(() -> new IdNotFoundException("Marca com o ID " + id + " não encontrada"));
	}

	@Override
	public List<Marca> getMarcas() {
		return marcaRepository.findAll();
	}
}
