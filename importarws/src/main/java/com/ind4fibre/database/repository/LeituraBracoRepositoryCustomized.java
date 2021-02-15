package com.ind4fibre.database.repository;

import com.ind4fibre.database.model.LeituraBraco;

interface LeituraBracoRepositoryCustomized {
	
	public LeituraBraco saveCommit(LeituraBraco leituraBraco);
	
	public void truncateCommit();
	
}