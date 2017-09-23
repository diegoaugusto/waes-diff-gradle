package com.waes.assignment.diegogomes.rest.responses;

import com.waes.assignment.diegogomes.common.persistence.model.DiffObject;

/**
 * Class that represents one type of response to the REST service.
 * 
 * Example:
 *   - {"status":200, "message":"request processed successfully", "diffObject":{"id":1, "left":"", "right":""}}
 * 
 * @author Diego Gomes
 *
 */
public class DiffObjectResponse extends AbstractResponse {
	private DiffObject diffObject;

	/**
	 * Constructor
	 */
	public DiffObjectResponse() {
		
	}
	
	/**
	 * Constructor
	 * @param diffObj
	 */
	public DiffObjectResponse(DiffObject diffObj) {
		this.diffObject = diffObj;
	}
	
	public DiffObject getDiffObject() {
		return diffObject;
	}

	public void setDiffObject(DiffObject diffObject) {
		this.diffObject = diffObject;
	}
}
