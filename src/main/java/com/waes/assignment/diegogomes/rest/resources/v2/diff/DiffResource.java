package com.waes.assignment.diegogomes.rest.resources.v2.diff;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v2")
public class DiffResource extends com.waes.assignment.diegogomes.rest.resources.v1.diff.DiffResource {

	/** 
	 * Constructor
	 * @param servletContext {@link ServletContext}
	 * @param servletRequest {@link HttpServletRequest}
	 */
	public DiffResource(ServletContext servletContext, HttpServletRequest servletRequest) {
		super(servletContext, servletRequest);
	}
	
	@OPTIONS
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response getAllOptions() {
		return Response.ok().build();
	}
	
	
}