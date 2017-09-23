package com.waes.assignment.diegogomes.common.exceptions;

import java.io.InputStream;

/**
 * Exception to indicate a failure while processing an {@link InputStream}
 * @author Diego Gomes
 *
 */
public class InputStreamException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param errorMessage Error message to provide details about the exception.
	 */
	public InputStreamException(String errorMessage) {
		super(errorMessage);
	}
	
	/**
	 * Constructor
	 * @param errorMessage Error message to provide details about the exception.
	 * @param t {@link Throwable} that caused the exception.
	 */
	public InputStreamException(String errorMessage, Throwable t) {
		super(errorMessage, t);
	}
}
