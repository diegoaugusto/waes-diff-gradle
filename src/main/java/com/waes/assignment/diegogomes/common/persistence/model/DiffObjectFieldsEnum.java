package com.waes.assignment.diegogomes.common.persistence.model;

/**
 * Enumeration used to list possible sides of a comparison in a Diff operation
 * 
 * @author Diego Gomes
 *
 */
public enum DiffObjectFieldsEnum {
	ID("_id"), LEFT("left"), RIGHT("right");
	
	private String description;
	private DiffObjectFieldsEnum(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public static DiffObjectFieldsEnum getByDescription(String desc) {
		for (DiffObjectFieldsEnum en : DiffObjectFieldsEnum.values()) {
			if (!en.equals(ID) && en.getDescription().equals(desc)) {
				return en;
			}
		}
		return null;
	}
}
