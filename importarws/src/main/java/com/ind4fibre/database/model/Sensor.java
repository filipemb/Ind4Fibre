package com.ind4fibre.database.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "sensor", schema = "fibre")
public class Sensor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="nome_medicao")
	private String nomeMedicao;

	@OneToMany(mappedBy="sensor")
	private List<LeituraBraco> leituraBracos = new ArrayList<LeituraBraco>();
	
	@OneToMany(mappedBy="sensor")
	private List<LeituraRecurso> leituraRecursos = new ArrayList<LeituraRecurso>();

	public Sensor() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomeMedicao() {
		return this.nomeMedicao;
	}

	public void setNomeMedicao(String nomeMedicao) {
		this.nomeMedicao = nomeMedicao;
	}

	public List<LeituraBraco> getLeituraBracos() {
		return this.leituraBracos;
	}

	public void setLeituraBracos(List<LeituraBraco> leituraBracos) {
		this.leituraBracos = leituraBracos;
	}
	
	public List<LeituraRecurso> getLeituraRecursos() {
		return leituraRecursos;
	}

	public void setLeituraRecursos(List<LeituraRecurso> leituraRecursos) {
		this.leituraRecursos = leituraRecursos;
	}

	public LeituraBraco addLeituraBraco(LeituraBraco leituraBraco) {
		getLeituraBracos().add(leituraBraco);
		leituraBraco.setSensor(this);

		return leituraBraco;
	}

	public LeituraBraco removeLeituraBraco(LeituraBraco leituraBraco) {
		getLeituraBracos().remove(leituraBraco);
		leituraBraco.setSensor(null);

		return leituraBraco;
	}
	
	public LeituraRecurso addLeituraRecurso(LeituraRecurso leituraRecurso) {
		getLeituraRecursos().add(leituraRecurso);
		leituraRecurso.setSensor(this);

		return leituraRecurso;
	}

	public LeituraRecurso removeLeituraRecurso(LeituraRecurso leituraRecurso) {
		getLeituraRecursos().remove(leituraRecurso);
		leituraRecurso.setSensor(null);

		return leituraRecurso;
	}

}