package com.waes.assignment.diegogomes.test.rest.resources.v1.diff;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import com.waes.assignment.diegogomes.rest.exceptions.BadRequestException;
import com.waes.assignment.diegogomes.rest.exceptions.InternalServerException;
import com.waes.assignment.diegogomes.rest.exceptions.JsonException;
import com.waes.assignment.diegogomes.rest.exceptions.NotFoundException;
import com.waes.assignment.diegogomes.rest.resources.v1.diff.DataWrapper;
import com.waes.assignment.diegogomes.rest.resources.v1.diff.Diff;
import com.waes.assignment.diegogomes.rest.resources.v1.diff.DiffService;
import com.waes.assignment.diegogomes.rest.resources.v1.diff.OffsetLength;

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
	
	@Test
	public void testValidateBodyDataWhenValidBodyButFormatIsWrong() {
		String b64Data = "YWJjZGVmZ2hpag==";
		String bodyData = "{\"another Name That Is Not Valid Here\":\""+ b64Data +"\"}";
		InputStream body = new ByteArrayInputStream(bodyData.getBytes());
		Assertions.assertThrows(BadRequestException.class, () -> DiffService.getInstance().validateBodyData(body));
    }
	
	@Test
	public void testValidateBodyDataWhenNullBody() {
		InputStream body = null;
		Assertions.assertThrows(BadRequestException.class, () -> DiffService.getInstance().validateBodyData(body));
    }
	
	@Test
	public void testValidateBodyDataWhenBodyFormatIsNotExpected() {
		InputStream body = new ByteArrayInputStream("format of data that is not expected.".getBytes());
		Assertions.assertThrows(InternalServerException.class, () -> DiffService.getInstance().validateBodyData(body));
    }
	
	@Test
	public void testValidateBodyDataWhenBodyIsEmpty() {
		InputStream body = new ByteArrayInputStream("".getBytes());
		Assertions.assertThrows(BadRequestException.class, () -> DiffService.getInstance().validateBodyData(body));
    }
	
	
	
	/**
	 * Set of tests for the compare method of DiffService
	 */
	@Test 
	public void testCompareEqualInput() {
		byte[] left = "abcdefghij".getBytes();
		byte[] right = "abcdefghij".getBytes();
		
		Diff actual = null;
		try {
			actual = DiffService.getInstance().compare(left, right);
			Assertions.assertTrue(actual.getEqual());
			Assertions.assertTrue(actual.getSameSize());
		} catch (BadRequestException e) {
		}
	}
	
	@Test 
	public void testCompareDifferentInputSameSize() {
		byte[] left = "abcdefghij".getBytes();
		byte[] right = "Xbcdefghij".getBytes();
		
		Diff actual = null;
		try {
			actual = DiffService.getInstance().compare(left, right);
			
			Assertions.assertFalse(actual.getEqual());
			Assertions.assertTrue(actual.getSameSize());
			OffsetLength expected = new OffsetLength(0, 1);
			Assertions.assertEquals(expected, actual.getOffsets().get(0));
		} catch (BadRequestException e) {
		}
	}
	
	@Test 
	public void testCompareDifferentInputSameSizeTwoOffsets() {
		byte[] left = "abcdefghij".getBytes();
		byte[] right = "ab345fg89j".getBytes();
		
		Diff actual = null;
		try {
			actual = DiffService.getInstance().compare(left, right);
			
			Assertions.assertFalse(actual.getEqual());
			Assertions.assertTrue(actual.getSameSize());
			OffsetLength expected0 = new OffsetLength(2, 3);
			OffsetLength expected1 = new OffsetLength(7, 2);
			Assertions.assertEquals(expected0, actual.getOffsets().get(0));
			Assertions.assertEquals(expected1, actual.getOffsets().get(1));
		} catch (BadRequestException e) {
		}
	}
	
	@Test
	public void testCompareDifferentInput() {
		byte[] left = "abcdefghij".getBytes();
		byte[] right = "12345abcdefghij".getBytes();
		
		Diff actual = null;
		try {
			actual = DiffService.getInstance().compare(left, right);
			
			Assertions.assertFalse(actual.getEqual());
			Assertions.assertFalse(actual.getSameSize());
		} catch (BadRequestException e) {
		}
	}
	
	@Test
	public void testCompareDifferentInputEmpty() {
		byte[] left = "abcdefghij".getBytes();
		byte[] right = "".getBytes();
		
		Diff actual = null;
		try {
			actual = DiffService.getInstance().compare(left, right);
			
			Assertions.assertFalse(actual.getEqual());
			Assertions.assertFalse(actual.getSameSize());
		} catch (BadRequestException e) {
		}
	}
	
	@Test
	public void testCompareDifferentInputLeftNull() {
		byte[] left = null;
		byte[] right = "abcdefghij".getBytes();
		
		Assertions.assertThrows(BadRequestException.class, () -> DiffService.getInstance().compare(left, right));
	}
	
	@Test
	public void testCompareDifferentInputRightNull() {
		byte[] left = "abcdefghij".getBytes();
		byte[] right = null;
		
		Assertions.assertThrows(BadRequestException.class, () -> DiffService.getInstance().compare(left, right));
	}
	
}
