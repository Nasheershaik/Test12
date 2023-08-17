/**
 * @author Mjilani
 * @date 06-07-23
 * @filename : OpportunityServiceImpl
 * Service class for handling business logic related to User entities.
 * Provides methods to interact with the User repository and perform operations on User objects.
 * this class will implement service interface to override abstract methods of that interface 
 */
package com.kloc.crm.Service.ServiceImpl;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kloc.crm.Entity.Contact;
import com.kloc.crm.Entity.Customer;
import com.kloc.crm.Entity.Offering;
import com.kloc.crm.Entity.Opportunity;
import com.kloc.crm.Entity.OpportunitySub;
import com.kloc.crm.Exception.DataNotFoundException;
import com.kloc.crm.Exception.InvalidInput;
import com.kloc.crm.Exception.NullDataException;
import com.kloc.crm.Repository.ContactRepository;
import com.kloc.crm.Repository.CustomerRepository;
import com.kloc.crm.Repository.OfferingRepository;
import com.kloc.crm.Repository.OpportunityRepository;
import com.kloc.crm.Repository.OpportunitySubRepository;
import com.kloc.crm.Service.OpportunityService;
@Service
@SuppressWarnings("unused")
public class OpportunityServiceImpl implements OpportunityService
{
	@Autowired
	private OpportunityRepository opportunityRepo;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	private OfferingRepository offeringRepository;
	@Autowired
	private OpportunitySubRepository opportunitySubRepository;
 
	/**Constructor injection */
	public OpportunityServiceImpl(OpportunityRepository opportunity,CustomerRepository customerRepository,ContactRepository contactRepository,OfferingRepository offeringRepository)
	{
		super();
		this.opportunityRepo=opportunity;
		this.customerRepository=customerRepository;
		this.contactRepository=contactRepository;
		this.offeringRepository=offeringRepository;
	}
	 /**
     * Saves the given Opportunity entity.
     * @return The saved  Opportunity entity.
     */
	@Override
	public Opportunity saveOppartunity(Opportunity oppartunity,String contact_Id)
	{
		Contact contact=contactRepository.findById(contact_Id).orElseThrow(()->new DataNotFoundException("contact","contactId", contact_Id));
		oppartunity.setContact(contact);
		return opportunityRepo.save(oppartunity);
	}
	@Override
	public Opportunity saveOpportunities(Opportunity opportunity, String contact_Id, String offering_id)
	{
//		List<Offering> offer = opportunity.getO;
////		List<Offering> offer=opportunityRepo.findAll().stream().filter(e->e.getOffering().equals(offering_id)).toList();
////		List<Offering> offer=offeringRepository.findById(offering_id).orElseThrow(()-> new DataNotFoundException("offering","offering_id",offering_id));
//		List<Offering> offer=offeringRepository.findAll().stream().filter(e->e.equals(offering_id)).toList();
//		opportunity.setOffering(offer);
//		Contact contact=contactRepository.findById(contact_Id).orElseThrow(()->new DataNotFoundException("contact","contactId", contact_Id));
//		opportunity.setContact_Id(contact);
//		return opportunityRepo.save(opportunity);
		
		if(contact_Id == null || contact_Id.equals(""))
		{
			throw new InvalidInput("Contact_id cannot be empty for opportunity");
		}
		else if(offering_id==null || offering_id.equals(""))
		{
			throw new InvalidInput("Offering id  cannot be null");
			
		}
		else
		{
		Offering offer = offeringRepository.findById(offering_id).orElseThrow(()-> new DataNotFoundException("offering","offering_id", offering_id));
		opportunity.setOffering(offer);
		Contact contacts= contactRepository.findById(contact_Id).orElseThrow(()-> new DataNotFoundException("contact","contact_id", offer));
		opportunity.setContact(contacts);
		
		List<OpportunitySub> collect = opportunitySubRepository.findAll().stream().filter(e->e.getOpportunityId().getOpportunityName().equals(opportunity.getOpportunityName())).collect(Collectors.toList());
		 System.out.println(collect);
//		if(collect.isEmpty())
//			// save
//		else
//		{
//			 collect = collect.stream().filter(e->e.getOpportunityType().equals("deal")).collect(Collectors.toList());
//			 if(!collect.isEmpty())
//				 id return
//						 //save
//				 else
	
		return opportunityRepo.save(opportunity);
		}
	}
	 /**
     * Retrieves all opportunities
     */
	@Override
	public List<Opportunity> getAllOppartunities() 
	{
		return opportunityRepo.findAll();
	}
	
	 /**
     * Retrieves a opportunity entity by the provided id.
     * @return The Opportunity entity associated with the provided id, or exception if not found.
     */
	@Override
	public Opportunity getOpportunitybyId(String id) 
	{
		if(id==null || id.equals(" "))
		{
			throw new NullDataException("opportunity id cannot be null");
		}
		else
		{
		Optional<Opportunity> opportunity= opportunityRepo.findById(id);
		if(opportunity.isPresent())
			return opportunity.get();
		else
			throw new DataNotFoundException("Opportunity","id",id);
		}
	}
	 /**
     * Retrieves a opportunity entity by the provided contact id.
     * @return The Opportunity entity associated with the provided id, or exception if not found.
     */
	@Override
	public List<Opportunity> getOpportunityByContactId(String contactId) 
	{
		if(contactId==null || contactId=="")
		{
			throw new NullDataException("contact_id should not be null");
		}
		else
		{
			return opportunityRepo.findAll().stream().filter(e->e.getContact().getContactId().toLowerCase().equals(contactId.toLowerCase())).toList();
		}
	}
	 /**
     * Update  a Opportunity entity by the provided id.
     * @return The Opportunity entity associated with the provided id, or RuntimeException if not found.
     */
	@Override
	public Opportunity updateOpportunity(Opportunity oppartunity, String id,String contact_id,String offering_id)
	{
		if(id==null ||id.equals(" ")||contact_id==null || contact_id.equals(" ")||offering_id==null ||offering_id.equals(" "))
		{
			throw new NullDataException("id should not be null or empty please enter valid id");
		}
		else {
		Offering offer =offeringRepository.findById(offering_id).orElseThrow(()-> new DataNotFoundException("offering","offering_id",offering_id));
		Contact contact = contactRepository.findById(contact_id).orElseThrow(()-> new DataNotFoundException("contact","contact_id",contact_id));
		Opportunity opportunity=opportunityRepo.findById(id).orElseThrow(()->new DataNotFoundException("Opportunity","id",id));
		
		
		if(oppartunity.getOpportunityName()!=null && !oppartunity.getOpportunityName().isEmpty())
		{
		   opportunity.setOpportunityName(oppartunity.getOpportunityName());
		}
		
		
		if(oppartunity.getOpportunitySize()>0)
		{
		opportunity.setOpportunitySize(oppartunity.getOpportunitySize());
		}
		
		opportunity.setContact(contact);  
		
		opportunity.setOffering(offer);
		/**if we want to change contact**/
		/**to save existing information to  database*/
		opportunityRepo.save(opportunity);
		return opportunity;
		}
	}
	/**update opportunity by contact id**/
	@Override
	public Opportunity updateOpportunityByContactId(String oppportunity_id, String contact_id)
	{
		if(oppportunity_id==null || oppportunity_id.equals(" ")||contact_id==null || contact_id.equals(" "))
		{
			throw new NullDataException("contact id and opportunity id should not be null please enter valid data");
		}
		Contact contact = contactRepository.findById(contact_id).orElseThrow(()-> new DataNotFoundException("contact","contact_id",contact_id));
		Opportunity opportunities=opportunityRepo.findById(oppportunity_id).orElseThrow(()-> new DataNotFoundException("opportunity","opportunity_id",oppportunity_id));
		opportunities.setContact(contact);
		opportunityRepo.save(opportunities);
		return opportunities;
	}
	
	 /**
    * Delete  a Opportunity entity by the provided id.
    *
    * @return The Opportunity entity associated with the provided id, or RuntimeException if not found.
    */
	@Override
	public  void deleteOpportunity(String id) 
	{
		 if (id == null || id.trim().isEmpty()) {
		        throw new NullDataException("Opportunity ID should not be empty. Please enter a valid ID.");
		    } else {
		        Opportunity opportunity = opportunityRepo.findById(id)
		                .orElseThrow(() -> new DataNotFoundException("Opportunity", "id", id));

		        // Delete associated records in opportunity_sub table if present
		        // Replace "OpportunitySub" with the actual class name for opportunity_sub table
		        List<OpportunitySub> associatedSubRecords = opportunitySubRepository.findByOpportunityId(opportunity);
		        opportunitySubRepository.deleteAll(associatedSubRecords);
		        // Delete the Opportunity record
		        opportunityRepo.delete(opportunity);
		    }
	}
}
