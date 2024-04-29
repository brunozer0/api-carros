package com.api.project.services;

import java.util.List;

import com.api.project.exceptions.CorDuplicadaException;
import com.api.project.model.Cor;

public interface CorService {

	Cor salvarCor(Cor cor) throws CorDuplicadaException;

	Cor updateCor(Long id, Cor cor);

	List<Cor> getCores();

	Cor getCorById(Long id);

	void deleteCor(Cor cor);
}
