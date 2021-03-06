package com.waes.assignment.diegogomes.test.common.persistence;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.waes.assignment.diegogomes.common.persistence.mongodb.MongoDBServiceCreator;
import com.waes.assignment.diegogomes.common.persistence.mongodb.MongoDBServiceFactory;

/**
 * Class to test the creation of the {@link MongoDBServiceCreator} class
 * @author Diego Gomes
 *
 */
public class MongoDBServiceCreatorTest {

	@Test 
	public void testGetDBServiceFactory() {
		MongoDBServiceCreator classUnderTest = new MongoDBServiceCreator();
        assertTrue("getDBServiceFactory should return an instance of MongoDBServiceFactory", classUnderTest.getDBServiceFactory() instanceof MongoDBServiceFactory);
    }
}
