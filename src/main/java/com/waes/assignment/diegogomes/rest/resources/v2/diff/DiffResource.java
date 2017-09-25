package com.waes.assignment.diegogomes.rest.resources.v2.diff;

import java.io.InputStream;
import java.util.Base64;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.waes.assignment.diegogomes.common.persistence.model.DiffObject;
import com.waes.assignment.diegogomes.rest.exceptions.JsonException;
import com.waes.assignment.diegogomes.rest.exceptions.RestException;
import com.waes.assignment.diegogomes.rest.responses.DiffObjectResponse;

/**
 * Version 2 of of the Diff services 
 * @author Diego Gomes
 *
 */
public class DiffResource extends com.waes.assignment.diegogomes.rest.resources.v1.diff.DiffResource {

	private DiffService diffService;
	
	/**
	 * Constructor
	 */
	public DiffResource() {
		super();
		this.diffService = DiffService.getInstance();
	}
	
	/** 
	 * Constructor
	 * @param servletContext {@link ServletContext}
	 * @param servletRequest {@link HttpServletRequest}
	 */
	public DiffResource(ServletContext servletContext, HttpServletRequest servletRequest) {
		super(servletContext, servletRequest);
		this.diffService = DiffService.getInstance();
	}
	
	@OPTIONS
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response getAllOptions() {
		return Response.ok().build();
	}
	
	/**
	 * End-point that will store the data stream for one specific {id} and the two sides of the diff comparison.
	 * It is an improvement of the requirement "Provide 2 http endpoints that accepts JSON base64 encoded binary data on both endpoints"
	 * 
	 * Examples of valid POST calls:
	 *   - <host>/v1/diff/234/
	 * 
	 * Examples of invalid POST calls:
	 *   - <host>/v1/diff/-2
	 *   - <host>/v1/diff/12abc
	 *   
	 * Example of body content (JSON format):
	 *   - {"left":"VGhpcyBpcyBhbiBleGFtcGxlIG9mIHRoZSBkYXRhIGlucHV0Lg==", "right":"VGhpcyBpcyBhbiBleGFtcGxlIG9mIHRoZSBkYXRhIGlucHV0Lg=="}
	 * 
	 * @param id The id of the Diff object created to compare both binary data. Only {@link Integer} value is expected. 
	 * 		  There is a validation to return a respective error message when it's not a valid id.
	 * @return Json data containing the status code and the message about the insertion of binary data
	 */
	@POST
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response postBinaryData(@PathParam("id") final String id, InputStream body) {
		// validate input of <id> parameter
		log.info("Validating id ["+ id +"]");
		try {
			Integer idInt = this.getDiffService().validateId(id);
			
			// validate input of <body>
			log.info("Validating <body> data");
			DataWrapper dataWrapper = this.getDiffService().validateBodyData(body);
		
			// insert binary data
			log.info("Inserting data in the database for id:["+ id +"]");
			DiffObject diffObject = this.getDiffService().insert(idInt, dataWrapper);
			
			DiffObjectResponse diffObjResponse = new DiffObjectResponse(diffObject);
			diffObjResponse.setStatus(Response.Status.OK.getStatusCode());
			diffObjResponse.setMessage("Request successfully processed.");
			
			return Response.ok().entity(diffObjResponse).build();
		} catch (JsonException je) {
			throw new RestException(je);
		}
	}

	
	// GETTERS and SETTERS
	private DiffService getDiffService() {
		return this.diffService;
	}
	
	public static void main(String[] args) {
		System.out.println(new String(Base64.getEncoder().encode("abcdefghijklmno".getBytes())));
	}
}