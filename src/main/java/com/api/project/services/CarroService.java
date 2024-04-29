package com.api.project.services;

import java.util.List;

import com.api.project.exceptions.CarroDuplicadoException;
import com.api.project.model.Carro;

public interface CarroService {

	Carro salvarCarro(Carro carro) throws CarroDuplicadoException;

	Carro updateCarro(Long id, Carro carro) throws CarroDuplicadoException;

	void deleteCarro(Long id);

	Carro getCarroById(Long id);

	List<Carro> getCarros();
}
