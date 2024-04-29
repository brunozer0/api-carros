package com.api.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.project.model.Carro;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {

	@Query("SELECT COUNT(c) > 0 FROM Carro c WHERE c.nomeCarro = ?1 AND c.anoFabricacaoCarro = ?2 AND c.anoModeloCarro = ?3 AND c.modeloCarro = ?4")
	boolean existsCarro(String nomeCarro, int anoFabricacaoCarro, int anoModeloCarro, String modeloCarro);


}
