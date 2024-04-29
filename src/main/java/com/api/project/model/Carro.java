package com.api.project.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "carro")
public class Carro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idcarro")
	private Long id;
	@NotBlank(message = "O nome do carro precisa ser informado")
	@Column(name = "nomecarro")
	private String nomeCarro;

	@NotNull(message = "O ano de fabricação precisa ser informado")
	@Column(name = "anofabricacaocarro")
	private int anoFabricacaoCarro;

	@NotNull(message = "O ano do modelo precisa ser informado")
	@Column(name = "anomodelocarro")
	private int anoModeloCarro;

	@NotBlank(message = "O modelo do carro precisa ser informado")
	@Column(name = "modelocarro")
	private String modeloCarro;
	@ManyToOne
	@JoinColumn(name = "marca_idmarca", referencedColumnName = "idmarca")
	private Marca marca;
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "carro_cor", joinColumns = @JoinColumn(name = "carro_idcarro"), inverseJoinColumns = @JoinColumn(name = "cor_idcor"))
	private List<Cor> cores;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeCarro() {
		return nomeCarro;
	}

	public void setNomeCarro(String nomeCarro) {
		this.nomeCarro = nomeCarro;
	}

	public int getAnoFabricacaoCarro() {
		return anoFabricacaoCarro;
	}

	public void setAnoFabricacaoCarro(int anoFabricacaoCarro) {
		this.anoFabricacaoCarro = anoFabricacaoCarro;
	}

	public int getAnoModeloCarro() {
		return anoModeloCarro;
	}

	public void setAnoModeloCarro(int anoModeloCarro) {
		this.anoModeloCarro = anoModeloCarro;
	}

	public String getModeloCarro() {
		return modeloCarro;
	}

	public void setModeloCarro(String modeloCarro) {
		this.modeloCarro = modeloCarro;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public List<Cor> getCores() {
		return cores;
	}

	public void setCores(List<Cor> cores) {
		this.cores = cores;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nomeCarro);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carro other = (Carro) obj;
		return Objects.equals(id, other.id) && Objects.equals(nomeCarro, other.nomeCarro);
	}
	
}
