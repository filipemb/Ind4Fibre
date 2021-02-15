package com.ind4fibre.database.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.ind4fibre.database.model.AlarmeBraco;

class AlarmeBracoRepositoryCustomizedImpl implements AlarmeBracoRepositoryCustomized {

	@PersistenceContext
    private EntityManager em;
	
	@Transactional
	public AlarmeBraco saveCommit(AlarmeBraco alarmeBraco) {
		em.persist(alarmeBraco);
		return alarmeBraco;
	}
	
	@Transactional
	public void truncateCommit() {
		em.createQuery("DELETE FROM AlarmeBraco ab").executeUpdate();
	}
	
}
