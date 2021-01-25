package com.ind4fibre.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ind4fibre.database.model.LeituraBraco;


@Repository
public interface LeituraBracoRepository extends JpaRepository<LeituraBraco, Integer> {



}
