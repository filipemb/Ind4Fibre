package com.ind4fibre.database.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="alarme_braco",schema = "fibre")
public class AlarmeBraco implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private LocalDateTime data;

	private Boolean valor;

	//bi-directional many-to-one association to BracoRobotico1
	@ManyToOne
	@JoinColumn(name="braco_robotico_id")
	private BracoRobotico bracoRobotico;

	public AlarmeBraco() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getValor() {
		return this.valor;
	}

	public void setValor(Boolean valor) {
		this.valor = valor;
	}

	public BracoRobotico getBracoRobotico() {
		return this.bracoRobotico;
	}

	public void setBracoRobotico(BracoRobotico bracoRobotico) {
		this.bracoRobotico = bracoRobotico;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

}