package com.waes.assignment.diegogomes.common.persistence;

/**
 * This class uses the Factory method to determine which {@link DBServiceFactory} to create.
 * It is a singleton too, which holds it's sole instance.
 * 
 * @author Diego Gomes
 *
 */
public abstract class DBServiceFactoryCreator {

	// Sole instance
	private static DBServiceFactoryCreator instance;
	
	/**
	 * Constructor
	 */
	public DBServiceFactoryCreator() {
		super();
	}

	/**
	 * It gets the sole instance of the DBServiceFactory
	 * @return
	 */
	public static DBServiceFactoryCreator getInstance() {
		return instance;
	}

	/**
	 * Instead of checking if the instance is null and then create one, this method will receive one DBServiceFactory
	 * instance that will be used across the application until it is changed (whenever it is needed).
	 * 
	 * @param instance {@link DBServiceFactoryCreator}
	 */
	public static void setInstance(DBServiceFactoryCreator instance) {
		DBServiceFactoryCreator.instance = instance;
	}
	
	/**
	 * Abstract method that will be implemented by children Classes
	 * @return {@link DBServiceFactory}
	 */
	public abstract DBServiceFactory getDBServiceFactory();
}
