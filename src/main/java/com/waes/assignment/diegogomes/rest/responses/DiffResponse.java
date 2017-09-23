package com.waes.assignment.diegogomes.rest.responses;

import com.waes.assignment.diegogomes.rest.resources.v1.diff.Diff;

/**
 * Class that represents one type of response to the REST service.
 * 
 * Example:
 *   - {"status":200, "message":"request processed successfully", "diff":{"id":1, "equal":false, "sameSize":false, "offsets":[{"offset":0, "length":123}, {"offset":1, "length":21}, ...]}}
 * 
 * @author Diego Gomes
 *
 */
public class DiffResponse extends AbstractResponse {
	private Diff diff;

	/**
	 * Constructor
	 */
	public DiffResponse() {
		
	}
	
	/**
	 * Constructor
	 * @param diff
	 */
	public DiffResponse(Diff diff) {
		this.diff = diff;
	}
	
	public Diff getDiff() {
		return diff;
	}

	public void setDiff(Diff diff) {
		this.diff = diff;
	}
}
