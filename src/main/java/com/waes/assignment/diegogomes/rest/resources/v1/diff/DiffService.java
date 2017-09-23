package com.waes.assignment.diegogomes.rest.resources.v1.diff;

import java.util.Arrays;
import java.util.Base64;

import org.bson.Document;

import com.waes.assignment.diegogomes.common.persistence.DBService;
import com.waes.assignment.diegogomes.common.persistence.DBServiceFactoryCreator;
import com.waes.assignment.diegogomes.common.persistence.model.DiffObject;
import com.waes.assignment.diegogomes.common.persistence.model.DiffObjectFieldsEnum;
import com.waes.assignment.diegogomes.common.persistence.mongodb.MongoDBServiceCreator;

/**
 * This is the service class where all the business logic related to the diff operation
 * will be implemented.
 * 
 * @author Diego Gomes
 *
 */
public class DiffService {

	private static DiffService instance;
	private DBService db;
	
	/**
	 * Private contructor
	 */
	private DiffService() {
		// Instantiate the DB service. In this case, MongoDBService - it could be others but their implementation should be provided
		DBServiceFactoryCreator.setInstance(new MongoDBServiceCreator());
		this.db = DBServiceFactoryCreator.getInstance().getDBServiceFactory().getMongoDBService();
	}
	
	/**
	 * Singleton to provide only one instance of the DiffService all over the application
	 * @return
	 */
	public static DiffService getInstance() {
		if (instance == null) {
			instance = new DiffService();
		}
		return instance;
	}

	public DiffObject getById(Integer id) {
		return this.getDb().getDiffObjectById(id);
	}
	
	public void insert(Integer id, String side, String base64Data) {
		byte[] decodedData = Base64.getDecoder().decode(base64Data);
		
		DiffObject diffObject = this.getById(id);
		if (diffObject == null) {
			diffObject = new DiffObject();
			diffObject.setId(id);
		}
		
		DiffObjectFieldsEnum diffSide = DiffObjectFieldsEnum.getByDescription(side);
		
		if (diffSide.equals(DiffObjectFieldsEnum.LEFT)) {
			diffObject.setLeft(decodedData);
		} else if (diffSide.equals(DiffObjectFieldsEnum.RIGHT)) {
			diffObject.setRight(decodedData);
		}
		
		this.getDb().insertOrUpdate(diffObject);
	}
	
	public void getDiff(Integer id) {
		DiffObject diffObject = this.getById(id);
		
		// TODO get both byte arrays from DB
		byte[] left = diffObject.getLeft();
		byte[] right = diffObject.getRight();
		
		// TODO compare both
		if (left.length != right.length) {
			// return that they are not equal --> different sizes
			System.out.println("NOT Equal - Different sizes");
		} else if (Arrays.equals(left, right)) {
			// return that they are equal
			System.out.println("They ARE Equal");
		} else {
			// return offset and lenght
			System.out.println("TODO implementar");
		}
	}
	
	// GETTERS and SETTERS
	/**
	 * Method to return the instance created to access the database.
	 * It should be accessible only by this service class.
	 * @return
	 */
	private DBService getDb() {
		return db;
	}
}
