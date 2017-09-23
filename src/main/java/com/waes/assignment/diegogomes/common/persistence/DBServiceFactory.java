package com.waes.assignment.diegogomes.common.persistence;

import com.waes.assignment.diegogomes.common.persistence.mongodb.MongoDBService;

/**
 * An interface to be implemented by factory objects for each type of DB, as needed.
 * 
 * @author Diego Gomes
 *
 */
public interface DBServiceFactory {
	
	/**
	 * Get an instance of the MongoDBService
	 * @return mongoDBService An instance of {@link MongoDBService}
	 */
	MongoDBService getMongoDBService();
	
	/**
	 *  It is possible to add method signatures for other Databases. See the examples below:
	 */
	// CassandraDBService getCassandraDBService();
	// IBMCloudantDBService getIBMCloudantDBService();
	// etc...
}
