package com.waes.assignment.diegogomes.rest.resources.v1.diff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import com.google.gson.Gson;
import com.waes.assignment.diegogomes.common.exceptions.InputStreamException;
import com.waes.assignment.diegogomes.common.persistence.DBService;
import com.waes.assignment.diegogomes.common.persistence.DBServiceFactoryCreator;
import com.waes.assignment.diegogomes.common.persistence.model.DiffObject;
import com.waes.assignment.diegogomes.common.persistence.model.DiffObjectFieldsEnum;
import com.waes.assignment.diegogomes.common.persistence.mongodb.MongoDBServiceCreator;
import com.waes.assignment.diegogomes.rest.exceptions.BadRequestException;
import com.waes.assignment.diegogomes.rest.exceptions.Base64Exception;
import com.waes.assignment.diegogomes.rest.exceptions.InternalServerException;
import com.waes.assignment.diegogomes.rest.exceptions.NotFoundException;
import com.waes.assignment.diegogomes.rest.resources.AbstractService;

/**
 * This is the service class where all the business logic related to the diff operation
 * will be implemented.
 * 
 * @author Diego Gomes
 *
 */
public class DiffService extends AbstractService {

	private static DiffService instance;
	private DBService db;
	
	/**
	 * Private contructor
	 */
	private DiffService() {
		// Instantiate the DB service. In this case, MongoDBService - it could be others but their implementation should be provided
		DBServiceFactoryCreator.setInstance(new MongoDBServiceCreator());
		this.db = DBServiceFactoryCreator.getInstance().getDBServiceFactory().getMongoDBService();
	}
	
	/**
	 * Singleton to provide only one instance of the DiffService all over the application
	 * @return
	 */
	public static DiffService getInstance() {
		if (instance == null) {
			instance = new DiffService();
		}
		return instance;
	}

	/**
	 * Get {@link DiffObject} by its id. If it doesn't exist, returns null.
	 * @param id Id of the {@link DiffObject} object
	 * @return {@link DiffObject} object for the id selected or null if it doesn't exist
	 */
	public DiffObject getById(Integer id) {
		return this.getDb().getDiffObjectById(id);
	}
	
	/**
	 * Method used to insert the binary data for one {@link DiffObject}, according to the its side and id
	 * @param id Id of the {@link DiffObject} object
	 * @param side Side of the binary data in the {@link DiffObject} object
	 * @param base64Data Base64 encoded binary data to be stored in the {@link DiffObject} object 
	 * @throws Base64Exception Thrown when the base64Data is not a valid {@link Base64} encoded data
	 * @throws InternalServerException Thrown when there is an internal error to process the {@link DiffObject} or to insert it in the database 
	 */
	public DiffObject insert(Integer id, String side, DataWrapper dataWrapper) throws Base64Exception, InternalServerException {
		byte[] decodedData = null;
		
		try {
			decodedData = Base64.getDecoder().decode(dataWrapper.getData());
		} catch (Exception e) {
			throw new Base64Exception(e);
		}
		
		try {
			DiffObject diffObject = this.getById(id);
			if (diffObject == null) {
				diffObject = new DiffObject();
				diffObject.setId(id);
			}
			
			DiffObjectFieldsEnum diffSide = DiffObjectFieldsEnum.getByDescription(side);
			
			if (diffSide.equals(DiffObjectFieldsEnum.LEFT)) {
				diffObject.setLeft(decodedData);
			} else if (diffSide.equals(DiffObjectFieldsEnum.RIGHT)) {
				diffObject.setRight(decodedData);
			}
			
			diffObject = this.getDb().insertOrUpdate(diffObject);
			
			return diffObject;
		} catch (Exception e) {
			throw new InternalServerException("Error while inserting the binary data.", e);
		}
	}
	
	/**
	 * Method containing the business logic to validate if the left/right binary data are equal or not and why
	 * @param id Id of the {@link DiffObject} object that the comparison should be made.
	 * @throws NotFoundException Thrown when the id is not valid or does not exist
	 */
	public Diff getDiff(Integer id) throws NotFoundException {
		// get object by its id
		DiffObject diffObject = this.getById(id);
		
		// get diff even if the diffObject does not exist
		Diff diff = this.getDiff(id, diffObject);
		
		return diff;
	}

	/**
	 * Get the {@link Diff} based on an id or existing {@link DiffObject}
	 * @param id Id of the {@link DiffObject} object that the comparison should be made.
	 * @param diffObject
	 * @return
	 * @throws NotFoundException
	 */
	private Diff getDiff(Integer id, DiffObject diffObject) throws NotFoundException {
		if (diffObject == null) {
			throw new NotFoundException("Id does not exist.");
		}
		
		byte[] left = diffObject.getLeft();
		byte[] right = diffObject.getRight();
		
		Diff diff = new Diff();
		if (left.length != right.length) {
			// If not of equal size just return that
			diff.setId(id);
			diff.setEqual(false);
			diff.setSameSize(false);
		} else if (Arrays.equals(left, right)) {
			// If equal return that
			diff.setId(id);
			diff.setEqual(true);
			diff.setSameSize(true);
		} else if (left.length == right.length) {
			// If of same size provide insight in where the diffs are, actual diffs are not needed (So mainly offsets + length in the data)
			diff.setId(id);
			diff.setEqual(false);
			diff.setSameSize(true);
			
			List<OffsetLength> offsets = new ArrayList<OffsetLength>();
			diff.setOffsets(offsets);
			for (int o = 0; o < left.length; o++) {
				if (left[o] == right[o]) {
					// When byte comparison is equal AND the last item in the list does not have the lenght value, calculate the lenght.
					if (offsets.size() > 0 && offsets.get(offsets.size() - 1) != null && offsets.get(offsets.size() - 1).getLength() == null) {
						OffsetLength lastOffsetInList = offsets.get(offsets.size() - 1);
						lastOffsetInList.setLength(o - lastOffsetInList.getOffset());	
						// if the last object's offset is 2 and the current position (o) is 5, the lenght will be 3: bytes in positions 2, 3 and 4
					} else {
						continue;
					}
				} else {
					// When the last OffsetLength object in the List does not have value for length attribute AND the byte comparison is different, 
					// continue the iteration until it finds an equal byte comparison. 
					if (offsets.size() > 0 && offsets.get(offsets.size() - 1) != null && offsets.get(offsets.size() - 1).getLength() == null) {
						// ignore
					} else {
						// When the bytes are different, and the last OffsetLength object in the list is complete (containing offset and lenght values),
						// OR when the list is still empty, create a new OffsetLenght object and set its offset position. 
						// The length of this new created object will be empty. It will continue the iteration until it finds a byte that is equal, 
						// then it calculates the lenght based on the current offset
						OffsetLength offsetLength = new OffsetLength();
						offsetLength.setOffset(o);
						offsets.add(offsetLength);
					}
				}
			}
		}
		return diff;
	}
	
	
	// ###### USEFUL METHODS
	/**
	 * Validates the id from a string
	 * @param id
	 * @return
	 * @throws BadRequestException 
	 * @throws NotFoundException 
	 */
	public Integer validateId(final String id) throws BadRequestException, NotFoundException {
		Integer idInt = null;
		if (id == null || "".equals(id)) {
			log.error("ID is empty");
			throw new BadRequestException("ID path parameter is mandatory and must be an integer number.");
		} else {
			try {
				idInt = Integer.parseInt(id);
				if (idInt < 0) {
					throw new Exception("Id is negative.");
				}
			} catch (Exception e) {
				log.error("ID ["+ id +"] is not valid.");
				throw new NotFoundException("ID MUST be a positive Integer value.");
			}
		}
		
		return idInt;
	}
	
	/**
	 * Method created to validate the body data
	 * @param body
	 * @param dataWrapper
	 * @return
	 * @throws BadRequestException 
	 * @throws InternalServerException 
	 */
	public DataWrapper validateBodyData(InputStream body) throws BadRequestException, InternalServerException {
		DataWrapper dataWrapper = null;
		if (body == null) {
			log.error("BODY data is null.");
			throw new BadRequestException("Body of the request is empty.");
		} else {
			String bodyStr = null;
			try {
				bodyStr = getString(body);
				Gson gson = new Gson();
				dataWrapper = gson.fromJson(bodyStr, DataWrapper.class);
			} catch (InputStreamException ise) {
				log.error("InputStream containing body data could not be processed correctly.", ise);
				throw new BadRequestException("Error while processing request body.", ise);
			} catch (Exception e) {
				log.error("Error to transform body data in the internal data type..", e);
				throw new InternalServerException("Error to transform body data in the internal data type. Make sure body looks like {\"data\":\"VGhpcyBpcyBhbiBleGFtcGxlIG9mIHRoZSBkYXRhIGlucHV0Lg==\"} .", e);
			}
		}
		
		if (dataWrapper == null || dataWrapper.getData() == null) {
			log.error("Base64 encoded binary data is empty.");
			throw new BadRequestException("Base64 encoded binary data is empty. Make sure body looks like {\\\"data\\\":\\\"VGhpcyBpcyBhbiBleGFtcGxlIG9mIHRoZSBkYXRhIGlucHV0Lg==\\\"}.");
		}
		return dataWrapper;
	}
	
	/**
	 * Util method to get the string from an {@link InputStream}
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public String getString(InputStream is) throws Exception {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		
		if (is == null) {
			log.error("InputStream is null.");
			throw new InputStreamException("Input Stream is null.");
		}

		String line = null;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			log.error("Error while parsing InputStream.");
			throw new InputStreamException("Error while parsing the input stream.", e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return sb.toString();
	}
	
	// GETTERS and SETTERS
	/**
	 * Method to return the instance created to access the database.
	 * It should be accessible only by this service class.
	 * @return
	 */
	private DBService getDb() {
		return db;
	}
}
