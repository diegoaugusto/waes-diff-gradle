package com.waes.assignment.diegogomes.test.rest.resources.v1.diff;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import com.waes.assignment.diegogomes.rest.exceptions.BadRequestException;
import com.waes.assignment.diegogomes.rest.exceptions.JsonException;
import com.waes.assignment.diegogomes.rest.exceptions.NotFoundException;
import com.waes.assignment.diegogomes.rest.resources.v1.diff.DataWrapper;
import com.waes.assignment.diegogomes.rest.resources.v1.diff.DiffService;

/**
 * Test class that will test specific business operations in the {@link DiffService} class.
 * @author Diego Gomes
 *
 */
public class DiffServiceTest {
	
	/**
	 * Set of tests to test the validateId method of DiffService
	 */
	@Test
	public void testValidateIdWhenValidId() {
		String idStr = "123";
		Integer result = null;
		try {
			result = DiffService.getInstance().validateId(idStr);
		} catch (Exception e) {
			result = -1;
		}
		Assertions.assertEquals(new Integer(123), result);
    }
	
	@Test
	public void testValidateThrowsBadRequestExceptionWhenIdIsNull() {
		String idStr = null;
		Assertions.assertThrows(BadRequestException.class, () -> DiffService.getInstance().validateId(idStr));
    }
	
	@Test
	public void testValidateThrowsBadRequestExceptionWhenIdIsEmpty() {
		String idStr = "";
		Assertions.assertThrows(BadRequestException.class, () -> DiffService.getInstance().validateId(idStr));
    }
	
	@Test
	public void testValidateThrowsNotFoundExceptionWhenIdIsNegative() {
		String idStr = "-1";
		Assertions.assertThrows(NotFoundException.class, () -> DiffService.getInstance().validateId(idStr));
    }
	
	@Test
	public void testValidateThrowsNotFoundExceptionWhenIdIsNotNumber() {
		String idStr = "12abc";
		Assertions.assertThrows(NotFoundException.class, () -> DiffService.getInstance().validateId(idStr));
    }
	
	@Test
	public void testValidateThrowsNotFoundExceptionWhenIdIsHugeNumber() {
		String idStr = "1234123412341234123412341234123423412341234123412341";
		Assertions.assertThrows(NotFoundException.class, () -> DiffService.getInstance().validateId(idStr));
    }
	
	/**
	 * Set of tests to test the validateBodyData method of DiffService
	 */
	@Test
	public void testValidateBodyDataWhenValidBody() {
		String b64Data = "YWJjZGVmZ2hpag==";
		String bodyData = "{\"data\":\""+ b64Data +"\"}";
		InputStream body = new ByteArrayInputStream(bodyData.getBytes());
		DataWrapper actual = null;
		
		try {
			actual = DiffService.getInstance().validateBodyData(body);
		} catch (JsonException e) {}
		
		Assertions.assertEquals(b64Data, (actual != null) ? actual.getData() : null);
    }
}
