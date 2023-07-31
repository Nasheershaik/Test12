/**
 * @author : Mjilani
 * @date :06-July-2023
 * @fileName : CustomerController.java
 *This Controller class  is responsible for handling incoming HTTP requests,
   processing the necessary logic, and generating appropriate responses.
 *@RestController is the combination of both @controller and @RequestBody  also its responsible
 *for building restful API's.
 */
package com.kloc.crm.Controller;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.kloc.crm.Entity.Customer;
import com.kloc.crm.Exception.DataNotFoundException;
import com.kloc.crm.Exception.InvalidInput;
import com.kloc.crm.Exception.NullDataException;
import com.kloc.crm.Service.ContactService;
import com.kloc.crm.Service.CustomerService;
@RestController
@CrossOrigin("*")
@RequestMapping("/app")
public class CustomerController
{
	@Autowired
	private CustomerService customerService;
	
	private static final Logger logger = LogManager.getLogger(CustomerController.class.getName());
	/**
	 * @param customerService * constructor injection we can use @Autowired also if
	 *                        we used setter injection
	 */
	public CustomerController(CustomerService customerService)
	{
		super();
		this.customerService = customerService;
	}
	/**
	 * Responsible for Creating EndPoint Customer
	 * 
	 * @PostMapping is the responsible for handling HTTP Post request
	 * @return Newly Created Customers
	 **/
	@PostMapping("/save/{opportunityId}/{contact_Id}")
	public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer,@PathVariable("opportunityId") String opportunityId, @PathVariable("contact_Id") String contact_Id)
	{
		logger.info("Request recived to save customer");
		
		 if(opportunityId==null || contact_Id==null || opportunityId.equals(" ")||contact_Id.equals(" "))
		 {
			 logger.error("opportunity id or contact id should not be null");
			 throw new InvalidInput("please enter valid opportunity id or contact id");
		 }
	    Customer savedCustomer = customerService.saveCustomer(customer,opportunityId,contact_Id);

	    logger.info("Customer saved successfully with ID: {}", savedCustomer.getCustomerId());

	    return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
	}
	/**
	 * Responsible for Retrieving endPoint Customers
	 * 
	 * @GetMapping is the responsible for handling HTTP GET Request
	 * @return all the Customers
	 */
	@GetMapping("/getAllCustomers")
	@ResponseStatus(HttpStatus.OK)
	public List<Customer> getAllCustomers()
	{
		 logger.info("Request to fetch all the customers");
		List<Customer> customers = customerService.getAllCustomers();
		
		logger.info("Successfully retrived all customers");
		return customers;
	}
	/**
	 * Responsible for Retrieving endPoint Customers based on id.
	 * 
	 * @GetMapping is the responsible for handling HTTP GET Request.
	 * @return all the Customers based on id.
	 */
	@GetMapping("getCustomerBy/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("id") String id) 
	{
		 logger.trace("Request to fetch all the customers based on customer id;{}",id);
		if(id==null || id.equals(" "))
		{
		   logger.warn("Customer id should not be null");
		   throw  new InvalidInput("Please enter customer id");
         }
		Customer customer=customerService.getCustomerbyId(id);
		if(customer!=null)
			logger.info("Found customer with id {}: {}", id, customer);
		return  new ResponseEntity<>(customer,HttpStatus.OK);
	}
	/** this method returns all customers by contact_id **/
	@GetMapping("getCustomersBycontact/{contact_id}")
	public ResponseEntity<List<Customer>> getCustomerByContactId(@PathVariable("contact_id") String contact_id)
	{
		 logger.trace("Request to fetch all the customers based on contact_id:{}",contact_id);
		if(contact_id==null||contact_id.equals(" "))
		{
			logger.error("contact id should not be null");
			throw new InvalidInput("please enter contact_id");
		}
		List<Customer> customersByContact=customerService.getCustomerByContactId(contact_id);
		  if (!customersByContact.isEmpty())
		  {
		logger.info("Sucessfully retrived all the customers based on Contact_Id");
		return new ResponseEntity<>(customersByContact, HttpStatus.OK);
		  }
		  else
		  {
			  logger.warn("No customers found for contact_id: {}", contact_id);
			  return ResponseEntity.notFound().build();
		  }
	}
	
	
	/** getAllCustomersByopportunity id **/
	@GetMapping("getAllCustomersBy/{opportunity_id}")
	public ResponseEntity<List<Customer>> getCustomerByOpportunityId(@PathVariable("opportunity_id") String opportunity_id) 
	{
		 logger.trace("Request to fetch all the customers based opportunity id:{}",opportunity_id);
		 if(opportunity_id==null || opportunity_id.equals(" "))
		 {
			 logger.error("contact id should not be null");
			 throw new InvalidInput("Please enter opportunity id");
		 }
		 List<Customer> customersByOpportunity=customerService.getCustomerByOpportunityId(opportunity_id);
		 
		 if (!customersByOpportunity.isEmpty())
		  {
			logger.info("Sucessfully retrived all the customers based on opportunity_id");
		return new ResponseEntity< >(customersByOpportunity,HttpStatus.OK);
		  }
		  else
		  {
			  logger.warn("No customers found for opportunity_id: {}", opportunity_id);
			  return ResponseEntity.notFound().build();
		  }
	}
	/**
	 * Responsible for Updating endPoint Customers
	 * 
	 * @PUTMapping is the responsible for handling HTTP Put Request
	 * @return all the updated Customers update all the fields along with the
	 *         customer fields and contact fields
	 */
	@PutMapping("customer/{customer_id}/{contact_id}")
	public ResponseEntity<Customer> updatebyId(@RequestBody Customer customer, @PathVariable String customer_id,@PathVariable String contact_id) 
	{
		logger.trace("Request to update  all the customers based on customer id and contact id:{}",customer_id,contact_id);
		if(customer_id==null ||contact_id==null||customer_id.equals(" ")||contact_id.equals(" "))
		{
			logger.error("Contact id or customer id should not be null");
			throw new InvalidInput("Please enter valid customer id and  contact_id");
		}
		 Customer updatecustomers = customerService.updateCustomer(customer, customer_id, contact_id);

	        if (updatecustomers == null) {
	            logger.warn("No customer found with customer_id: {} and contact_id: {}", customer_id, contact_id);
	            return ResponseEntity.notFound().build();
	        }
	        else {
		 logger.info("Updated customer bsed on customer_id and contact_id");
		return new ResponseEntity<>(updatecustomers,HttpStatus.OK);
	        }
	}
	
	/** update contact id with respective to the customer **/
	@PutMapping("updateContact/{customer_id}/{contact_id}")
	public ResponseEntity<Customer> updateContactId(@PathVariable("customer_id") String customer_id,@PathVariable("contact_id") String contact_id)
	{
		logger.trace("Request to update  contact based on  the  customer id and contact id:{}",customer_id,contact_id);
		if(customer_id==null ||contact_id==null||customer_id.equals(" ")||contact_id.equals(" "))
		{
			logger.error("Contact id or customer id should not be null");
			throw new InvalidInput("Please enter valid customer id and  contact_id");
		}
		 Customer updatedCustomers = customerService.updateCustomerBycontactid(customer_id, contact_id);
		 
		 if (updatedCustomers == null) {
	            logger.warn("No customer found with customer_id: {} and contact_id: {}", customer_id, contact_id);
	            return ResponseEntity.notFound().build();
	        }
		 else {
	        logger.info("Successfully updated customer contact based on customer_id: {} and contact_id: {}", customer_id, contact_id);
	        return new ResponseEntity<>(updatedCustomers, HttpStatus.OK);
	    }
	}
	/**
	 * Responsible for deleting endPoint Customers based on id,if id not found
	 * through RunTime exception
	 * 
	 * @DeleteMapping is the responsible for handling HTTP DELETE Request
	 * @return all the Customer except deleted
	 */
	@DeleteMapping("/customerid/{id}")
	public ResponseEntity<String> deleteDept(@PathVariable("id") String id) 
	{
		 logger.trace("Request received to delete the customer by customer id: {}", id);
		if (id==null ||id.equals(" ")) 
		{
			logger.error("id should not be null");
			throw  new InvalidInput("Please enter valid cuto,er id");
		}
		/** delete from database **/
		customerService.deleteCustomer(id);
		 logger.info("Customer successfully deleted based on id: {}", id);
		return new ResponseEntity<String>("Customer deleted successfully", HttpStatus.OK);
	}
}