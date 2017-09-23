package com.waes.assignment.diegogomes.common.persistence;

import com.waes.assignment.diegogomes.common.persistence.model.DiffObject;

public interface DBService {

	public DiffObject getDiffObjectById(Integer id);
	public DiffObject insertOrUpdate(DiffObject diffObject);
}
