package com.api.project.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.project.exceptions.IdNotFoundException;
import com.api.project.exceptions.CarroDuplicadoException;
import com.api.project.model.Carro;
import com.api.project.model.Cor;
import com.api.project.model.Marca;
import com.api.project.repository.CarroRepository;
import com.api.project.repository.CorRepository;
import com.api.project.repository.MarcaRepository;
import com.api.project.services.CarroService;

@Service
public class CarroServiceImpl implements CarroService {

	@Autowired
	CarroRepository carroRepository;

	@Autowired
	MarcaRepository marcaRepository;
	@Autowired
	private CorRepository corRepository;

	@Override
	public Carro salvarCarro(Carro carro) throws CarroDuplicadoException {
		if (carroRepository.existsCarro(carro.getNomeCarro(), carro.getAnoFabricacaoCarro(), carro.getAnoModeloCarro(),
				carro.getModeloCarro())) {
			throw new CarroDuplicadoException(
					"Já existe um carro com nome, ano de fabricação, ano do modelo e modelo cadastrado.");
		}
		Marca marca = carro.getMarca();
		if (marca.getId() == null) {
			if (marcaRepository.existsByMarca(marca.getMarca())) {
				marca = marcaRepository.findByMarca(marca.getMarca());
			} else {
				marca = marcaRepository.save(marca);
			}
			carro.setMarca(marca);
		}


		List<Cor> coresSalvas = new ArrayList<>();
		for (Cor cor : carro.getCores()) {
			if (cor.getId() != null) {
				Optional<Cor> corExistenteOptional = corRepository.findById(cor.getId());
				corExistenteOptional.ifPresent(coresSalvas::add);
			} else {
				Cor corExistente = corRepository.findByNomeCor(cor.getNomecor());
				if (corExistente != null) {
					coresSalvas.add(corExistente);
				} else {
					Cor corSalva = corRepository.save(cor);
					coresSalvas.add(corSalva);
				}
			}
		}
		carro.setCores(coresSalvas);
		return carroRepository.save(carro);
	}

	@Override
	public Carro updateCarro(Long id, Carro carro) {
		Optional<Carro> carroOptional = carroRepository.findById(id);

		if (carroOptional.isPresent()) {
			Carro carroAtualizado = carroOptional.get();

			carroAtualizado.setNomeCarro(carro.getNomeCarro());
			carroAtualizado.setAnoFabricacaoCarro(carro.getAnoFabricacaoCarro());
			carroAtualizado.setAnoModeloCarro(carro.getAnoModeloCarro());
			carroAtualizado.setModeloCarro(carro.getModeloCarro());

			Marca marca = carro.getMarca();
			if (marca.getId() == null) {
				if (marcaRepository.existsByMarca(marca.getMarca())) {
					marca = marcaRepository.findByMarca(marca.getMarca());
				} else {
					marca = marcaRepository.save(marca);
				}
			}
			carroAtualizado.setMarca(marca);

			List<Cor> coresSalvas = new ArrayList<>();
			for (Cor cor : carro.getCores()) {
				if (cor.getId() != null) {
					Optional<Cor> corExistenteOptional = corRepository.findById(cor.getId());
					corExistenteOptional.ifPresent(coresSalvas::add);
				} else {
					Cor corExistente = corRepository.findByNomeCor(cor.getNomecor());
					if (corExistente != null) {
						coresSalvas.add(corExistente);
					} else {
						Cor corSalva = corRepository.save(cor);
						coresSalvas.add(corSalva);
					}
				}
			}
			carroAtualizado.setCores(coresSalvas);

			return carroRepository.save(carroAtualizado);
		} else {
			throw new IdNotFoundException("Carro com o id " + id + " não encontrado");
		}

	}

	@Override
	public void deleteCarro(Long id) {
		Optional<Carro> idCarro = carroRepository.findById(id);
		if (idCarro.isPresent()) {
			carroRepository.deleteById(id);
		} else {
			throw new IdNotFoundException("Carro com o id " + id + " não existe");
		}
	}

	@Override
	public Carro getCarroById(Long id) {
		return carroRepository.findById(id)
				.orElseThrow(() -> new IdNotFoundException("Carro com o id " + id + " não encontrado"));

	}

	@Override
	public List<Carro> getCarros() {
		return carroRepository.findAll();

	}
}
