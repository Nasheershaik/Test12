/**
 * @author Mjilani
 * @date 07-07-23
 * @fileName : OpportunitySubServiceImpl
 * Service class for handling business logic related to User entities.
 * Provides methods to interact with the User repository and perform operations on User objects.
 * this class will implement service interface to override abstract methods of that interface 
 */
package com.kloc.crm.Service.ServiceImpl;
import java.time.LocalDate;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;
import com.kloc.crm.Entity.Opportunity;
import com.kloc.crm.Entity.OpportunitySub;
import com.kloc.crm.Entity.Status;
import com.kloc.crm.Exception.DataNotFoundException;
import com.kloc.crm.Exception.NullDataException;
import com.kloc.crm.Repository.OpportunityRepository;
import com.kloc.crm.Repository.OpportunitySubRepository;
import com.kloc.crm.Repository.StatusRepo;
import com.kloc.crm.Service.OpportunitySubServcie;
@Service
public class OpportunitySubServiceImpl implements OpportunitySubServcie
{
	@Autowired
	private OpportunitySubRepository opportunitySubRepository;
	@Autowired
	private OpportunityRepository opportunityRepository;
	  @Autowired
	  private StatusRepo statusRepository;
	
	/**
	 * @param opportunitySubRepository
	 */
	/**Constructor injection**/
	public OpportunitySubServiceImpl(OpportunitySubRepository opportunitySubRepository,OpportunityRepository opportunityRepository
			       ,StatusRepo statusRepository)
	{
		super();
		this.opportunitySubRepository = opportunitySubRepository;
		this.opportunityRepository =opportunityRepository;
		this.statusRepository=statusRepository;
	}
	 /**
     * Saves the given Opportunity entity.
     * @return The saved  Opportunity entity.
     */
	@Override
	public OpportunitySub saveOpportunity(OpportunitySub opportunitySub,String opportunityId) 
	{
		if(opportunityId==null || opportunityId.equals(""))
		{
			throw new NullDataException("there is no data in opportunity Id");
		}
	     Opportunity opportunity= opportunityRepository.findById(opportunityId).orElseThrow(()->new DataNotFoundException("opportunity", "opportunityId", opportunityId));
	     
		if(opportunitySub.getStatus().getStatusValue()==null ||opportunitySub.getStatus().getStatusValue().equals(""))
		{
			throw new NullDataException("write status of opportunity");
		}
		
		Status status=statusRepository.findByStatusTypeAndStatusValue("opportunity/deal",opportunitySub.getStatus().getStatusValue());

		opportunitySub.setStatus(status);
		opportunitySub.setOpportunityId(opportunity);
		opportunitySub.setOpportunityCreatedDate(LocalDate.now());
		return opportunitySubRepository.save(opportunitySub);
	}
		
//		if(opportunityId==null || opportunityId.equals(" "))
//			 throw new NullDataException("opportunity id should not be null");
//		else
//		{
//			if (opportunitySub == null || opportunitySub.toString().equals(new OpportunitySub().toString())) 
//				throw new DataNotFoundException("Data can't be empty please enter data.");
//			else
//			{
//				if(statusType==null || statusType.equals(""))
//				{
//					throw new NullDataException("Status_id should not b empty please enter status_id");
//				}
//			}
//		
//		    opportunitySub.setStatus(statusRepository.findById(status_id).orElseThrow(()-> new DataNotFoundException("Status","Status_id",status_id)));
////		        
//		    if(statusRepository.findById(status_id).stream().filter(e->e.getStatusType().toLowerCase().equals("Deal"))
//		    {
//		     opportunitySub.setOpportunityId(opportunityRepository.findById(opportunityId).orElseThrow(()->new DataNotFoundException("opportunity", "opportunityId", opportunityId)));
//		     return opportunitySubRepository.save(opportunitySub);
//			}

	 /**
      * Retrieves all opportunities
      */
	@Override
	public List<OpportunitySub> getAllOppartunities() 
	{
		List<OpportunitySub> findAll = opportunitySubRepository.findAll();
		if (findAll.isEmpty()) 
			throw new DataNotFoundException("no opportunity available.");
		else
			return findAll;
	}
	
	 /**
      * Retrieves a opportunity entity by the provided id.
      *
      * @return The Opportunity entity associated with the provided id, or exception if not found.
      */
	@Override
	public OpportunitySub getOpportunitybyId(String id)
	{
		if(id==null || id.equals(" "))
			throw new NullDataException("id should not be enpty please enter valid id");
		else
			return opportunitySubRepository.findById(id).orElseThrow(()->new DataNotFoundException("Opportunity","id",id));
	}
	
	/**get all opportunities by date**/
	@Override
	public List<OpportunitySub> getOpportunityByDate(LocalDate date) 
	{
		if(date.toString()==null || date.toString().equals(""))
			throw  new NullDataException("Date should not be empty plese eneter valid date in the LocalDate Format");
		else
			return opportunitySubRepository.findAll().stream().filter(e->e.getOpportunityCreatedDate().isEqual(date)).toList();
	}
	
	 /**
      * Update  a Opportunity entity by the provided id.
      *
      * @return The Opportunity entity associated with the provided id, or RuntimeException if not found.
      */
	@Override
	public OpportunitySub updateOpportunitySub(OpportunitySub opportunitySubInput, String id,String opportunity_id)
	{
		if(id==null || id.equals("")||opportunity_id==null ||opportunity_id.equals(" "))
			throw new NullDataException("id and opportunity id should not be null please enter valid details");
		//opportunitySubRepository.findById(id).orElseThrow(()->new DataNotFoundException("Opportunity Sub", "OpportunitySub_id", id));
		opportunityRepository.findById(opportunity_id).orElseThrow(()->new DataNotFoundException("Opportunity", "Opportunity_id", opportunity_id));
		
			OpportunitySub opportunitySubFromDatabase = opportunitySubRepository.findById(id).orElseThrow(()->new DataNotFoundException("OpportunitySub","Id",id));
	       	if (opportunitySubInput.getOpportunityCreatedDate() != null)
	       	{
	       		opportunitySubFromDatabase.setOpportunityCreatedDate(opportunitySubInput.getOpportunityCreatedDate());
	       	}
	       	
	       	System.out.println(opportunitySubInput.getCurrency()+"================================");
	       	
	       	if(!opportunitySubInput.getCurrency().equals(null) || !opportunitySubInput.getCurrency().toString().equals(""))
			{
	       		opportunitySubFromDatabase.setCurrency(opportunitySubInput.getCurrency());
			}else {opportunitySubFromDatabase.setCurrency("");}
	       	
			if(opportunitySubInput.getDuration().equals(null))
			{
				opportunitySubFromDatabase .setDuration(opportunitySubInput.getDuration());
			}
			if(opportunitySubInput.getNoOfInstallements() > 0 )
			{
				opportunitySubFromDatabase.setNoOfInstallements(opportunitySubInput.getNoOfInstallements());
			}		
//			if(!opportunitySubInput.getOpportunityType().equals(null) || !opportunitySubInput.getOpportunityType().equals(""))
//			{
//				opportunitySubFromDatabase.setOpportunityType(opportunitySubInput.getOpportunityType());
//			}	
			if(opportunitySubInput.getPrice() > 0)
			{
				opportunitySubFromDatabase.setPrice(opportunitySubInput.getPrice());
			}
			Opportunity opportunityFromDatabase= opportunityRepository.findById(opportunity_id).orElseThrow(()-> new DataNotFoundException("opoortunity","opportunity_id",opportunity_id));
			opportunitySubFromDatabase.setOpportunityId(opportunityFromDatabase);
       
	       opportunitySubRepository.save(opportunitySubFromDatabase);
	       return opportunitySubFromDatabase;
		
	}
	/**update based on opportunity  id**/
	@Override
	public OpportunitySub updateOpportunitySubByOpportunityId(String opportunitySubId, String opportunityId) 
	{
		if(opportunitySubId==null ||opportunitySubId.equals("")||opportunityId==null ||opportunityId.equals(""))
		{
			throw new NullDataException("OpportuniotySub and Oppportunityid hould not empty please enter valid id");
		}
		else
		{
		Opportunity opportunity=opportunityRepository.findById(opportunityId).orElseThrow(()->new DataNotFoundException("Opportunity","Id",opportunityId));
		OpportunitySub opportunitySub=opportunitySubRepository.findById(opportunitySubId).orElseThrow(()->new DataNotFoundException("OpportunitySub","Id",opportunitySubId));
		opportunitySub.setOpportunityId(opportunity);
		
		/** to save in opportunitySubRepo **/
		opportunitySubRepository.save(opportunitySub);
		return opportunitySub;
		}
	}
//	=============================================================================trail and error implementation==========================

//	@Override
//	public OpportunitySub updateOpportunitySubWithOpportunityIdAndOpprtunitySub(OpportunitySub opportunitySubId,String id, String oppportunityId)
//	{
//		Opportunity opportunity = opportunityRepository.findById(oppportunityId).orElseThrow(()-> new DataNotFoundException("opportunity","opportunityId",oppportunityId));
//		OpportunitySub opportunitySub=opportunitySubRepository.findById(id).orElseThrow(()-> new DataNotFoundException("OpportunitySub","opportunitySubId",opportunitySubId));
//		opportunitySub.setOpportunityId(opportunity);
//		opportunitySub.setCurrency(opportunitySubId.getCurrency());
//		/**to save This**/
//		opportunitySubRepository.save(opportunitySub);
//		return opportunitySub;
//	}

//	    
//	    // Check if the OpportunitySub with the given opportunityId exists
//	    OpportunitySub existingOpportunitySub = opportunitySubRepository.findById(opportunityId)
//	            .orElseThrow(() -> new DataNotFoundException("opportunitySub", "opportunityId", opportunityId));
//	    
//	    // Update the status if provided in the updatedOpportunitySub
//	    if (opportunitySub.getStatus() != null && opportunitySub.getStatus().getStatusValue() != null
//	            && !opportunitySub.getStatus().getStatusValue().equals("")) {
//	        Status status = statusRepository.findByStatusTypeAndStatusValue("opportunity/deal",
//	                opportunitySub.getStatus().getStatusValue());
//	        existingOpportunitySub.setStatus(status);
//	    }
//	    
//	    // You can add more fields to update here if needed
//	    
//	    // Save the changes
//	    return opportunitySubRepository.save(existingOpportunitySub);
//	}

	 /**
     * Update  a Opportunity entity by the provided id.
     *
     * @return The Opportunity entity associated with the provided id, or RuntimeException if not found.
     */
	@Override
	public OpportunitySub updateOpportunitySubEntity(OpportunitySub opportunitySub, String opportunitySubId,
			String opportunityId)
	  {
		  if (opportunityId == null || opportunityId.equals("")) {
		        throw new NullDataException("there is no data in opportunity Id");
		    }
		   if(opportunitySubId==null || opportunitySubId.equals(""))
		   {
			   throw new NullDataException("there is no data with respective opportunitySub Id");
		   }
		  
		    
		    // Check if the OpportunitySub with the given opportunityId exists
		    OpportunitySub existingOpportunitySub = opportunitySubRepository.findById(opportunitySubId)
		            .orElseThrow(() -> new DataNotFoundException("opportunitySub", "opportunityId", opportunityId));
		    
		    
		    // Update the status if provided in the updatedOpportunitySub
		    if (opportunitySub.getStatus() != null && opportunitySub.getStatus().getStatusValue() != null
		            && !opportunitySub.getStatus().getStatusValue().equals("")) {
		        Status status = statusRepository.findByStatusTypeAndStatusValue("opportunity/deal",
		                opportunitySub.getStatus().getStatusValue());
		        existingOpportunitySub.setStatus(status);
		    
		        if(opportunitySub.getCurrency()!=null && !opportunitySub.getCurrency().isEmpty())
		        {
		           existingOpportunitySub.setCurrency(opportunitySub.getCurrency());
		        }
		        
		        if(opportunitySub.getDuration()!=null )
		        {
		        existingOpportunitySub.setDuration(opportunitySub.getDuration());
		        }
		       
		        if(opportunitySub.getNoOfInstallements() > 0 )
				{
		        	existingOpportunitySub.setNoOfInstallements(opportunitySub.getNoOfInstallements());
				}	
		        
		     	if (opportunitySub.getOpportunityCreatedDate() != null)
		       	{
		     		existingOpportunitySub.setOpportunityCreatedDate(opportunitySub.getOpportunityCreatedDate());
		       	}
		     	
		    	if(opportunitySub.getPrice() > 0)
				{
		    		existingOpportunitySub.setPrice(opportunitySub.getPrice());
				}
		        
		    Opportunity opportunityFromDatabase= opportunityRepository.findById(opportunityId).orElseThrow(()-> new DataNotFoundException("opoortunity","opportunity_id",opportunityId));
		   
		    existingOpportunitySub.setOpportunityId(opportunityFromDatabase);
		    }
		    // Save the changes
		    return opportunitySubRepository.save(existingOpportunitySub);
	}
	



	
	
	
	
//	======================================================================================================================================

}
