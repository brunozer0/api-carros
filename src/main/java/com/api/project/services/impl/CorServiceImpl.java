package com.api.project.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.project.exceptions.CorDuplicadaException;
import com.api.project.exceptions.IdNotFoundException;
import com.api.project.model.Cor;
import com.api.project.repository.CarroCorRepository;
import com.api.project.repository.CorRepository;
import com.api.project.services.CorService;

@Service
public class CorServiceImpl implements CorService {

	@Autowired
	private CorRepository corRepository;
	@Autowired
	CarroCorRepository carroCorRepository;

	@Override
	public Cor salvarCor(Cor cor) throws CorDuplicadaException {

		if (corRepository.existsByNomeCor(cor.getNomecor())) {
			throw new CorDuplicadaException("a cor " + cor.getNomecor() + " já foi cadastrada");
		}
		return corRepository.save(cor);
	}

	@Override
	public Cor updateCor(Long id, Cor cor) {
		Optional<Cor> corOptional = corRepository.findById(id);
		if (corOptional.isPresent()) {
			Cor corAtualizada = corOptional.get();
			corAtualizada.setNomecor(cor.getNomecor());
			return corRepository.save(corAtualizada);
		} else {
			throw new IdNotFoundException("Cor com o ID " + id + " não encontrada");
		}
	}

	@Override
	public void deleteCor(Cor cor) {
		Optional<Cor> idCor = corRepository.findById(cor.getId());
		if (idCor.isPresent()) {
			cor.setDeletado(true);
			cor.setNomecor("indisponível");
			corRepository.save(cor);
		}
	}

	@Override
	public Cor getCorById(Long id) {
		return corRepository.findById(id)
				.orElseThrow(() -> new IdNotFoundException("Cor com o ID " + id + " não encontrada"));
	}

	@Override
	public List<Cor> getCores() {
		return corRepository.findAll();
	}

}
