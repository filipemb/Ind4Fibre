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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "leitura_braco", schema = "fibre",
	   uniqueConstraints={@UniqueConstraint(name ="leitura_braco_uk", columnNames = {"id", "data", "braco_robotico_id", "sensor_id"})})
public class LeituraBraco implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "data")
	private LocalDateTime data;

	@Column(name = "coordenada_x")
	private BigDecimal coordenadaX;

	@Column(name = "coordenada_y")
	private BigDecimal coordenadaY;

	@Column(name = "coordenada_z")
	private BigDecimal coordenadaZ;

	@Column(name = "elbow_joint")
	private BigDecimal elbowJoint;
	
	@Transient
	private BigDecimal elbowJointVel;

	@Column(name = "robotiq_85_left_knuckle_joint")
	private BigDecimal robotiq85LeftKnuckleJoint;

	@Column(name = "shoulder_lift_joint")
	private BigDecimal shoulderLiftJoint;

	@Transient
	private BigDecimal shoulderLiftJointVel;
	
	@Column(name = "shoulder_pan_joint")
	private BigDecimal shoulderPanJoint;
	
	@Transient
	private BigDecimal shoulderPanJointVel;

	@Column(name = "wrist_1_joint")
	private BigDecimal wrist1Joint;
	
	@Transient
	private BigDecimal wrist1JointVel;

	@Column(name = "wrist_2_joint")
	private BigDecimal wrist2Joint;
	
	@Transient
	private BigDecimal wrist2JointVel;

	@Column(name = "wrist_3_joint")
	private BigDecimal wrist3Joint;
	
	@Transient
	private BigDecimal wrist3JointVel;

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

	public BigDecimal getElbowJointVel() {
		return elbowJointVel;
	}

	public void setElbowJointVel(BigDecimal elbowJointVel) {
		this.elbowJointVel = elbowJointVel;
	}

	public BigDecimal getShoulderLiftJointVel() {
		return shoulderLiftJointVel;
	}

	public void setShoulderLiftJointVel(BigDecimal shoulderLiftJointVel) {
		this.shoulderLiftJointVel = shoulderLiftJointVel;
	}

	public BigDecimal getShoulderPanJointVel() {
		return shoulderPanJointVel;
	}

	public void setShoulderPanJointVel(BigDecimal shoulderPanJointVel) {
		this.shoulderPanJointVel = shoulderPanJointVel;
	}

	public BigDecimal getWrist1JointVel() {
		return wrist1JointVel;
	}

	public void setWrist1JointVel(BigDecimal wrist1JointVel) {
		this.wrist1JointVel = wrist1JointVel;
	}

	public BigDecimal getWrist2JointVel() {
		return wrist2JointVel;
	}

	public void setWrist2JointVel(BigDecimal wrist2JointVel) {
		this.wrist2JointVel = wrist2JointVel;
	}

	public BigDecimal getWrist3JointVel() {
		return wrist3JointVel;
	}

	public void setWrist3JointVel(BigDecimal wrist3JointVel) {
		this.wrist3JointVel = wrist3JointVel;
	}

}