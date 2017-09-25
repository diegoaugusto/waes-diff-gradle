package com.waes.assignment.diegogomes.rest.resources.v2.diff;

/**
 * Class used to wrap json data containing the base64 encoded binary data.
 * @author Diego Gomes
 *
 */
public class DataWrapper {

	private String left;
	private String right;

	public String getLeft() {
		return left;
	}
	public void setLeft(String left) {
		this.left = left;
	}
	public String getRight() {
		return right;
	}
	public void setRight(String right) {
		this.right = right;
	}
}
