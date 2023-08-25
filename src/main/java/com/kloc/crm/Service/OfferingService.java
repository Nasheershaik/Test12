/**
 * Service interface for managing Offering entities in the CRM system.
 * 
 * @Author_name: AnkushJadhav
 * @File_name: OfferingService.java
 * @Created_Date: 5/7/2023
 */
package com.kloc.crm.Service;

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
	 * @param offering The new offer details to be added to the database.
	 * @return A ResponseEntity with a status message.
	 */
	ResponseEntity<String> CreateOffering(Offering offering);
	
	/**
	 * Retrieves an offering with the corresponding offering ID.
	 *
	 * @param offeringId The ID of the offering to retrieve.
	 * @return The offer details with the specified offering ID.
	 */
	OfferingDTO GetOfferingByOfferingID(String offeringId);
	
	/**
	 * Retrieves all offers present in the database.
	 *
	 * @return A list of all offers.
	 */
	List<OfferingDTO> GetAllOffering();
	
	/**
	 * Retrieves offers within a specified price range.
	 *
	 * @param flooringPrice The minimum price of the offerings to retrieve.
	 * @param ceilingPrice The maximum price of the offerings to retrieve.
	 * @return A list of offerings within the specified price range.
	 */
	List<OfferingDTO> GetAllOfferingByPriceRange(long floorPrice, long ceilingPrice);
	
	/**
	 * Updates offer details with the corresponding offering ID and provided details.
	 *
	 * @param offering The updated details of the offer.
	 * @param offeringId The ID of the offer to be updated.
	 * @return A ResponseEntity with a status message.
	 */
	ResponseEntity<String> UpdateOfferingByOfferingId(Offering offering, String offeringId);
	
	/**
	 * Deletes an offering with the provided offering ID.
	 *
	 * @param offeringId The ID of the offering to delete.
	 * @return A ResponseEntity indicating if the offering was deleted or not.
	 */
	ResponseEntity<String> DeleteOfferingByOfferingId(String offeringId);
	
}