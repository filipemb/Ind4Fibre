package com.ind4fibre.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ind4fibre.database.model.Sensor;


@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {



}
