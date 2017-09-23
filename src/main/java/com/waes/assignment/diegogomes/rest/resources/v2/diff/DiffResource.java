package com.waes.assignment.diegogomes.rest.resources.v2.diff;

import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.waes.assignment.diegogomes.rest.resources.AbstractResource;

@Path("/v2")
public class DiffResource extends AbstractResource {

	/** 
	 * Constructor
	 * @param servletContext {@link ServletContext}
	 * @param servletRequest {@link HttpServletRequest}
	 */
	public DiffResource(ServletContext servletContext, HttpServletRequest servletRequest) {
		super(servletContext, servletRequest);
	}
	
	@OPTIONS
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllOptions() {
		return Response.ok().build();
	}
	
	// <host>/v1/diff/<ID>
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDiff(@PathParam("id") final Integer id) {
		// TODO get both byte arrays from DB
		byte[] left = null;
		byte[] right = null;
		
		// TODO compare both
		if (left.length != right.length) {
			// return that they are not equal --> different sizes
		} else if (Arrays.equals(left, right)) {
			// return that they are equal
		} else {
			// return offset and lenght
		}
		
		return Response.ok().build();
	}
	
	// <host>/v1/diff/<ID>/left
	@POST
	@Path("{id}/left")
	@Produces(MediaType.APPLICATION_JSON)
	public Response postLeft(@PathParam("id") final Integer id) {
		return Response.ok().build();
	}
	
	// <host>/v1/diff/<ID>/right
	@POST
	@Path("{id}/right")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response postRight(@PathParam("id") final Integer id, @FormParam("right") final String rightData) {
		System.out.println(rightData);
		byte[] decodedRight = Base64.getDecoder().decode(rightData);
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		// Creating Credentials 
		MongoCredential credential;
		credential = MongoCredential.createCredential("waes", "waesDb", "waespassword".toCharArray());
		System.out.println("Connected to the database successfully");
		
		MongoDatabase database = mongoClient.getDatabase("waesDb");
		
		// Creating a collection
		database.createCollection("sampleCollection");
		System.out.println("Collection created successfully");

		// Retrieving a collection
		MongoCollection<Document> collection = database.getCollection("sampleCollection");
		System.out.println("Collection sampleCollection selected successfully");
		
		Document document = new Document("id", id).append("description", "right data")
				.append("right", decodedRight);
		collection.insertOne(document); 

		
		return Response.ok().build();
	}
	
	
	// TODO FIXME Remove
	@GET
	@Path("hello")
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello() {
		return "Hello 2!!";
	}
	
	public static void main(String[] args) {
		String str = new String("testando base 64");
		System.out.println(Base64.getEncoder().encodeToString(str.getBytes()));
		
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		// Creating Credentials 
		MongoCredential credential;
		credential = MongoCredential.createCredential("waes", "waesDb", "waespassword".toCharArray());
		System.out.println("Connected to the database successfully");
		
		MongoDatabase database = mongoClient.getDatabase("waesDb");

		// Retrieving a collection
		MongoCollection<Document> collection = database.getCollection("sampleCollection");
		System.out.println("Collection sampleCollection selected successfully");
		
		// Getting the iterable object
		FindIterable<Document> iterDoc = collection.find();
		int i = 1;

		// Getting the iterator
		Iterator it = iterDoc.iterator();

		while (it.hasNext()) {
			System.out.println(it.next());
			i++;
		}
	}
}