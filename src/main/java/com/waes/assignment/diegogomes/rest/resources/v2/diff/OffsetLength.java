package com.waes.assignment.diegogomes.rest.resources.v2.diff;

/**
 * Class that represents the offset and the length in the data that is different from another one.
 * 
 * @author Diego Gomes
 *
 */
public class OffsetLength {

	private Integer offset;
	private Integer length;
	
	public OffsetLength() {
		
	}
	
	public OffsetLength(Integer offset, Integer length) {
		this.offset = offset;
		this.length = length;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((length == null) ? 0 : length.hashCode());
		result = prime * result + ((offset == null) ? 0 : offset.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OffsetLength other = (OffsetLength) obj;
		if (length == null) {
			if (other.length != null)
				return false;
		} else if (!length.equals(other.length))
			return false;
		if (offset == null) {
			if (other.offset != null)
				return false;
		} else if (!offset.equals(other.offset))
			return false;
		return true;
	}
	
	
}
