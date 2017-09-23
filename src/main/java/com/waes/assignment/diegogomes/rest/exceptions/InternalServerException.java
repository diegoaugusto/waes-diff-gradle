package com.waes.assignment.diegogomes.rest.exceptions;

import javax.ws.rs.core.Response;

/**
 * {@link JsonException} with {@link Response.Status#INTERNAL_SERVER_ERROR} status showing an internal exception 
 * while processing the request
 * 
 * @author Diego Gomes
 *
 */
public class InternalServerException extends JsonException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param errorMessage Message used to describe the error that happened while processing the request
	 */
	public InternalServerException(String errorMessage) {
		super(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), errorMessage);
	}
	
	/**
	 * Constructor
	 * @param errorMessage Message used to describe the error that happened while processing the request
	 * @param t {@link Throwable} object
	 */
	public InternalServerException(String errorMessage, Throwable t) {
		super(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), errorMessage, t);
	}
}
