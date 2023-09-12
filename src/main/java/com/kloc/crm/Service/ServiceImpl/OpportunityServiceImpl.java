/**
 * @author Mjilani
 * @date 06-07-23
 * @filename : OpportunityServiceImpl
 * Service class for handling business logic related to User entities.
 * Provides methods to interact with the User repository and perform operations on User objects.
 * this class will implement service interface to override abstract methods of that interface 
 */
package com.kloc.crm.Service.ServiceImpl;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.text.html.CSS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kloc.crm.Entity.Contact;
import com.kloc.crm.Entity.ContactSub;
import com.kloc.crm.Entity.Customer;
import com.kloc.crm.Entity.Offering;
import com.kloc.crm.Entity.Opportunity;
import com.kloc.crm.Entity.OpportunitySub;
import com.kloc.crm.Exception.DataNotFoundException;
import com.kloc.crm.Exception.InvalidInput;
import com.kloc.crm.Exception.NullDataException;
import com.kloc.crm.Repository.ContactRepository;
import com.kloc.crm.Repository.ContactSubRepository;
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
    @Autowired
	private ContactSubRepository contactSubRepository;
	private List<Opportunity> list;
	
	/**Constructor injection */
	public OpportunityServiceImpl(OpportunityRepository opportunity,CustomerRepository customerRepository,ContactSubRepository contactSubRepository,OfferingRepository offeringRepository)
	{
		super();
		this.opportunityRepo=opportunity;
		this.customerRepository=customerRepository;
		this.contactSubRepository=contactSubRepository;
		this.offeringRepository=offeringRepository;
	}
	 /**
     * Saves the given Opportunity entity.
     * @return The saved  Opportunity entity.
     */
	@Override
	public Opportunity saveOppartunity(Opportunity oppartunity,String contact_sub_Id)
	{
		ContactSub contactSub=contactSubRepository.findById(contact_sub_Id).orElseThrow(()->new DataNotFoundException("contact","contactId", contact_sub_Id));
		oppartunity.setContactSub(contactSub);
		return opportunityRepo.save(oppartunity);
	}
	
	/**
     * Saves the given Opportunity entity.
     * @return The saved  Opportunity entity based on ContactSub_id and Offering-id.
     */
	@Override
	public Opportunity saveOpportunities(Opportunity opportunity, String contactSub_Id, String offering_id)
	{
		if(contactSub_Id == null || contactSub_Id.equals(""))
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
		ContactSub contactsSub= contactSubRepository.findById(contactSub_Id).orElseThrow(()-> new DataNotFoundException("contact","contact_id", offer));
		opportunity.setContactSub(contactsSub);
		opportunity.setOpportunityCreatedDate(LocalDate.now());
		
		List<OpportunitySub> collect = opportunitySubRepository.findAll().stream().filter(e->e.getOpportunityId().getOpportunityName().equals(opportunity.getOpportunityName())).collect(Collectors.toList());
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
	public List<Opportunity> getOpportunityByContactSubId(String contactSub_id) 
	{
		if(contactSub_id==null || contactSub_id=="")
		{
			throw new NullDataException("contact_id should not be null");
		}
		else
		{
			return opportunityRepo.findAll().stream().filter(e->e.getContactSub().getContactSubId().toLowerCase().equals(contactSub_id.toLowerCase())).toList();
		}
	}
	
	 /**
     * Update  a Opportunity entity by the provided id.
     * @return The Opportunity entity associated with the provided id, or RuntimeException if not found.
     */
	@Override
	public Opportunity updateOpportunity(Opportunity oppartunity, String id,String contactSub_id,String offering_id)
	{
		if(id==null ||id.equals(" ")||contactSub_id==null || contactSub_id.equals(" ")||offering_id==null ||offering_id.equals(" "))
		{
			throw new NullDataException("id should not be null or empty please enter valid id");
		}
		else {
		Offering offer =offeringRepository.findById(offering_id).orElseThrow(()-> new DataNotFoundException("offering","offering_id",offering_id));
		ContactSub contactSub = contactSubRepository.findById(contactSub_id).orElseThrow(()-> new DataNotFoundException("contact","contact_id",contactSub_id));
		Opportunity opportunity=opportunityRepo.findById(id).orElseThrow(()->new DataNotFoundException("Opportunity","id",id));
		
		
		if(oppartunity.getOpportunityName()!=null && !oppartunity.getOpportunityName().isEmpty())
		{
		   opportunity.setOpportunityName(oppartunity.getOpportunityName());
		}
		if(oppartunity.getOpportunitySize()>0)
		{
		opportunity.setOpportunitySize(oppartunity.getOpportunitySize());
		}
		opportunity.setContactSub(contactSub);  
		
		opportunity.setOffering(offer);
		/**if we want to change contact**/
		/**to save existing information to  database*/
		opportunityRepo.save(opportunity);
		return opportunity;
		}
	}
	/**update opportunity by contact id**/
	@SuppressWarnings("unlikely-arg-type")
	@Override
	public Opportunity updateOpportunityByContactSubId(Opportunity opportunity,String oppportunity_id, String contactSub_id)
	{
		if(oppportunity_id==null || oppportunity_id.equals(" ")||contactSub_id==null || contactSub_id.equals(" "))
		{
			throw new NullDataException("contact id and opportunity id should not be null please enter valid data");
		}
		
		 Opportunity findFirst = opportunityRepo.findAll().stream().filter(e -> e.getContactSub().getContactSubId().equals(contactSub_id)).findFirst().get();
		
		 findFirst.setOpportunityName(opportunity.getOpportunityName());
		 findFirst.setOpportunitySize(opportunity.getOpportunitySize());
		 findFirst.setOpportunityCreatedDate(opportunity.getOpportunityCreatedDate());
		 
//		ContactSub contactSub = contactSubRepository.findById(contactSub_id).orElseThrow(()-> new DataNotFoundException("contact","contact_id",contactSub_id));
//		Opportunity opportunities=opportunityRepo.findById(oppportunity_id).orElseThrow(()-> new DataNotFoundException("opportunity","opportunity_id",oppportunity_id));
//		opportunities.setContact_sub(contactSub);
		opportunityRepo.save(findFirst);
		return findFirst;
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
	 /**
	    * Retrive all Opportunity entity by the provided status_type
	    *
	    * @return The Opportunity entity associated with the provided type, or RuntimeException if not found.
	    */
	@Override
	public List<Opportunity> getAllOpportunityByType(String Status_type)
	{
		if(Status_type==null || Status_type.equals(""))
		{
			throw new NullDataException("Status-Type should not be empty");
		}
		else {
		return opportunityRepo.findAll().stream().filter(e->{
			List<OpportunitySub> list=opportunitySubRepository.findByOpportunityId(e);
			
			if(!list.isEmpty()) 
			{
				OpportunitySub last=list.get(list.size()-1);
				if(last.getStatus().getStatusValue().equalsIgnoreCase(Status_type)) {
				return true;
			}else {
				return false;
			}
				
			}
			return false;
		}).toList();
	}
	}
	 /**
	    * Retrive all Opportunity entity by the provided status_type and dateRange
	    *
	    * @return The Opportunity entity associated with the provided type and dateRange, or RuntimeException if not found.
	    */
	@Override
	public List<Opportunity> getAllOpportunityByDate(LocalDate fromdate, LocalDate toDate,String opportunityType) {
		if(opportunityType==null || opportunityType.equals(""))
		{
			throw new NullDataException("Status-Type should not be empty");
		}
		else {
		return opportunityRepo.findAll().stream().filter(e->{
			List<OpportunitySub> list=opportunitySubRepository.findByOpportunityId(e);
			if(!list.isEmpty()) 
			{
				OpportunitySub last=list.get(list.size()-1);
				if(last.getStatus().getStatusValue().equalsIgnoreCase(opportunityType)&&(last.getOpportunityStatusDate().isAfter(fromdate)||last.getOpportunityStatusDate().equals(fromdate))&&(last.getOpportunityStatusDate().isBefore(toDate)||last.getOpportunityStatusDate().equals(toDate))) {
				return true;
			}else {
				return false;
			}
			}
			return false;
		}).toList();
	    }
	}
	
	 /**
	    * Retrive all Opportunity for Specific-Customer
	    *
	    * @return The Opportunity entity associated with the provided Customer based on Contact_id, or RuntimeException if not found.
	    */
	@Override
	public List<Opportunity> getAllOpportuntiesByCustomer(Customer customer, String contact_id) {
		if(contact_id==null || contact_id.equals(""))
		{
			throw new NullDataException("Contact_id Shpuld not be null please enter Valid Contact_id");
		}
		Contact contact=contactRepository.findById(contact_id).orElseThrow(()->new DataNotFoundException("invalid contact"));
//		contact.getContactId()
		Customer cust=customerRepository.findAll().stream().filter(e->e.getContact().getContactId().equals(contact_id)).findFirst().get();
		if(cust == null) 
		{
			throw new InvalidInput("this contact is not our customer");
		}
		return opportunityRepo.findAll().stream().filter(e -> e.getContactSub().getContactId().getContactId().equals(contact_id)).collect(Collectors.toList());
	}
	
	
	
	
	@Override
	public List<Opportunity> getAllOpportunitesByCustomerId(String customer_id) 
	{
		if(customer_id==null || customer_id.equals(""))
		{
			throw new NullDataException("Please enter Customer_id");
		}
		Customer customer=customerRepository.findById(customer_id).get();
		customer.getContact();
		List<ContactSub> list2 = contactSubRepository.findAll().stream().filter(e->e.getContactId().equals(customer.getContact())).toList();
//		
//		List<Opportunity> list3 = new ArrayList<>();
//		
//		for (int i = 0; i < list2.size(); i++)
//		{
//			list3.add(opportunityRepo.findByContactSub(list2.get(i)));
//		}
		List<Opportunity> list3 = list2.stream()
			    .map(contact -> opportunityRepo.findByContactSub(contact)).filter(e->e!=null)
			    .collect(Collectors.toList());

		return list3;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
