package com.waes.assignment.diegogomes.common.persistence.mongodb;

import com.waes.assignment.diegogomes.common.persistence.DBServiceFactory;
import com.waes.assignment.diegogomes.common.persistence.DBServiceFactoryCreator;

/**
 * Class that represents the creator of MongoDBServices.
 * 
 * @author Diego Gomes
 *
 */
public class MongoDBServiceCreator extends DBServiceFactoryCreator {

	@Override
	public DBServiceFactory getDBServiceFactory() {
		return new MongoDBServiceFactory();
	}

}
