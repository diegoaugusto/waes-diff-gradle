package com.waes.assignment.diegogomes.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.waes.assignment.diegogomes.rest.resources.v1.V1Resource;
import com.waes.assignment.diegogomes.rest.resources.v2.V2Resource;

/**
 * This is the entry point of the REST application to process the REST requests for the WAES assignment
 * given to Diego Gomes.
 * 
 * For each new version added, please, update the API documentation available.
 * @author Diego Gomes
 *
 */
@ApplicationPath("")
public class RestApplication extends Application {
	
	@Override
	public Set<Class<?>> getClasses() {
	    final Set<Class<?>> classes = new HashSet<>();
	    
	    // register root resource for version 1 of the API
	    classes.add(V1Resource.class);
	    
	    // register root resource for version 2 of the API
	    classes.add(V2Resource.class);
	    
	    return classes;
	}
}