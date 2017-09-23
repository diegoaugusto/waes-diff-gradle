package com.waes.assignment.diegogomes.rest.resources.v1.diff;

/**
 * Class that represents the offset and the length in the data that is different from another one.
 * 
 * @author Diego Gomes
 *
 */
public class OffsetLength {

	private Integer offset;
	private Integer length;

	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
}
