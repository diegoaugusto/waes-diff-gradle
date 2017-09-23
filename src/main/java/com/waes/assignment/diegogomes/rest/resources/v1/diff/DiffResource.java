package com.waes.assignment.diegogomes.rest.resources.v1.diff;

import java.net.HttpURLConnection;
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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.waes.assignment.diegogomes.common.persistence.model.DiffObjectFieldsEnum;
import com.waes.assignment.diegogomes.rest.resources.AbstractResource;

@Path("/v1")
public class DiffResource extends AbstractResource {

	private DiffService diffService = null;
	
	/** 
	 * Constructor
	 * @param servletContext {@link ServletContext}
	 * @param servletRequest {@link HttpServletRequest}
	 */
	public DiffResource(ServletContext servletContext, HttpServletRequest servletRequest) {
		super(servletContext, servletRequest);
		this.diffService = DiffService.getInstance();
	}
	
	
	/**
	 * It shows all possible calls available in this end-point.
	 * @return
	 */
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
		
		this.getDiffService().getDiff(id);
		
		return Response.ok().build();
	}
	
	/**
	 * 
	 * @param id
	 * @param side
	 * @param base64Data
	 * @return
	 */
	// <host>/v1/diff/<ID>/right
	@POST
	@Path("{id}/{side}")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response postRight(@PathParam("id") final Integer id, @PathParam("side") final String side, @FormParam("data") String base64Data) {
		if (id == null) {
			throw new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("ID path parameter is mandatory").build());
		}
		if (side == null || DiffObjectFieldsEnum.getByDescription(side) == null) {
			throw new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("SIDE path parameter is mandatory. Possible values are").build());
		}
		if (base64Data == null || base64Data.equals("")) {
			throw new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("DATA Form parameter is mandatory.").build());
		}
		
		this.getDiffService().insert(id, side, base64Data);
		
		return Response.ok().build();
	}
	
	
	private DiffService getDiffService() {
		return this.diffService;
	}
	
	
	
	
	
	
	
	
	
	// TODO FIXME Remove
	@GET
	@Path("hello")
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello() {
		return "Hello 1!!";
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