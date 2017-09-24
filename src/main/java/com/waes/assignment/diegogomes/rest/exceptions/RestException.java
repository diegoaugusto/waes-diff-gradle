package com.waes.assignment.diegogomes.rest.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class RestException extends WebApplicationException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that receives an instance of {@link JsonException}
	 * @param jsonException Current {@link JsonException} launched by business service methods
	 */
	public RestException(JsonException jsonException) {
		super(Response.status(jsonException.getStatus())
				.entity(jsonException)
				.type(MediaType.APPLICATION_JSON)
				.build());
	}
	
	/**
	 * Constructor
	 * @param status The status of the response
	 * @param errorText {@link String} with the error text
	 */
	public RestException(Response.Status status, String errorText) {
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
	public RestException(Response.Status status, String errorText, Throwable t) {
		super(t, Response.status(status)
				.entity("{\"code\":" + status.getStatusCode() + ", \"error\": \"" + errorText + "\", \"exception\":\"" + ((t != null) ? t.getMessage() : "none") + "\"}")
				.type(MediaType.APPLICATION_JSON)
				.build());
	}
}
