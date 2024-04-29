package com.api.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.project.model.Cor;

@Repository
public interface CorRepository extends JpaRepository<Cor, Long> {

	Cor findByNomeCor(String nomeCor);

	boolean existsByNomeCor(String nomeCor);

}
