/**
 * @author Mjilani
 * @date   07-07-23
 * @filename : OpportuinitySubService.java
 * this interface contains the abstract methods for the implementation classes
 */
package com.kloc.crm.Service;

import java.time.LocalDate;
import java.util.List;
import com.kloc.crm.Entity.OpportunitySub;

public interface OpportunitySubServcie
{
	/**
	 *abstract method for creating or inserting the opportunity
	 */
	OpportunitySub saveOpportunity(OpportunitySub opportunity,String OportinityId);
	
	/**
	 *abstract method to fetch all opportunities
	 */	
	List<OpportunitySub> getAllOppartunities();
	
	/**
	 *abstract method  to fetch based on id
	 */
	OpportunitySub getOpportunitybyId(String id);
	
	/**to fetch all opportunities based on date**/
	List<OpportunitySub> getOpportunityByDate(LocalDate date);
	
	/**
	 *abstract methods to update  based on id and oppotunitySub_id
	 */
	OpportunitySub updateOpportunitySub(OpportunitySub oppartunitySub,String opportunitySubId,String opportunity_id);
	
	
	/**abstract method to update all opportunity sub entities**/
	OpportunitySub updateOpportunitySubEntity(OpportunitySub opportunitySub,String opportunitySubId,String opportunityId);
	
	/**
	 *abstract methods to update opportunitySub  based on id and oppotunity_id
	 */
	OpportunitySub updateOpportunitySubByOpportunityId(String opportunitySubId,String opportunityId);
	
}
