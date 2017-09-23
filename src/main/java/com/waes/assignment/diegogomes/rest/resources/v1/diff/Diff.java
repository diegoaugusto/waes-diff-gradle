package com.waes.assignment.diegogomes.rest.resources.v1.diff;

import java.util.List;

/**
 * Class that represents the output of the the differences between two binary data.
 *  
 * @author Diego Gomes
 *
 */
public class Diff {

	private Integer id;
	private Boolean equal = false;
	private Boolean sameSize = false;
	private List<OffsetLength> offsets;	// List of offsets where the differences are happening
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Boolean getEqual() {
		return equal;
	}
	public void setEqual(Boolean equal) {
		this.equal = equal;
	}
	public Boolean getSameSize() {
		return sameSize;
	}
	public void setSameSize(Boolean sameSize) {
		this.sameSize = sameSize;
	}
	public List<OffsetLength> getOffsets() {
		return offsets;
	}
	public void setOffsets(List<OffsetLength> offsets) {
		this.offsets = offsets;
	}
}
