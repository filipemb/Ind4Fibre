package com.ind4fibre.database.repository;

import com.ind4fibre.database.model.LeituraRecurso;

interface LeituraRecursoRepositoryCustomized {
	
	public LeituraRecurso saveCommit(LeituraRecurso leituraRecurso);
	
	public void truncateCommit();
	
}