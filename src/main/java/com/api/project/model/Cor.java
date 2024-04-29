package com.api.project.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "cor")
public class Cor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idcor")
	private Long id;
	@NotBlank(message = "o nome da cor precisa ser informado")
	@Column(name = "nomecor")
	private String nomeCor;
	private boolean deletado;

	public Cor() {
	}

	public Cor(String nomecor) {
		this.nomeCor = nomecor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomecor() {
		return nomeCor;
	}

	public void setNomecor(String nomecor) {
		this.nomeCor = nomecor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nomeCor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cor other = (Cor) obj;
		return Objects.equals(id, other.id) && Objects.equals(nomeCor, other.nomeCor);
	}

	public boolean isDeletado() {
		return deletado;
	}

	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}

}