package com.waes.assignment.diegogomes.common.persistence.model;

import org.bson.Document;
import org.bson.types.Binary;

public class DiffObject {

	private Integer id;
	private byte[] left;
	private byte[] right;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public byte[] getLeft() {
		return left;
	}
	public void setLeft(byte[] left) {
		this.left = left;
	}
	public byte[] getRight() {
		return right;
	}
	public void setRight(byte[] right) {
		this.right = right;
	}
	
	/**
	 * Default constructor
	 */
	public DiffObject() {
		
	}
	
	/**
	 * Constructor that receives a MongoDB Document as attribute
	 */
	public DiffObject(Document doc) {
		super();
		this.setId(doc.getInteger(DiffObjectFieldsEnum.ID.getDescription()));
		
		Binary binLeft = (Binary) doc.get(DiffObjectFieldsEnum.LEFT.getDescription());
		this.setLeft(binLeft == null ? null : binLeft.getData());
		
		Binary binRight = (Binary) doc.get(DiffObjectFieldsEnum.RIGHT.getDescription());
		this.setRight(binRight == null ? null : binRight.getData());
		
		
	}
	
	/**
	 * Returns a MongoDB Document instance based on the content of the DiffObject
	 */
	public Document getMongoDBDocument() {
		Document doc = new Document(DiffObjectFieldsEnum.ID.getDescription(), this.getId())
				.append(DiffObjectFieldsEnum.LEFT.getDescription(), this.getLeft())
				.append(DiffObjectFieldsEnum.RIGHT.getDescription(), this.getRight());
		return doc;
	}
}
