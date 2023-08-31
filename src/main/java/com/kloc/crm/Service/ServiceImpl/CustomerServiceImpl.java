/**
 * @author Mjilani
 * @date 05-07-23
 * @fileName : CustomerServiceImpl
 * Service class for handling business logic related to User entities.
 * Provides methods to interact with the User repository and perform operations on User objects.
 * this class will implement service interface to override abstract methods of that interface 
 */
package com.kloc.crm.Service.ServiceImpl;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.access.InvalidInvocationException;
import org.springframework.stereotype.Service;
import com.kloc.crm.Entity.Contact;
import com.kloc.crm.Entity.Customer;
import com.kloc.crm.Entity.Opportunity;
import com.kloc.crm.Entity.Status;
import com.kloc.crm.Exception.DataNotFoundException;
import com.kloc.crm.Exception.InvalidInput;
import com.kloc.crm.Exception.NullDataException;
import com.kloc.crm.Repository.ContactRepository;
import com.kloc.crm.Repository.CustomerRepository;
import com.kloc.crm.Repository.OpportunityRepository;
import com.kloc.crm.Repository.StatusRepo;
import com.kloc.crm.Service.CustomerService;
@Service
@SuppressWarnings("unused")
public class CustomerServiceImpl implements CustomerService
{
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	private OpportunityRepository opportunityRepository;
	@Autowired
	private StatusRepo statusRepository;
	
	private static final Logger logger=LogManager.getLogger(CustomerServiceImpl.class);
	
	/**
	 * @param customerRepo
	 * constructor injection
	 */
	public CustomerServiceImpl(ContactRepository contactRepository,CustomerRepository    customerRepo,
							OpportunityRepository	opportunityRepository,	 StatusRepo statusRepository)
	{
		super();
		this.customerRepo=customerRepo;
		this.contactRepository= contactRepository;
		this.opportunityRepository= opportunityRepository;
		this.statusRepository= statusRepository;
	}
	 /**
     * Saves the given Customer entity.
     * @return The saved  Customer entity.
     */
	@Override
     public Customer saveCustomer(Customer customer,String contactId)
     {
			if(contactId == null || contactId.equals(""))
			{
				throw new InvalidInput("contact id can not be empty");
			}
			if(contactId!=null||contactId.equals(""))
			{
//				Opportunity a = opportunityRepository.findById(opportunityId).orElseThrow(
//						() -> new DataNotFoundException("opportunity", "opportunityId", opportunityId));

				Contact c = contactRepository.findById(contactId)
						.orElseThrow(() -> new DataNotFoundException("contacts", "contactsId", contactId));
				
				
				customer.setContact(c);
//				customer.setOpportunity(a);
				logger.info("Customer saved sucessfully with {}:",contactId);
				
		   }
	  return customerRepo.save(customer);
	}

		
//				Status status = statusRepository.findById(status_id).orElseThrow(()-> new 
//						    DataNotFoundException("Status","stauts_id",status_id));
//				Optional<Status> statusOptional = statusRepository.findById(status_id);
//				
//				if (statusOptional.isPresent()) {
//				       Status status1 = statusOptional.get();
//
//				    // Check if the status type is equal to "deal" (case-insensitive)
//				    if (status.getStatusType().equalsIgnoreCase("deal")) {
//				        customer.setStatus(status);
//				    } 
//			
//		        }
	 /**
     * Retrieves all customers
     */
	@Override
	public List<Customer> getAllCustomers() 
	{
		
		return  customerRepo.findAll();
	}
	 /**
     * Retrieves a Customer entity by the provided id.
     *
     * @return The Customer entity associated with the provided id, or exception if not found.
     */
	@Override
	public Customer getCustomerbyId(String id)
	{
		if(id==null || id.equals(""))
		{
			throw new InvalidInput("customer id cannot be null");
		}
		else
		{
			Optional<Customer> customer= customerRepo.findById(id);
			if(customer.isPresent())
				return customer.get();
			else
				throw new DataNotFoundException("Customer","id",id);
		}
	}
	/**get customer by contact_id**/
	@Override
	public List<Customer> getCustomerByContactId(String contact_id)
	{
		if(contact_id==null || contact_id.equals(""))
		{
			throw new InvalidInput("Contact id should not be null or empty");
		}
		else
		{
		return customerRepo.findAll().stream().filter(e->e.getContact().getContactId().toLowerCase().equals(contact_id.toLowerCase())).toList();
      	}
	}
//	/**get customer by opportunity id**/
//	@Override
//	public List<Customer> getCustomerByOpportunityId(String opportunityId) 
//	{
//		 if(opportunityId==null || opportunityId.equals(""))
//		 {
//			 throw new InvalidInput("OpportunityId should not be empty");
//		 }
//		 return customerRepo.findAll().stream().filter(e->e.getOpportunity().getOpportunityId().toLowerCase().equals(opportunityId.toLowerCase())).toList();
//	}
	 /**
     * Update  a Customer entity by the provided id.
     *
     * @return The Customer entity associated with the provided idm, or RuntimeException if not found.
     */
	@Override
	public Customer updateCustomer(Customer customer, String customer_id,String contact_id)
	{
		Contact contact=contactRepository.findById(contact_id).orElseThrow(()-> new DataNotFoundException("contact_Id","contact",contact_id));
		Customer existingCustomer= customerRepo.findById(customer_id)
				 .orElseThrow(()->new DataNotFoundException("Customer","id",customer_id));
		
		if(customer.getCustomerCreatedDate()!=null)
		{
		existingCustomer.setCustomerCreatedDate(customer.getCustomerCreatedDate());
		}

		existingCustomer.setContact(contact);
		
		//to save into database
		customerRepo.save(existingCustomer);
		return existingCustomer;
	}
	
	/**update customer id based on contact id and opportuntiy id **/
	@Override
	public Customer updateCustomerBycontactid(String customer_id, String contact_id)
	{
		Contact contact=contactRepository.findById(contact_id).orElseThrow(()-> new DataNotFoundException("contact_Id","contact",contact_id));
		Customer customer = customerRepo.findById(customer_id).orElseThrow(()-> new DataNotFoundException("customer","customer_id",customer_id));
		customer.setContact(contact);
		customerRepo.save(customer);
		return customer;
	}
	
	 /**
	    * Delete  a  Customer  entity by the provided id.
	    *
	    * @return The Customer entity associated with the provided id, or RuntimeException if not found.
	    */
	@Override
	public void deleteCustomer(String id) 
	{
		customerRepo.findById(id)
		.orElseThrow(()->new DataNotFoundException("Customer","id",id));
		customerRepo.deleteById(id);
	}
}
