package com.waes.assignment.diegogomes.rest.resources.v1.diff;

import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.waes.assignment.diegogomes.common.persistence.model.DiffObject;
import com.waes.assignment.diegogomes.common.persistence.model.DiffObjectFieldsEnum;
import com.waes.assignment.diegogomes.rest.exceptions.BadRequestException;
import com.waes.assignment.diegogomes.rest.exceptions.JsonException;
import com.waes.assignment.diegogomes.rest.exceptions.RestException;
import com.waes.assignment.diegogomes.rest.resources.AbstractResource;
import com.waes.assignment.diegogomes.rest.responses.DiffObjectResponse;
import com.waes.assignment.diegogomes.rest.responses.DiffResponse;

/**
 * Version 1 of of the Diff services 
 * @author Diego Gomes
 *
 */
public class DiffResource extends AbstractResource {

	private DiffService diffService = null;
	
	/**
	 * Constructor
	 */
	public DiffResource() {
		super();
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
	
	/**
	 * It shows all possible calls available in this end-point.
	 * @return
	 */
	@OPTIONS
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllOptions() {
		//GsonBuilder gb = new GsonBuilder();
		// TODO implement
		return Response.ok().build();
	}
	
	/**
	 * End-point that will compare the left and right binary data and return the corresponding message to client about the
	 * comparison between the two binary data.
	 * 
	 * Example of valid calls:
	 *   - <host>/v1/diff/<ID>
	 *   - <host>/v1/diff/123
	 *   
	 * Example of invalid calls:
	 *   - <host>/v1/diff/-2
	 *   - <host>/v1/diff/1abc
	 * 
	 * @param id The id of the Diff object created to compare both binary data. Only {@link Integer} value is expected. 
	 * 		  	 There is a validation to return a respective error message when it's not a valid id.
	 * @return Json data containing the status code and the message about the comparison between the left/right binary data 
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDiff(@PathParam("id") final String id) {
		// validate input of <id> parameter
		log.info("Validating id ["+ id +"]");
		Integer idInt = null;
		try {
			idInt = this.getDiffService().validateId(id);
			
			// call service method to compare both binary data and get the diff between them
			log.info("Comparing binary data for id ["+ idInt +"]");
			Diff diff = this.getDiffService().getDiff(idInt);
			
			DiffResponse diffResponse = new DiffResponse(diff);
			diffResponse.setStatus(Response.Status.OK.getStatusCode());
			diffResponse.setMessage("Request successfully processed.");
			
			return Response.ok().entity(diffResponse).build();
		} catch (JsonException je) {
			throw new RestException(je);
		}
	}

	
	/**
	 * End-point that will store the data stream for one specific {id} and one specific {side} of the diff comparison.
	 * It implements requirement "Provide 2 http endpoints that accepts JSON base64 encoded binary data on both endpoints"
	 * 
	 * Examples of valid calls:
	 *   - <host>/v1/diff/234/right
	 *   - <host>/v1/diff/234/left
	 * 
	 * Examples of invalid calls:
	 *   - <host>/v1/diff/-2/right
	 *   - <host>/v1/diff/12abc/left
	 *   
	 * Example of body content (JSON format):
	 *   - {"data":"VGhpcyBpcyBhbiBleGFtcGxlIG9mIHRoZSBkYXRhIGlucHV0Lg=="}
	 * 
	 * @param id The id of the Diff object created to compare both binary data. Only {@link Integer} value is expected. 
	 * 		  There is a validation to return a respective error message when it's not a valid id.
	 * @param side The side of the Diff comparison. Possible values are "right" and "left".
	 * @return Json data containing the status code and the message about the insertion of binary data
	 */
	@POST
	@Path("{id}/{side}")
	@Consumes({MediaType.APPLICATION_JSON/*, MediaType.APPLICATION_FORM_URLENCODED*/})
	@Produces(MediaType.APPLICATION_JSON)
	public Response postBinaryData(@PathParam("id") final String id, @PathParam("side") final String side, InputStream body) {
		// validate input of <id> parameter
		log.info("Validating id ["+ id +"]");
		try {
			Integer idInt = this.getDiffService().validateId(id);
			
			// validate input of <side> parameter
			log.info("Validating parameter <side> ["+ side +"]");
			if (side == null || DiffObjectFieldsEnum.getByDescription(side) == null) {
				throw new RestException(new BadRequestException("SIDE path parameter is mandatory. Possible values are: [left,right]"));
			}
			
			// validate input of <body>
			log.info("Validating <body> data");
			DataWrapper dataWrapper = this.getDiffService().validateBodyData(body);
		
			// insert binary data
			log.info("Inserting data in the database for id:["+ id +"], side:["+ side +"]");
			DiffObject diffObject = this.getDiffService().insert(idInt, side, dataWrapper);
			
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
}