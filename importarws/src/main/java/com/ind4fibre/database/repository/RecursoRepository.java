package com.ind4fibre.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ind4fibre.database.model.Recurso;


@Repository
public interface RecursoRepository extends JpaRepository<Recurso, Integer> {

	Recurso findByNomeAndIlha(String nome, String ilha);


}
