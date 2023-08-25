/**
 * This class is the implementation of the Offering Service
 * 
 * @Author_name: AnkushJadhav
 * @File_name: OfferingServiceImpl.java
 * @Created_Date: 5/7/2023
 */
package com.kloc.crm.Service.ServiceImpl;

// Here all the imports which is necessary and used in this class
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.kloc.crm.Entity.Offering;
import com.kloc.crm.Entity.Status;
import com.kloc.crm.Exception.DataNotFoundException;
import com.kloc.crm.Exception.InvalidInput;
import com.kloc.crm.Exception.NullDataException;
import com.kloc.crm.Repository.OfferingRepository;
import com.kloc.crm.Repository.StatusRepo;
import com.kloc.crm.Service.OfferingService;
import com.kloc.crm.dto.OfferingDTO;

@Service
public class OfferingServiceImpl implements OfferingService {
	// offering repository to perform database operation on the offering table
	@Autowired
	OfferingRepository offeringRepository;

	// status repository to get status from the status table
	@Autowired
	StatusRepo statusRepository;

	// Logger object to use logging messages in the class
	private static final Logger logger = Logger.getLogger(OfferingServiceImpl.class.getName());

	// Create a new offering with the all data and create a offering id.
	@Override
	public ResponseEntity<String> CreateOffering(Offering offering)
	{
		logger.info("Creating new offering...");
		try {
			if (offering != null && !offering.toString().equals(new Offering().toString()))
			{
				if (offering.getOfferingName() == null || offering.getOfferingName().isEmpty())
					throw new NullDataException("Please enter offer name.");
				
				if (offering.getOfferingCategory() == null || offering.getOfferingCategory().getStatusValue() == null || offering.getOfferingCategory().getStatusValue().isEmpty())
					throw new NullDataException("Please enter offer category.");
				
				if (offering.getOfferingType() == null || offering.getOfferingType().getStatusValue() == null || offering.getOfferingType().getStatusValue().isEmpty())
					throw new NullDataException("Please enter offer type.");
	
				List<Status> allStatusCategory = statusRepository.findAll().stream()
						.filter(e -> e.getTableName().equalsIgnoreCase("offering") && e.getStatusValue().equalsIgnoreCase(offering.getOfferingCategory().getStatusValue()))
						.collect(Collectors.toList());
				
				if (allStatusCategory.size() > 1) 
				{
					logger.warning("Duplicate offering category in database.");
					throw new InternalError("Something went wrong.");
				} 
				else if (allStatusCategory.isEmpty())
					throw new InvalidInput("offering category not found please check offering category.");
				else
					offering.setOfferingCategory(allStatusCategory.get(0));
				
				List<Status> allStatusType = statusRepository.findAll().stream()
						.filter(e -> e.getTableName().equalsIgnoreCase("offering") && e.getStatusValue().equalsIgnoreCase(offering.getOfferingType().getStatusValue()))
						.collect(Collectors.toList());
				
				if (allStatusType.size() > 1) 
				{
					logger.warning("Duplicate offering type in database.");
					throw new InternalError("Something went wrong.");
				} 
				else if (allStatusType.isEmpty())
					throw new InvalidInput("offering type not found please check offering type.");
				else
					offering.setOfferingType(allStatusType.get(0));
				
				if (offering.getCTC() <= 0L)
					throw new InvalidInput("Please enter valid CTC.");

				if (offering.getCeilingPrice() <= 0L) 
					throw new InvalidInput("Please enter valid offering price.");

				if (offering.getFloorPrice() <= 0L) 
					throw new InvalidInput("Please enter valid floor price.");

				if (offering.getFloorPrice() > offering.getCeilingPrice()) 
					throw new InvalidInput("Floor price can not be greater than ceiling price.");

				if (offering.getCurrency() == null || offering.getCurrency().isEmpty())
					throw new NullDataException("Please enter Currency type.");

				if (offering.getValidTillDate() == null) // date greater than current time is remaining to add condition
					throw new NullDataException("Please enter valid date.");

				logger.warning("Offering added sucessfully.");
				return new ResponseEntity<>("Offering added your offering id is : " + offeringRepository.save(offering).getOfferingId(), HttpStatus.OK);
			} 
			else
				throw new NullDataException("Data can't be empty please check it.");
		} catch (Exception e) {
			logger.warning("An exception occurred while creating the offering.");
			throw e; // Re-throw the exception
		}
	}

	// Get an offering details with the corresponding offering id.
	@Override
	public OfferingDTO GetOfferingByOfferingID(String offeringId) {
		logger.info("Getting offering by offering ID...");

		try {
			if (offeringId == null || offeringId.isEmpty()) 
				throw new NullDataException("Please give offer id to get offer.");
			
			Offering offering = offeringRepository.findById(offeringId).orElseThrow(() -> new DataNotFoundException("No offer available with offer Id : " + offeringId));
				OfferingDTO offeringDTO = new OfferingDTO();
				offeringDTO.setOfferingId(offering.getOfferingId());
				offeringDTO.setOfferingCategory(offering.getOfferingCategory().getStatusValue());
				offeringDTO.setOfferingName(offering.getOfferingName());
				offeringDTO.setCTC(offering.getCTC());
				offeringDTO.setCeilingPrice(offering.getCeilingPrice());
				offeringDTO.setFloorPrice(offering.getFloorPrice());
				offeringDTO.setCurrency(offering.getCurrency());
				offeringDTO.setOfferingType(offering.getOfferingType().getStatusValue());
				offeringDTO.setValidTillDate(offering.getValidTillDate());
				logger.info("Offering retrieved successfully.");
				return offeringDTO;
		} catch (Exception e) {
			logger.severe("An exception occurred while getting an offering.");
			throw e; // Re-throw the exception
		}

	}

	// Get all offerings.
	@Override
	public List<OfferingDTO> GetAllOffering() {
		logger.info("Getting all offerings...");
		try {
			List<Offering> findAll = offeringRepository.findAll();
			if (findAll.isEmpty())
				throw new DataNotFoundException("No offers available");
			else {
				List<OfferingDTO> offeringDTOs = findAll.stream().map(offering -> {
					OfferingDTO offeringDTO = new OfferingDTO();
					offeringDTO.setOfferingId(offering.getOfferingId());
					offeringDTO.setOfferingCategory(offering.getOfferingCategory().getStatusValue());
					offeringDTO.setOfferingName(offering.getOfferingName());
					offeringDTO.setCTC(offering.getCTC());
					offeringDTO.setCeilingPrice(offering.getCeilingPrice());
					offeringDTO.setFloorPrice(offering.getFloorPrice());
					offeringDTO.setCurrency(offering.getCurrency());
					offeringDTO.setOfferingType(offering.getOfferingType().getStatusValue());
					offeringDTO.setValidTillDate(offering.getValidTillDate());
					return offeringDTO;
				}).collect(Collectors.toList());
				logger.info("All offerings retrieved successfully.");
				return offeringDTOs;
			}
		} catch (Exception e) {
			logger.severe("An exception occurred while getting All offerings.");
			throw e; // Re-throw the exception
		}
	}

	// Get all offering in given price range
	@Override
	public List<OfferingDTO> GetAllOfferingByPriceRange(long floorPrice, long ceilingPrice) {
		try {
			if (floorPrice < 0L)
				throw new InvalidInput("please enter valid flooring price");

			if (ceilingPrice < 0L)
				throw new InvalidInput("please enter valid ceiling price");
			
			if (floorPrice > ceilingPrice)
				throw new InvalidInput("Floor price can not grater than ceiling price");

			List<Offering> findAll = offeringRepository.findAll().stream()
					.filter(e -> e.getFloorPrice() >= floorPrice && e.getCeilingPrice() <= ceilingPrice).toList();

			if (findAll.isEmpty()) 
				throw new DataNotFoundException("No offers available");
			else {
				List<OfferingDTO> offeringDTOs = findAll.stream().map(offering -> {
					OfferingDTO offeringDTO = new OfferingDTO();
					offeringDTO.setOfferingId(offering.getOfferingId());
					offeringDTO.setOfferingCategory(offering.getOfferingCategory().getStatusValue());
					offeringDTO.setOfferingName(offering.getOfferingName());
					offeringDTO.setCTC(offering.getCTC());
					offeringDTO.setCeilingPrice(offering.getCeilingPrice());
					offeringDTO.setFloorPrice(offering.getFloorPrice());
					offeringDTO.setCurrency(offering.getCurrency());
					offeringDTO.setOfferingType(offering.getOfferingType().getStatusValue());
					offeringDTO.setValidTillDate(offering.getValidTillDate());
					return offeringDTO;
				}).collect(Collectors.toList());
				logger.info("All offerings retrieved successfully.");
				return offeringDTOs;
			}
		} catch (Exception e) {
			logger.severe("exception occurred while getting an offering in given range.");
			throw e; // Re-throw the exception
		}
	}

	// Update the offering details with the corresponding offering id.
	@Override
	public ResponseEntity<String> UpdateOfferingByOfferingId(Offering offering, String offeringId) {
		logger.info("Updating offering with ID: " + offeringId);
		try {
			if (offering.getFloorPrice() > offering.getCeilingPrice()) 
				throw new InvalidInput("Floor price can not greater that ceiling price.");
			
			if (offeringId == null || offeringId.isEmpty()) 
				throw new NullDataException("Please give offer ID to update offer.");
			else 
			{
				if (offering != null && !offering.toString().equals(new Offering().toString())) 
				{
					Offering offerInDatabase = offeringRepository.findById(offeringId).orElseThrow(() -> new DataNotFoundException("No offer available with offer ID: " + offeringId));

					if (offering.getOfferingCategory() != null && offering.getOfferingCategory().getStatusValue() != null && !offering.getOfferingCategory().getStatusValue().isEmpty())
					{
						List<Status> allStatusCategory = statusRepository.findAll().stream()
								.filter(e -> e.getTableName().equalsIgnoreCase("offering") && e.getStatusValue().equalsIgnoreCase(offering.getOfferingCategory().getStatusValue()))
								.collect(Collectors.toList());
						if (allStatusCategory.size() > 1) 
						{
							logger.warning("Duplicate offering category in database.");
							throw new InternalError("Something went wrong.");
						} 
						else if (allStatusCategory.isEmpty())
							throw new InvalidInput("Offering category not found, please check offering category.");
						else
							offerInDatabase.setOfferingCategory(allStatusCategory.get(0));
					}

					if (offering.getOfferingType() != null && offering.getOfferingType().getStatusValue() != null && !offering.getOfferingType().getStatusValue().isEmpty())
					{
						List<Status> allStatusType = statusRepository.findAll().stream()
								.filter(e -> e.getTableName().equalsIgnoreCase("offering") && e.getStatusValue().equals(offering.getOfferingType().getStatusValue()))
								.collect(Collectors.toList());
						if (allStatusType.size() > 1)
						{
							logger.warning("Duplicate offering type in database.");
							throw new InternalError("Something went wrong.");
						} 
						else if (allStatusType.isEmpty())
							throw new InvalidInput("Offering type not found, please check offering type.");
						else
							offerInDatabase.setOfferingType(allStatusType.get(0));
					}

					if (offering.getCTC() > 0L)
						offerInDatabase.setCTC(offering.getCTC());

					if (offering.getCeilingPrice() > 0L)
						offerInDatabase.setCeilingPrice(offering.getCeilingPrice());

					if (offering.getFloorPrice() > 0L)
						offerInDatabase.setFloorPrice(offering.getFloorPrice());
					
					if (offering.getOfferingName() != null && !offering.getOfferingName().isEmpty())
						offerInDatabase.setOfferingName(offering.getOfferingName());

					if (offering.getCurrency() != null && !offering.getCurrency().isEmpty())
						offerInDatabase.setCurrency(offering.getCurrency());

					if (offering.getValidTillDate() != null)
						offerInDatabase.setValidTillDate(offering.getValidTillDate());

					offeringRepository.save(offerInDatabase);
					logger.info("Offering updated successfully.");
					return new ResponseEntity<>("Offer updated.", HttpStatus.OK);
				} 
				else
					throw new NullDataException("Data can't be empty, please check it.");
			}
		} catch (Exception e) {
			logger.severe("An exception occurred while updating offering");
			throw e; // Re-throw the exception
		}
	}

	// Delete the offering with the corresponding provided offering id.
	@Override
	public ResponseEntity<String> DeleteOfferingByOfferingId(String offeringId) {
		logger.info("Deleting offering with ID: " + offeringId);
		try {
			if (offeringId == null || offeringId.isEmpty())
				throw new NullDataException("Please give offer ID to delete offer.");
			else {
				Offering offeringPresent = offeringRepository.findById(offeringId)
						.orElseThrow(() -> new DataNotFoundException("Offer not present with offer ID: " + offeringId));
				if (offeringPresent != null) {
					offeringRepository.deleteById(offeringId);
					logger.info("Offer deleted successfully.");
					return new ResponseEntity<>("Offer deleted.", HttpStatus.OK);
				} else 
					throw new DataNotFoundException("Offer not present with offer ID: " + offeringId);
			}
		} catch (Exception e) {
			logger.severe("An exception occurred while deleting offering.");
			throw e; // Re-throw the exception
		}
	}
}
