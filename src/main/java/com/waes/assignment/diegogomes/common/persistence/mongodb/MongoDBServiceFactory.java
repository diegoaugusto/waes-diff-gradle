package com.waes.assignment.diegogomes.common.persistence.mongodb;

import com.waes.assignment.diegogomes.common.persistence.DBServiceFactory;

/**
 * Factory class that provides the instance to MongoDB Services
 * 
 * @author Diego Gomes
 *
 */
public class MongoDBServiceFactory implements DBServiceFactory {
	
	/**
	 * Method returns an instance of {@link MongoDBService}
	 */
	@Override
	public MongoDBService getMongoDBService() {
		return MongoDBService.getInstance();
	}

}
