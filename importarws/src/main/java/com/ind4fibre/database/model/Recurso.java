package com.ind4fibre.database.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "recurso", schema = "fibre")
public class Recurso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String ilha;

	private String nome;

	private String tipo;
	
	@OneToMany(mappedBy="recurso")
	private List<LeituraRecurso> leituraRecursos = new ArrayList<LeituraRecurso>();

	public Recurso() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIlha() {
		return this.ilha;
	}

	public void setIlha(String ilha) {
		this.ilha = ilha;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<LeituraRecurso> getLeituraRecursos() {
		return leituraRecursos;
	}

	public void setLeituraRecursos(List<LeituraRecurso> leituraRecursos) {
		this.leituraRecursos = leituraRecursos;
	}
	
	public LeituraRecurso addLeituraRecurso(LeituraRecurso leituraRecurso) {
		getLeituraRecursos().add(leituraRecurso);
		leituraRecurso.setRecurso(this);

		return leituraRecurso;
	}

	public LeituraRecurso removeLeituraRecurso(LeituraRecurso leituraRecurso) {
		getLeituraRecursos().remove(leituraRecurso);
		leituraRecurso.setRecurso(null);

		return leituraRecurso;
	}

}