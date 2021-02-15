package com.ind4fibre.database.repository;

import com.ind4fibre.database.model.AlarmeBraco;

interface AlarmeBracoRepositoryCustomized {
	
	public AlarmeBraco saveCommit(AlarmeBraco alarmeBraco);
	
	public void truncateCommit();
	
}