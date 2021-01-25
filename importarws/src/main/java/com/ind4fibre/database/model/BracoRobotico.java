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
@Table(name="braco_robotico")
public class BracoRobotico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String nome;

	//bi-directional many-to-one association to LeituraBraco
	@OneToMany(mappedBy="bracoRobotico")
	private List<LeituraBraco> leituraBracos = new ArrayList<LeituraBraco>();

	public BracoRobotico() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<LeituraBraco> getLeituraBracos() {
		return this.leituraBracos;
	}

	public void setLeituraBracos(List<LeituraBraco> leituraBracos) {
		this.leituraBracos = leituraBracos;
	}

	public LeituraBraco addLeituraBraco(LeituraBraco leituraBraco) {
		getLeituraBracos().add(leituraBraco);
		leituraBraco.setBracoRobotico(this);

		return leituraBraco;
	}

	public LeituraBraco removeLeituraBraco(LeituraBraco leituraBraco) {
		getLeituraBracos().remove(leituraBraco);
		leituraBraco.setBracoRobotico(null);

		return leituraBraco;
	}

}