package com.waes.assignment.diegogomes.rest.exceptions;

import javax.ws.rs.core.Response;

/**
 * {@link JsonException} with {@link Response.Status#NOT_FOUND} status showing an internal exception 
 * while processing the request
 * 
 * @author Diego Gomes
 *
 */
public class NotFoundException extends JsonException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param errorMessage Message used to describe the error that happened while processing the request
	 */
	public NotFoundException(String errorMessage) {
		super(Response.Status.NOT_FOUND.getStatusCode(), errorMessage);
	}
	
	/**
	 * Constructor
	 * @param errorMessage Message used to describe the error that happened while processing the request
	 * @param t {@link Throwable} object
	 */
	public NotFoundException(String errorMessage, Throwable t) {
		super(Response.Status.NOT_FOUND.getStatusCode(), errorMessage, t);
	}
}
