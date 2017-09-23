package com.waes.assignment.diegogomes.rest.responses;

/**
 * Class that represents an abstract response for REST services.
 * It will contain a status code and message text. This object should be converted in a json object.
 * 
 * Example:
 *   - {"status":200, "message":"request processed successfully"}
 * 
 * @author Diego Gomes
 *
 */
public class AbstractResponse {
	private int status;
	private String message;

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
