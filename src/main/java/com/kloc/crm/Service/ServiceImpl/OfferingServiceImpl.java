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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hibernate.boot.model.naming.IllegalIdentifierException;
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

import jakarta.persistence.EntityExistsException;

@Service
public class OfferingServiceImpl implements OfferingService
{
	// offering repository to perform database operation on the offering table
	@Autowired
	OfferingRepository offeringRepository;
	
	// status repository to get status from the status table
	@Autowired
	StatusRepo statusRepository;
	
	// Create a new offering with the all data and create a offering id.
    @Override
    public ResponseEntity<String> CreateOffering(Offering offering)
    {
    	
    	if(offering != null && !offering.toString().equals(new Offering().toString()))
    	{
    		if(offering.getOfferingName() == null || offering.getOfferingName().equals(""))
    			throw new NullDataException("Please enter offer name.");
    		
//    		List<Offering> listWithOfferingName = offeringRepository.findAll().stream().filter(e -> e.getOfferingName().equals(offering.getOfferingName())).toList();
//    		if (!listWithOfferingName.isEmpty())
//    		{
//    			Offering offeringCategory = listWithOfferingName.stream().filter(e -> ( e.getOfferingCategory().getStatusType().toLowerCase().equals("offering category")) && (e.getOfferingCategory().getStatusValue().toLowerCase().equals(offering.getOfferingCategory().getStatusValue().toLowerCase()))).findFirst().get();
//    			Offering offeringType = listWithOfferingName.stream().filter(e -> ( e.getOfferingType().getStatusType().toLowerCase().equals("offering type")) && (e.getOfferingType().getStatusValue().toLowerCase().equals(offering.getOfferingType().getStatusValue().toLowerCase()))).findFirst().get();
//    			if (offeringCategory != null && offeringType !=null)
//    			{
//					throw new EntityExistsException("This Offer Already Exist.");
//				}
//			}
    		if(offering.getOfferingCategory() == null || offering.getOfferingCategory().getStatusValue() == null || offering.getOfferingCategory().getStatusValue().equals(""))
    			throw new NullDataException("Please enter offer category.");
    		if(offering.getOfferingType() == null || offering.getOfferingType().getStatusValue() == null || offering.getOfferingType().getStatusValue().equals(""))
    			throw new NullDataException("Please enter offer type.");
    		List<Status> allStatusCategory = statusRepository.findAll().stream().filter(e -> e.getStatusValue().equalsIgnoreCase(offering.getOfferingCategory().getStatusValue().toLowerCase())).collect(Collectors.toList());
    		if (allStatusCategory.size()>1)
    			throw new InternalError("Something went wrong.");
    		else if (allStatusCategory.isEmpty())
    			throw new InvalidInput("offering category not found please check offering category.");
    		else
    			offering.setOfferingCategory(allStatusCategory.get(0));
    		List<Status> allStatusType = statusRepository.findAll().stream().filter(e -> e.getStatusValue().equalsIgnoreCase(offering.getOfferingType().getStatusValue().toLowerCase())).collect(Collectors.toList());
    		if (allStatusType.size()>1)
    			throw new InternalError("Something went wrong.");
    		else if (allStatusType.isEmpty())
    			throw new InvalidInput("offering type not found please check offering type.");
    		else
    			offering.setOfferingType(allStatusType.get(0));
    		if(offering.getCTC() <= 0L)
    			throw new InvalidInput("Please enter valid CTC.");
    		if(offering.getProjectCost() <= 0L)
    			throw new InvalidInput("Please enter valid project cost.");
    		if(offering.getActualCost() <= 0L)
    			throw new InvalidInput("Please enter valid actual cost.");
    		if(offering.getCostType() == null || offering.getCostType().equals(""))
    			throw new NullDataException("Please enter cost type.");
    		if(offering.getValidTillDate() == null || offering.getValidTillDate().equals("")) // date greater than current time is remaining to add condition
    			throw new NullDataException("Please enter valid date.");
    		return new ResponseEntity<>("Offering added your offering id is : "+offeringRepository.save(offering).getOfferingId(),HttpStatus.OK);
    	}
    	else
    		throw new NullDataException("Data can't be empty please check it.");
    }

    // Get an offering details with the corresponding offering id if it is present in the database.
    @Override
    public Offering GetOfferingByOfferingID(String offeringId) 
    {
    	if(offeringId == null || offeringId.equals(""))
    		throw new NullDataException("Please give offer id to get offer.");
    	else
    		return offeringRepository.findById(offeringId).orElseThrow(() -> new DataNotFoundException("No offer available with offer Id : " + offeringId));
    }

    // Get all offerings present in the database.
    @Override
    public List<Offering> GetAllOffering()
    {
    	List<Offering> findAll = offeringRepository.findAll();
    	if(findAll.isEmpty())
    		throw new DataNotFoundException("No offers available");
    	else
    		return findAll;
        			 
    }
    
	// Update the offer details with the corresponding offering id and provided data.
	@Override
	public ResponseEntity<String> updateOfferingByOfferingId(Offering offering, String offeringId)
	{
		if(offeringId == null || offeringId.equals(""))
    		throw new NullDataException("Please give offer id to get offer.");
		else 
		{
			if(offering != null && !offering.toString().equals(new Offering().toString()))
	    	{
				Offering offerInDatabase = offeringRepository.findById(offeringId).orElseThrow(()->new DataNotFoundException("No offer available with offer Id : "+offeringId));
				
				if(offering.getOfferingCategory() != null && offering.getOfferingCategory().getStatusValue() != null && !offering.getOfferingCategory().getStatusValue().equals(""))
				{
					List<Status> allStatusCategory = statusRepository.findAll().stream().filter(e -> e.getStatusValue().equalsIgnoreCase(offering.getOfferingCategory().getStatusValue())).collect(Collectors.toList());
		    		if (allStatusCategory.size()>1)
		    			throw new InternalError("Something went wrong.");
		    		else if (allStatusCategory.isEmpty())
		    			throw new InvalidInput("offering category not found please check offering category.");
		    		else
		    			offerInDatabase.setOfferingCategory(allStatusCategory.get(0));
				}
				if(offering.getOfferingType() != null && offering.getOfferingType().getStatusValue() != null && !offering.getOfferingType().getStatusValue().equals(""))
				{
					List<Status> allStatusType = statusRepository.findAll().stream().filter(e -> e.getStatusValue().equals(offering.getOfferingType().getStatusValue())).collect(Collectors.toList());
		    		if (allStatusType.size()>1)
		    			throw new InternalError("Something went wrong.");
		    		else if (allStatusType.isEmpty())
		    			throw new InvalidInput("offering type not found please check offering type.");
		    		else
		    			offerInDatabase.setOfferingType(allStatusType.get(0));
				}
				if(offering.getCTC() > 0L)
					offerInDatabase.setCTC(offering.getCTC());
				if(offering.getProjectCost() > 0L)
					offerInDatabase.setProjectCost(offering.getProjectCost());
				if(offering.getActualCost() > 0L)
					offerInDatabase.setActualCost(offering.getActualCost());
				if(offering.getOfferingName() != null && !offering.getOfferingName().equals(""))
					offerInDatabase.setOfferingName(offering.getOfferingName());
				if(offering.getCostType() != null && !offering.getCostType().equals(""))
					offerInDatabase.setCostType(offering.getCostType());
				if(offering.getValidTillDate() != null && !offering.getValidTillDate().equals("")) // date greater than current time is remaining to add condition
					offerInDatabase.setValidTillDate(offering.getValidTillDate());
				offeringRepository.save(offerInDatabase);
				return new ResponseEntity<>("Offer updated.",HttpStatus.OK);
	    	}
			else
				throw new NullDataException("Data can't be empty please check it.");
		}
	}

	// Delete the offer with the corresponding provided offering id.
	@Override
	public ResponseEntity<String> deleteOfferingByOfferingId(String offeringId)
	{
		if(offeringId == null || offeringId.equals(""))
    		throw new NullDataException("Please give offer id to get offer.");
		else 
		{
			Offering offeringPresent = offeringRepository.findById(offeringId).orElseThrow(() -> new DataNotFoundException("Offer not present with offer id : "+offeringId));
			if(offeringPresent != null)
			{
				offeringRepository.deleteById(offeringId);
				return new ResponseEntity<>("Offer deleted.",HttpStatus.OK);
			}
			else
				throw new DataNotFoundException("Offer not present with offer id : "+offeringId);
		}
	}
}
