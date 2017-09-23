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
	 * End-point that will store the data stream for one specific {id} and one specific {side} of the diff comparison.
	 * It implements requirement "Provide 2 http endpoints that accepts JSON base64 encoded binary data on both endpoints"
	 * 
	 * Examples of calls:
	 * - <host>/v1/diff/234/right
	 * - <host>/v1/diff/234/left
	 * 
	 * @param id The id of the Diff object created to compare both binary data. Only {@link Integer} value is expected
	 * @param side The side of the Diff comparison. Possible values are "right" and "left".
	 * @param base64Data Binary data encoded in Base64
	 * @return
	 */
	@POST
	@Path("{id}/{side}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
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
	
	
	
	
	// GETTERS and SETTERS
	private DiffService getDiffService() {
		return this.diffService;
	}
}