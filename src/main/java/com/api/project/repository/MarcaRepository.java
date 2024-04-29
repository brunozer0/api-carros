package com.api.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.project.model.Marca;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {

	boolean existsByMarca(String marca);

	Marca findByMarca(String marca);

}
