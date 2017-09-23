package com.waes.assignment.diegogomes.rest.exceptions;

/**
 * Exception for a response in the following formats:
 * 		{"code":<error code>, "error":"<error message>"}
 * 		{"code":<error code>, "error":"<error message>", "exception":"<detailed exception message>"}
 * 
 * @author Diego Gomes
 *
 */
public class JsonException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private Integer status;
	private String error;
	private String exception;
	
	/**
	 * Constructor
	 * @param status The status of the response
	 * @param error {@link String} with the error text
	 */
	public JsonException(Integer status, String error) {
		this.status = status;
		this.error = error;
	}
	
	/**
	 * Constructor that accepts the throwable object
	 * @param status The status of the response
	 * @param errorText {@link String} with the error text
	 * @param t Throwable object
	 */
	public JsonException(Integer status, String error, Throwable t) {
		this.status = status;
		this.error = error;
		this.exception = (t == null) ? null : t.getMessage();
	}

	
	// #### GETTERS and SETTERS
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}
}
