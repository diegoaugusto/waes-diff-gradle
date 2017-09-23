package com.waes.assignment.diegogomes.rest.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Exception for a response in the following formats:
 * 		{"code":<error code>, "error":"<error message>"}
 * 		{"code":<error code>, "error":"<error message>", "exception":"<detailed exception message>"}
 * 
 * @author Diego Gomes
 *
 */
public class JsonException extends WebApplicationException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param status The status of the response
	 * @param errorText {@link String} with the error text
	 */
	public JsonException(Response.Status status, String errorText) {
		super(Response.status(status)
				.entity("{\"code\":" + status.getStatusCode() + ", \"error\": \"" + errorText + "\"}")
				.type(MediaType.APPLICATION_JSON)
				.build());
	}
	
	/**
	 * Constructor that accepts the throwable object
	 * @param status The status of the response
	 * @param errorText {@link String} with the error text
	 * @param t Throwable object
	 */
	public JsonException(Response.Status status, String errorText, Throwable t) {
		super(t, Response.status(status)
				.entity("{\"code\":" + status.getStatusCode() + ", \"error\": \"" + errorText + "\", \"exception\":\"" + ((t != null) ? t.getMessage() : "none") + "\"}")
				.type(MediaType.APPLICATION_JSON)
				.build());
	}
}
