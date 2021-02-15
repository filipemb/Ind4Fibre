package com.ind4fibre.database.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.ind4fibre.database.model.LeituraBraco;

class LeituraBracoRepositoryCustomizedImpl implements LeituraBracoRepositoryCustomized {

	@PersistenceContext
    private EntityManager em;
	
	@Transactional
	public LeituraBraco saveCommit(LeituraBraco leituraBraco) {
		em.persist(leituraBraco);
		return leituraBraco;
	}
	
	@Transactional
	public void truncateCommit() {
		em.createQuery("DELETE FROM LeituraBraco lb").executeUpdate();
	}
	
}
