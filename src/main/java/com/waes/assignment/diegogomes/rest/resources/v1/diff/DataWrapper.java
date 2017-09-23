package com.waes.assignment.diegogomes.rest.resources.v1.diff;

/**
 * Class used to wrap json data containing the base64 encoded binary data.
 * @author Diego Gomes
 *
 */
public class DataWrapper {

	private String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
