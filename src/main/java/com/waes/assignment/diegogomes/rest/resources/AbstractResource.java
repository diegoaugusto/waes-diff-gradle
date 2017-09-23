package com.waes.assignment.diegogomes.rest.resources;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response.ResponseBuilder;

public abstract class AbstractResource {

	@Context
	protected ServletContext servletContext;
	
	@Context
	protected HttpServletRequest httpServletRequest;
	
	@PostConstruct
	protected void init() {
		// TODO initialize DB access
	}
	
	/**
	 * Constructor
	 */
	protected AbstractResource() {
		
	}
	
	/**
	 * Constructor
	 * @param servletContext Injected {@link ServletContext}
	 * @param servletRequest Injected {@link HttpServletRequest}
	 */
	public AbstractResource(ServletContext servletContext, HttpServletRequest servletRequest) {
		this.servletContext = servletContext;
		this.httpServletRequest = servletRequest;
	}
	
	/**
	 * Disable any caching of the response
	 * @param builder {@link ResponseBuilder}
	 */
	public void setNoCache(ResponseBuilder builder) {
		CacheControl cacheControl = new CacheControl();
		cacheControl.setNoCache(true);
		cacheControl.setMaxAge(0);
		cacheControl.setSMaxAge(0);
		cacheControl.setMustRevalidate(true);
		cacheControl.setPrivate(true);
		builder.expires(new Date(0));
		builder.cacheControl(cacheControl);
	}
	
	/**
	 * Disable proxy caching of the response.
	 * @param builder {@link ResponseBuilder}
	 */
	public void setProxyNoCache(ResponseBuilder builder) {
		CacheControl cacheControl = new CacheControl();
		cacheControl.setPrivate(true);
		builder.cacheControl(cacheControl);
	}
}
