package com.api.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.project.model.CarroCor;

@Repository
public interface CarroCorRepository extends JpaRepository<CarroCor, Long> {

	Optional<CarroCor> findByCorIdAndCarroId(Long corId, Long carroId);

}
