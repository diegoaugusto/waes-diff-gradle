package com.waes.assignment.diegogomes.rest.resources.v1;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import com.waes.assignment.diegogomes.rest.resources.v1.diff.DiffResource;

/**
 * In this resource class, all the services available in version 1 of the REST API are available
 * 
 * @author Diego Gomes
 *
 */
@Path("/v1")
public class V1Resource {
	
	/**
	 * Provides the DiffResource end-points available for use in version 1
	 * @param servletContext {@link ServletContext}
	 * @param servletRequest {@link HttpServletRequest}
	 * @return
	 */
	@Path("/diff")
	public DiffResource getDiffRecourceV1(@Context ServletContext servletContext, @Context HttpServletRequest servletRequest) {
		return new DiffResource(servletContext, servletRequest);
	}
	
	/*
	 * Other services (paths) could be available as part of version 1 of this REST API.
	 */
}
