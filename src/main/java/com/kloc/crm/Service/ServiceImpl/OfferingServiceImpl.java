package com.kloc.crm.Service.ServiceImpl;

/**
 * This class is the implementation of the Offering Service
 * 
 * @author_name  : Ankush
 * @File_name	 : OfferingServiceImpl.java
 * @Created_Date : 5/7/2023
 */

// Here all the imports which is necessary and used in this class
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
public class OfferingServiceImpl implements OfferingService
{
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
    	
    	if(offering != null && !offering.toString().equals(new Offering().toString()))
    	{
    		if(offering.getOfferingName() == null || offering.getOfferingName().equals(""))
    		{
    			 logger.warning("Offering name is missing.");
    			throw new NullDataException("Please enter offer name.");
    		}
    		if(offering.getOfferingCategory() == null || offering.getOfferingCategory().getStatusValue() == null || offering.getOfferingCategory().getStatusValue().equals(""))
    		{
    			 logger.warning("Offering category is missing.");
    			throw new NullDataException("Please enter offer category.");
    		}
    		if(offering.getOfferingType() == null || offering.getOfferingType().getStatusValue() == null || offering.getOfferingType().getStatusValue().equals(""))
    		{
    			logger.warning("Offering type is missing.");    			
    			throw new NullDataException("Please enter offer type.");
    		}
    		
    		List<Status> allStatusCategory = statusRepository.findAll().stream().filter(e -> e.getStatusValue().equalsIgnoreCase(offering.getOfferingCategory().getStatusValue().toLowerCase())).collect(Collectors.toList());
    		if (allStatusCategory.size()>1)
    		{
    			logger.warning("Duplicate offering category in database.");
    			throw new InternalError("Something went wrong.");
    		}
    		else if (allStatusCategory.isEmpty())
    		{
    			logger.warning("Offering category is not available");
    			throw new InvalidInput("offering category not found please check offering category.");
    		}
    		else
    			offering.setOfferingCategory(allStatusCategory.get(0));
    		List<Status> allStatusType = statusRepository.findAll().stream().filter(e -> e.getStatusValue().equalsIgnoreCase(offering.getOfferingType().getStatusValue().toLowerCase())).collect(Collectors.toList());
    		if (allStatusType.size()>1)
    		{
    			logger.warning("Duplicate offering type in database.");
    			throw new InternalError("Something went wrong.");
    		}
    		else if (allStatusType.isEmpty())
    		{
    			logger.warning("Offering type is not available");
    			throw new InvalidInput("offering type not found please check offering type.");
    		}
    		else
    			offering.setOfferingType(allStatusType.get(0));
    		if(offering.getCTC() <= 0L)
    		{
    			logger.warning("Not valid ctc entered.");
    			throw new InvalidInput("Please enter valid CTC.");
    		}
    		if(offering.getProjectCost() <= 0L)
    		{
    			logger.warning("Not valid project cost entered.");
    			throw new InvalidInput("Please enter valid project cost.");
    		}
    		if(offering.getActualCost() <= 0L)
    		{
    			logger.warning("Not valid actual cost entered.");
    			throw new InvalidInput("Please enter valid actual cost.");
    		}
    		if(offering.getCostType() == null || offering.getCostType().equals(""))
    		{
    			logger.warning("Cost type null or empty.");
    			throw new NullDataException("Please enter cost type.");
    		}
    		if(offering.getValidTillDate() == null || offering.getValidTillDate().equals("")) // date greater than current time is remaining to add condition
    		{
    			logger.warning("Not valid date entered.");
    			throw new NullDataException("Please enter valid date.");
    		}
			logger.warning("Offering added sucessfully.");
    		return new ResponseEntity<>("Offering added your offering id is : "+offeringRepository.save(offering).getOfferingId(),HttpStatus.OK);
    	}
    	else
    	{
			logger.warning("Offering object is null.");
    		throw new NullDataException("Data can't be empty please check it.");
    	}
    }

    // Get an offering details with the corresponding offering id if it is present in the database.
    @Override
    public OfferingDTO GetOfferingByOfferingID(String offeringId) 
    {
        logger.info("Getting offering by offering ID...");

        if (offeringId == null || offeringId.equals(""))
        {
            logger.warning("Offering ID is missing.");
            throw new NullDataException("Please give offer id to get offer.");
        } 
        else
        {
            try 
            {
                Offering offering = offeringRepository.findById(offeringId).orElse(null);
                if (offering == null) 
                {
                    logger.warning("No offer available with offer ID: " + offeringId);
                    throw new DataNotFoundException("No offer available with offer Id : " + offeringId);
                } 
                else
                {
                	OfferingDTO offeringDTO = new OfferingDTO();
                	offeringDTO.setOfferingId(offering.getOfferingId());
                	offeringDTO.setOfferingCategory(offering.getOfferingCategory().getStatusValue());
                	offeringDTO.setOfferingName(offering.getOfferingName());
                	offeringDTO.setCTC(offering.getCTC());
                	offeringDTO.setProjectCost(offering.getProjectCost());
                	offeringDTO.setActualCost(offering.getActualCost());
                	offeringDTO.setCostType(offering.getCostType());
                	offeringDTO.setOfferingType(offering.getOfferingType().getStatusValue());
                	offeringDTO.setValidTillDate(offering.getValidTillDate());
                    logger.info("Offering retrieved successfully.");
                    return offeringDTO;
                }
            } 
            catch (Exception e)
            {
                logger.severe("An error occurred while getting an offering: " + e.getMessage());
                throw e; // Rethrow the exception
            }
        }
    }

    // Get all offerings present in the database.
    @Override
    public List<OfferingDTO> GetAllOffering()
    {
        logger.info("Getting all offerings...");

        try 
        {
            List<Offering> findAll = offeringRepository.findAll();
            if (findAll.isEmpty()) 
            {
                logger.warning("No offers available.");
                throw new DataNotFoundException("No offers available");
            } 
            else
            {
            	 List<OfferingDTO> offeringDTOs = findAll.stream()
                         .map(offering -> {
                             OfferingDTO offeringDTO = new OfferingDTO();
                             offeringDTO.setOfferingId(offering.getOfferingId());
                             offeringDTO.setOfferingCategory(offering.getOfferingCategory().getStatusValue());
                             offeringDTO.setOfferingName(offering.getOfferingName());
                             offeringDTO.setCTC(offering.getCTC());
                             offeringDTO.setProjectCost(offering.getProjectCost());
                             offeringDTO.setActualCost(offering.getActualCost());
                             offeringDTO.setCostType(offering.getCostType());
                             offeringDTO.setOfferingType(offering.getOfferingType().getStatusValue());
                             offeringDTO.setValidTillDate(offering.getValidTillDate());
                             return offeringDTO;
                         })
                         .collect(Collectors.toList());
                logger.info("All offerings retrieved successfully.");
                return offeringDTOs;
            }
        } catch (Exception e)
        {
            logger.severe("An error occurred while getting all offerings: " + e.getMessage());
            throw e; // Rethrow the exception
        }
    }

    
	// Update the offer details with the corresponding offering id and provided data.
    public ResponseEntity<String> updateOfferingByOfferingId(Offering offering, String offeringId)
    {
    	System.out.println("-----------------------------------"+offeringId+"---------------------------------------------");
        logger.info("Updating offering with ID: " + offeringId);

        try 
        {
            if (offeringId == null || offeringId.equals("")) 
            {
                logger.warning("Offering ID is missing.");
                throw new NullDataException("Please give offer ID to update offer.");
            } 
            else
            {
                if (offering != null && !offering.toString().equals(new Offering().toString()))
                {
                    Offering offerInDatabase = offeringRepository.findById(offeringId).orElseThrow(() -> new DataNotFoundException("No offer available with offer ID: " + offeringId));

                    if (offering.getOfferingCategory() != null && offering.getOfferingCategory().getStatusValue() != null && !offering.getOfferingCategory().getStatusValue().equals(""))
                    {
                        List<Status> allStatusCategory = statusRepository.findAll().stream().filter(e -> e.getStatusValue().equalsIgnoreCase(offering.getOfferingCategory().getStatusValue())).collect(Collectors.toList());
                        if (allStatusCategory.size() > 1) 
                        {
                            logger.warning("Duplicate offering category in database.");
                            throw new InternalError("Something went wrong.");
                        } else if (allStatusCategory.isEmpty())
                        {
                            logger.warning("Offering category not found in database.");
                            throw new InvalidInput("Offering category not found, please check offering category.");
                        }
                        else
                        {
                            offerInDatabase.setOfferingCategory(allStatusCategory.get(0));
                        }
                    }

                    if (offering.getOfferingType() != null && offering.getOfferingType().getStatusValue() != null && !offering.getOfferingType().getStatusValue().equals(""))
                    {
                        List<Status> allStatusType = statusRepository.findAll().stream().filter(e -> e.getStatusValue().equals(offering.getOfferingType().getStatusValue())).collect(Collectors.toList());
                        if (allStatusType.size() > 1)
                        {
                            logger.warning("Duplicate offering type in database.");
                            throw new InternalError("Something went wrong.");
                        } 
                        else if (allStatusType.isEmpty())
                        {
                            logger.warning("Offering type not found in database.");
                            throw new InvalidInput("Offering type not found, please check offering type.");
                        }
                        else
                        {
                            offerInDatabase.setOfferingType(allStatusType.get(0));
                        }
                    }

                    if (offering.getCTC() > 0L)
                    {
                        offerInDatabase.setCTC(offering.getCTC());
                    }

                    if (offering.getProjectCost() > 0L)
                    {
                        offerInDatabase.setProjectCost(offering.getProjectCost());
                    }

                    if (offering.getActualCost() > 0L)
                    {
                        offerInDatabase.setActualCost(offering.getActualCost());
                    }

                    if (offering.getOfferingName() != null && !offering.getOfferingName().equals("")) 
                    {
                        offerInDatabase.setOfferingName(offering.getOfferingName());
                    }

                    if (offering.getCostType() != null && !offering.getCostType().equals(""))
                    {
                        offerInDatabase.setCostType(offering.getCostType());
                    }

                    if (offering.getValidTillDate() != null && !offering.getValidTillDate().equals(""))
                    {
                        offerInDatabase.setValidTillDate(offering.getValidTillDate());
                    }

                    offeringRepository.save(offerInDatabase);
                    logger.info("Offering updated successfully.");
                    return new ResponseEntity<>("Offer updated.", HttpStatus.OK);
                } 
                else
                {
                    logger.warning("Offering object is null or empty.");
                    throw new NullDataException("Data can't be empty, please check it.");
                }
            }
        } 
        catch (Exception e) 
        {
            logger.severe("An error occurred while updating offering: " + e.getMessage());
            throw e; // Rethrow the exception
        }
    }


	// Delete the offer with the corresponding provided offering id.
    public ResponseEntity<String> deleteOfferingByOfferingId(String offeringId)
    {
        logger.info("Deleting offering with ID: " + offeringId);

        try 
        {
            if (offeringId == null || offeringId.equals("")) 
            {
                logger.warning("Offering ID is missing.");
                throw new NullDataException("Please give offer ID to delete offer.");
            } 
            else 
            {
                Offering offeringPresent = offeringRepository.findById(offeringId).orElseThrow(() -> new DataNotFoundException("Offer not present with offer ID: " + offeringId));
                if (offeringPresent != null) 
                {
                    offeringRepository.deleteById(offeringId);
                    logger.info("Offer deleted successfully.");
                    return new ResponseEntity<>("Offer deleted.", HttpStatus.OK);
                } 
                else
                {
                    logger.warning("Offer not found with ID: " + offeringId);
                    throw new DataNotFoundException("Offer not present with offer ID: " + offeringId);
                }
            }
        } 
        catch (Exception e)
        {
            logger.severe("An error occurred while deleting offering: " + e.getMessage());
            throw e; // Rethrow the exception
        }
    }

}
