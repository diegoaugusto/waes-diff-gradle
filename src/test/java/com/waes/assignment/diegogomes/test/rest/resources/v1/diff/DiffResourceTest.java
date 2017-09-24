package com.waes.assignment.diegogomes.test.rest.resources.v1.diff;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.waes.assignment.diegogomes.rest.resources.v1.diff.Diff;

//@RunWith(Arquillian.class)
public class DiffResourceTest {

//	@Deployment
//	public static WebArchive create() {
//		return ShrinkWrap.create(WebArchive.class).addPackages(true, "com/waes/assignment/diegogomes")
//				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
//	}
//	
//	@Test
//	@GET
//	@Path("v1/diff/1")
//	@Consumes(MediaType.APPLICATION_XML)
//	public void shouldBeAbleToListAllCustomers(ClientResponse<Diff> response) {
//		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
//
//		Diff diff = response.getEntity();
////		Assert.assertEquals(2, diff.size());
//	}
	
//	@Deployment
//	public static WebArchive createDeployment() {
//	    return ShrinkWrap
//	        .create(WebArchive.class)
//	        .addPackages(true, Filters.exclude(".*Test.*"), DiffResource.class.getPackage(), 
//	        				RestApplication.class.getPackage(), DBService.class.getPackage(), 
//	        				InputStreamException.class.getPackage(), RestException.class.getPackage(), 
//	        				AbstractResource.class.getPackage(), AbstractResponse.class.getPackage())
//	        .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
//	}

//	@Test
//	public void testGetDiff(@ArquillianResource URL base) throws Exception {
//		// GET http://localhost:8080/test/rest/customer/1
//	      ClientRequest request = new ClientRequest(new URL(base, REST_PATH + "/customer/1").toExternalForm());
//	      request.header("Accept", MediaType.APPLICATION_XML);
//
//	      // we're expecting a String back
//	      ClientResponse<String> responseObj = request.get(String.class);
//
//	      Assert.assertEquals(200, responseObj.getStatus());
//	}
//	public void getDiff(@ArquillianResource final WebTarget webTarget) {
//		
//	    final Response response = webTarget
//	        .path("/v1/diff/1")
//	        .request(MediaType.APPLICATION_JSON)
//	        .get();
//	    Assertions.assertEquals(true, response.readEntity(DiffResponse.class));
//	}
	
	
	
	
//	private Client client = ClientBuilder.newClient();
//	
//	@Test
//	@RunAsClient
//    public void testAPI(@ArquillianResource URL deploymentUrl) {
//        // Test your REST service
//        try {
//			WebTarget target = client.target(deploymentUrl.toURI()).path("/v1/diff/1");
//			final Response response = target
//			        .request(MediaType.APPLICATION_JSON)
//			        .get();
//		    Assertions.assertEquals(true, response.readEntity(DiffResponse.class));
//		} catch (URISyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }
}
