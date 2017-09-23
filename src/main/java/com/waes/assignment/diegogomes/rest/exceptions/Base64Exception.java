package com.waes.assignment.diegogomes.rest.exceptions;

import javax.ws.rs.core.Response;

/**
 * {@link JsonException} with {@link Response.Status#BAD_REQUEST} status showing an exception while processing
 * the Base64 encoded data
 * 
 * @author Diego Gomes
 *
 */
public class Base64Exception extends JsonException {

	private static final long serialVersionUID = 1L;
	private static final String BASE64_EXCEPTION_MESSAGE = "This is not a valid Base64 encoded value.";

	/**
	 * Constructor
	 */
	public Base64Exception() {
		super(Response.Status.BAD_REQUEST, BASE64_EXCEPTION_MESSAGE);
	}
	
	/**
	 * Constructor
	 * @param t {@link Throwable} object
	 */
	public Base64Exception(Throwable t) {
		super(Response.Status.BAD_REQUEST, BASE64_EXCEPTION_MESSAGE, t);
	}
}
