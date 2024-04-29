package com.api.project.services;

import java.util.List;

import com.api.project.exceptions.MarcaDuplicadaException;
import com.api.project.model.Marca;

public interface MarcaService {

	Marca salvarMarca(Marca marca) throws MarcaDuplicadaException;

	Marca updateMarca(Long id, Marca marca);

	List<Marca> getMarcas();

	Marca getMarcaById(Long id);

	void deleteMarca(Marca marca);
}
