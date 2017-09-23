package com.waes.assignment.diegogomes.test.rest.resources.v1.diff;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.waes.assignment.diegogomes.rest.exceptions.BadRequestException;
import com.waes.assignment.diegogomes.rest.exceptions.NotFoundException;
import com.waes.assignment.diegogomes.rest.resources.v1.diff.DiffService;

/**
 * Test class that will test specific business operations in the {@link DiffService} class.
 * @author Diego Gomes
 *
 */
public class DiffServiceTest {	
	@Test
	public void throwsBadRequestExceptionWhenIdIsNull() {
		String idStr = null;
		boolean thrownBadRequest = false;
		try {
			DiffService.getInstance().validateId(idStr);
		} catch (BadRequestException e) {
			thrownBadRequest = true;
		} catch (NotFoundException e) {
			thrownBadRequest = false;
		}
		assertTrue(thrownBadRequest);
    }
	
	@Test
	public void throwsBadRequestExceptionWhenIdIsEmpty() {
		String idStr = "";
		boolean thrownBadRequest = false;
		try {
			DiffService.getInstance().validateId(idStr);
		} catch (BadRequestException e) {
			thrownBadRequest = true;
		} catch (NotFoundException e) {
			thrownBadRequest = false;
		}
		assertTrue(thrownBadRequest);
    }
	
	@Test
	public void throwsNotFoundExceptionWhenIdIsNegative() {
		String idStr = "-1";
		boolean thrownNotFound = false;
		try {
			DiffService.getInstance().validateId(idStr);
		} catch (BadRequestException e) {
			thrownNotFound = false;
		} catch (NotFoundException e) {
			thrownNotFound = true;
		}
		assertTrue(thrownNotFound);
    }
	
	@Test
	public void throwsNotFoundExceptionWhenIdIsNotNumber() {
		String idStr = "12abc";
		boolean thrownNotFound = false;
		try {
			DiffService.getInstance().validateId(idStr);
		} catch (BadRequestException e) {
			thrownNotFound = false;
		} catch (NotFoundException e) {
			thrownNotFound = true;
		}
		assertTrue(thrownNotFound);
    }
	
	@Test
	public void throwsNotFoundExceptionWhenIdIsHugeNumber() {
		String idStr = "1234123412341234123412341234123423412341234123412341";
		boolean thrownNotFound = false;
		try {
			DiffService.getInstance().validateId(idStr);
		} catch (BadRequestException e) {
			thrownNotFound = false;
		} catch (NotFoundException e) {
			thrownNotFound = true;
		}
		assertTrue(thrownNotFound);
    }
}
