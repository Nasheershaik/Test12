package com.kloc.crm.Service;

/**
 * Service interface for managing Offering entities in the CRM system.
 * 
 * @author_name  : Ankush
 * @File_name	 : OfferingService.java
 * @Created_Date : 5/7/2023
 */

//all used and necessary import for this interface.
import java.util.List;
import org.springframework.http.ResponseEntity;
import com.kloc.crm.Entity.Offering;
import com.kloc.crm.dto.OfferingDTO;
public interface OfferingService
{
	/**
	 * Creates a new offer.
	 *
	 * @param offering 	: 	new offer details which have to be add in the database.
	 */
	ResponseEntity<String> CreateOffering(Offering offering);
	
	/**
	 * Retrieves an offering with the corresponding offering id.
	 *
	 * @param offeringId	:  	offeringId the ID of the offering to retrieve.
	 * @return 				:	The offer details with the specified offering id.
	 */
	OfferingDTO GetOfferingByOfferingID(String offeringId);
	
	/**
	 * Retrieves all offers present in the database.
	 *
	 * @return 	: 	A list of all offers.
	 */
	List<OfferingDTO> GetAllOffering() ;
	
	
	/**
	 * update offer details with the corresponding offering id and provided details.
	 *
	 * @param Offering 	:	details of offer which have to be updates.
	 * @param offeringd :  	of offer which offer have to be updated.
	 */
	ResponseEntity<String> updateOfferingByOfferingId(Offering offering, String offeringId);
	
	/**
	 * Delete offering with the provided offering id.
	 *
	 * @param offering id	:	offering id to delete offer.
	 * @return 				:	Response to the user if id deleted or not.
	 */
	ResponseEntity<String> deleteOfferingByOfferingId(String offeringId);
	
}