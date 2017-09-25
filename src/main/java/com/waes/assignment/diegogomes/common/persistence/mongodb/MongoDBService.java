package com.waes.assignment.diegogomes.common.persistence.mongodb;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.waes.assignment.diegogomes.common.persistence.DBService;
import com.waes.assignment.diegogomes.common.persistence.model.DiffObject;
import com.waes.assignment.diegogomes.common.persistence.model.DiffObjectFieldsEnum;

/**
 * Class responsible to handle all the interaction with the MongoDB instance
 * @author Diego Gomes
 *
 */
public class MongoDBService implements DBService {

	// Singleton instance
	private static MongoDBService instance = null;
	private MongoClient mongoClient = null;
	private MongoCredential credential = null;
	private MongoDatabase db = null;
	
	private static final String USERNAME = "waes";
	private static final String DATABASENAME = "waesDB";
	private static final String USERPASSWORD = "waespassword";
	private static final String DIFF_OBJECTS_COLLECTIONNAME = "diffObjects";
	
	/**
	 * Constructor used to initiate the MongoDB instance
	 */
	private MongoDBService() {
		// Init database
		mongoClient = new MongoClient( "localhost" , 27017 );
		
		// Creating Credentials 
		credential = MongoCredential.createCredential(USERNAME, DATABASENAME, USERPASSWORD.toCharArray());
		
		// Getting the DB
		db = mongoClient.getDatabase(DATABASENAME);
		
		// Get the Diff Object collection. If it doesn't exist, create it
		MongoCollection<Document> collection = db.getCollection(DIFF_OBJECTS_COLLECTIONNAME);
		if (collection == null) {
			db.createCollection(DIFF_OBJECTS_COLLECTIONNAME);
		}
	}
	
	/**
	 * Implementing a singleton for this service that will handle all the actions with the MongoDB
	 * @return instance {@link MongoDBService}
	 */
	public static MongoDBService getInstance() {
		if (instance == null) {
			instance = new MongoDBService();
		}
		return instance;
	}
	
	private MongoCollection<Document> getDiffObjectsCollection() {
		return this.db.getCollection(DIFF_OBJECTS_COLLECTIONNAME);
	}

	/**
	 * Get an DiffObject by its id. If it doesn't exist, returns null.
	 */
	@Override
	public DiffObject getDiffObjectById(Integer id) {
		FindIterable<Document> docs = this.getDiffObjectsCollection().find(Filters.eq(DiffObjectFieldsEnum.ID.getDescription(), id));
		Document doc = docs.first();
		return (doc == null) ? null : new DiffObject(doc);
	}

	/**
	 * Method used to insert a new DiffObject in the database or update it, if it exists.
	 */
	@Override
	public DiffObject insertOrUpdate(DiffObject diffObject) {
		Document mongoDBDocument = diffObject.getMongoDBDocument();
		this.getDiffObjectsCollection().deleteOne(Filters.eq(DiffObjectFieldsEnum.ID.getDescription(), diffObject.getId()));
		this.getDiffObjectsCollection().insertOne(mongoDBDocument);
		return diffObject;
	}
}
