package com.ind4fibre.database.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.ind4fibre.database.model.LeituraRecurso;

class LeituraRecursoRepositoryCustomizedImpl implements LeituraRecursoRepositoryCustomized {

	@PersistenceContext
    private EntityManager em;
	
	@Transactional
	public LeituraRecurso saveCommit(LeituraRecurso leituraRecurso) {
		em.persist(leituraRecurso);
		return leituraRecurso;
	}
	
	@Transactional
	public void truncateCommit() {
		em.createQuery("DELETE FROM LeituraRecurso lr").executeUpdate();
	}
	
}
