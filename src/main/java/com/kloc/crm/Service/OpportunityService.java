/**
 * @author Mjilani
 * @date  06-07-23
 * @filename OpportunityService.java
 * this interface contains the abstract methods for the implementation classes
 */
package com.kloc.crm.Service;

import java.time.LocalDate;
import java.util.List;

import com.kloc.crm.Entity.Customer;
import com.kloc.crm.Entity.Opportunity;
import com.kloc.crm.Entity.OpportunitySub;

public interface OpportunityService
{
	/**
	 *abstract method for creating or inserting the opportunuity
	 */
	Opportunity saveOppartunity(Opportunity oppartunity,String contact_sub_Id);
	
	
	/**Anstract method to save opportunity manually using Specific contact_id and offering_id**/
	Opportunity saveOpportunities(Opportunity opportunity,String contact_sub_Id,String offering_id);
	
	/**
	 *abstract method to fetch all opportunities
	 */
	List<Opportunity> getAllOppartunities();
	
	/**
 	 *abstract methods  to fetch based on id
 	 */
	Opportunity getOpportunitybyId(String id);
	
	/**this method returns all the  opportunities based on contact Id**/
	List<Opportunity> getOpportunityByContactSubId(String contactSub_id);
	
	/**Abstract method to getAll Opportunities for per customer based on contact_id**/
	List<Opportunity> getAllOpportuntiesByCustomer(Customer customer,String contact_id);
	
	/**
	 *abstract methods to update opportunity based on id
	 *it will update all the opportunity entities
	 */
	Opportunity updateOpportunity(Opportunity oppartunity,String id,String contact_id,String offering_id);
	
	/**this method is used to update opportunity contact id**/
	Opportunity updateOpportunityByContactSubId(Opportunity opportunity,String  oppportunity_id,String contactSub_id);

	/**
	 *delete opportunity based on id
	 */
	void deleteOpportunity(String id);
	
	
	/**Abstract method to getAllOpportunties based on Opportuntiy_status_type{opportunity?deal}**/
	List<Opportunity> getAllOpportunityByType(String Status_type);
	
	/**abstract method to getAllOpportunites within the date range along with Opportuntiy_status_type{opportunity?deal}**/
	List<Opportunity> getAllOpportunityByDate(LocalDate fromdate,LocalDate toDate,String opportunityType);
}
