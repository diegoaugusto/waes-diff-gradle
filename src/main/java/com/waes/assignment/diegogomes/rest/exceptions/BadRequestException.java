package com.waes.assignment.diegogomes.rest.exceptions;

import javax.ws.rs.core.Response;

/**
 * {@link JsonException} with {@link Response.Status#BAD_REQUEST} status showing an  exception 
 * while processing the request
 * 
 * @author Diego Gomes
 *
 */
public class BadRequestException extends JsonException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param errorMessage Message used to describe the error that happened while processing the request
	 */
	public BadRequestException(String errorMessage) {
		super(Response.Status.BAD_REQUEST.getStatusCode(), errorMessage);
	}
	
	/**
	 * Constructor
	 * @param errorMessage Message used to describe the error that happened while processing the request
	 * @param t {@link Throwable} object
	 */
	public BadRequestException(String errorMessage, Throwable t) {
		super(Response.Status.BAD_REQUEST.getStatusCode(), errorMessage, t);
	}
}
