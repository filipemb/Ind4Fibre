package com.ind4fibre.database.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ind4fibre.database.model.BracoRobotico;


@Repository
public interface BracoRoboticoRepository extends JpaRepository<BracoRobotico, Integer> {

	Optional<BracoRobotico> findByNomeIgnoreCase(String nome);


}
