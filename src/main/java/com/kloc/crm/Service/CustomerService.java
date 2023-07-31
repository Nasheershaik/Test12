/**
 * @author Mjilani
 * @date 05-07-2023
 * @fileName : CustomerService.java
 * this interface contains the abstract methods for the implementation classes
 */
package com.kloc.crm.Service;
import java.util.List;
import com.kloc.crm.Entity.Customer;
public interface CustomerService
{
	/**
	 *abstract method for creating or inserting the Customer
	 */
	Customer saveCustomer(Customer customer,String opportunityId,String contactId);
	
	/**
	 * abstract method to fetch all Customers
	 */
	List<Customer> getAllCustomers();
	
	/**
	 * abstract method  to fetch based on id
	 */
	Customer getCustomerbyId(String id);
	
	/**
	 * get customers based on contact id
	 */
	List<Customer> getCustomerByContactId(String contactId);
	
	/**get customers based o n opportunity id**/
	List<Customer> getCustomerByOpportunityId(String opportunityId);
	
	/**
	 * abstract method to update based on id
	 */
	Customer updateCustomer(Customer customer,String customer_id,String contact_id);
	
	/**update customer based on contact id**/
	Customer updateCustomerBycontactid(String customer_id,String contact_id);
	
	/**
	 * delete opportunity based on id
	 */
	void deleteCustomer(String id);
}