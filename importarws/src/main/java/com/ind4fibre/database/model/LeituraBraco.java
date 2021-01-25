package com.ind4fibre.database.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "leitura_braco", 
	   uniqueConstraints={@UniqueConstraint(name ="leitura_braco_uk", columnNames = {"id", "data", "braco_robotico_id", "sensor_id"})})
public class LeituraBraco implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "data")
	private LocalDateTime data;

	private Boolean alarme;

	@Column(name = "coordenada_x")
	private BigDecimal coordenadaX;

	@Column(name = "coordenada_y")
	private BigDecimal coordenadaY;

	@Column(name = "coordenada_z")
	private BigDecimal coordenadaZ;

	@Column(name = "elbow_joint")
	private BigDecimal elbowJoint;

	@Column(name = "robotiq_85_left_knuckle_joint")
	private BigDecimal robotiq85LeftKnuckleJoint;

	@Column(name = "shoulder_lift_joint")
	private BigDecimal shoulderLiftJoint;

	@Column(name = "shoulder_pan_joint")
	private BigDecimal shoulderPanJoint;

	@Column(name = "wrist_1_joint")
	private BigDecimal wrist1Joint;

	@Column(name = "wrist_2_joint")
	private BigDecimal wrist2Joint;

	@Column(name = "wrist_3_joint")
	private BigDecimal wrist3Joint;

	@ManyToOne
	@JoinColumn(name = "braco_robotico_id")
	private BracoRobotico bracoRobotico;

	@ManyToOne
	@JoinColumn(name = "sensor_id")
	private Sensor sensor;

	public LeituraBraco() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public Boolean getAlarme() {
		return this.alarme;
	}

	public void setAlarme(Boolean alarme) {
		this.alarme = alarme;
	}

	public BigDecimal getCoordenadaX() {
		return this.coordenadaX;
	}

	public void setCoordenadaX(BigDecimal coordenadaX) {
		this.coordenadaX = coordenadaX;
	}

	public BigDecimal getCoordenadaY() {
		return this.coordenadaY;
	}

	public void setCoordenadaY(BigDecimal coordenadaY) {
		this.coordenadaY = coordenadaY;
	}

	public BigDecimal getCoordenadaZ() {
		return this.coordenadaZ;
	}

	public void setCoordenadaZ(BigDecimal coordenadaZ) {
		this.coordenadaZ = coordenadaZ;
	}

	public BigDecimal getElbowJoint() {
		return this.elbowJoint;
	}

	public void setElbowJoint(BigDecimal elbowJoint) {
		this.elbowJoint = elbowJoint;
	}

	public BigDecimal getRobotiq85LeftKnuckleJoint() {
		return this.robotiq85LeftKnuckleJoint;
	}

	public void setRobotiq85LeftKnuckleJoint(BigDecimal robotiq85LeftKnuckleJoint) {
		this.robotiq85LeftKnuckleJoint = robotiq85LeftKnuckleJoint;
	}

	public BigDecimal getShoulderLiftJoint() {
		return this.shoulderLiftJoint;
	}

	public void setShoulderLiftJoint(BigDecimal shoulderLiftJoint) {
		this.shoulderLiftJoint = shoulderLiftJoint;
	}

	public BigDecimal getShoulderPanJoint() {
		return this.shoulderPanJoint;
	}

	public void setShoulderPanJoint(BigDecimal shoulderPanJoint) {
		this.shoulderPanJoint = shoulderPanJoint;
	}

	public BigDecimal getWrist1Joint() {
		return this.wrist1Joint;
	}

	public void setWrist1Joint(BigDecimal wrist1Joint) {
		this.wrist1Joint = wrist1Joint;
	}

	public BigDecimal getWrist2Joint() {
		return this.wrist2Joint;
	}

	public void setWrist2Joint(BigDecimal wrist2Joint) {
		this.wrist2Joint = wrist2Joint;
	}

	public BigDecimal getWrist3Joint() {
		return this.wrist3Joint;
	}

	public void setWrist3Joint(BigDecimal wrist3Joint) {
		this.wrist3Joint = wrist3Joint;
	}

	public BracoRobotico getBracoRobotico() {
		return this.bracoRobotico;
	}

	public void setBracoRobotico(BracoRobotico bracoRobotico) {
		this.bracoRobotico = bracoRobotico;
	}

	public Sensor getSensor() {
		return this.sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

}