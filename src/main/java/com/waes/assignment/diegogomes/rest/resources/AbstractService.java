package com.waes.assignment.diegogomes.rest.resources;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Parent class of all those classes that will implement any sort of business logic for specific end-points/services
 * in the application.
 * @author Diego Gomes
 *
 */
public class AbstractService {

	protected Log log = LogFactory.getLog(getClass().getName());
}
