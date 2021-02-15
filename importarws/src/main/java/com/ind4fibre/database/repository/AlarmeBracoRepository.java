package com.ind4fibre.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ind4fibre.database.model.AlarmeBraco;


@Repository
public interface AlarmeBracoRepository extends JpaRepository<AlarmeBraco, Integer>, AlarmeBracoRepositoryCustomized {



}
