/**
 * @author Mjilani
 * @date  06-07-23
 * @filename OpportunityService.java
 * this interface contains the abstract methods for the implementation classes
 */
package com.kloc.crm.Service;

import java.util.List;
import com.kloc.crm.Entity.Opportunity;

public interface OpportunityService
{
	/**
	 *abstract method for creating or inserting the opportunuity
	 */
	Opportunity saveOppartunity(Opportunity oppartunity,String contact_Id);
	
	Opportunity saveOpportunities(Opportunity opportunity,String contact_Id,String offering_id);
	
	/**
	 *abstract method to fetch all opportunities
	 */
	List<Opportunity> getAllOppartunities();
	
	/**
 	 *abstract methods  to fetch based on id
 	 */
	Opportunity getOpportunitybyId(String id);
	
	/**this method returns all the  opportunities based on contact Id**/
	List<Opportunity> getOpportunityByContactId(String contactId);
	
	/**
	 *abstract methods to update opportunity based on id
	 *it will update all the opportunity entities
	 */
	Opportunity updateOpportunity(Opportunity oppartunity,String id,String contact_id,String offering_id);
	
	
	/**this method is used to update opportunity contact id**/
	Opportunity updateOpportunityByContactId(String  oppportunity_id,String contact_id);

	/**
	 *delete opportunity based on id
	 */
	void deleteOpportunity(String id);
}
