package com.waes.assignment.diegogomes.rest.resources.v2;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import com.waes.assignment.diegogomes.rest.resources.v2.diff.DiffResource;

/**
 * In this resource class, all the services available in version 2 of the REST API are available
 * 
 * @author Diego Gomes
 *
 */
@Path("/v2")
public class V2Resource {
	
	/**
	 * Provides the DiffResource end-points available for use in version 2
	 * @param servletContext {@link ServletContext}
	 * @param servletRequest {@link HttpServletRequest}
	 * @return
	 */
	@Path("/diff")
	public DiffResource getDiffRecource(@Context ServletContext servletContext, @Context HttpServletRequest servletRequest) {
		return new DiffResource(servletContext, servletRequest);
	}
	
	/*
	 * Other services could be available as part of version 2 of this REST API.
	 */
}
