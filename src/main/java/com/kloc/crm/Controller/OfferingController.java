/**
 * This class is the controller for managing offerings in the CRM system.
 */
package com.kloc.crm.Controller;

/**
 * The controller class that handles HTTP requests related to offerings.
 * 
 * @author_name  : Ankush
 * @File_name	 : OfferingController.java
 * @Created_Date : 5/7/2023
 */

// all user and necessary imports for this class
import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

import com.kloc.crm.Entity.Contact;
import com.kloc.crm.Entity.Offering;
import com.kloc.crm.Service.OfferingService;
import com.kloc.crm.dto.OfferingDTO;

@CrossOrigin("*")
@RestController
@RequestMapping("OfferingController")
public class OfferingController 
{
	// offering service interface to call the methods present in the offeringImpl class.
	@Autowired
	OfferingService offeringService;

	/**
	 * Creates a new offering.
	 *
	 * @RequestBody Offering 	: 	offering The offering object to be created.
	 */
	@PostMapping("create_offering")
	public ResponseEntity<String> CreateOffering(@RequestBody Offering offering)
	{
		return offeringService.CreateOffering(offering);
	}
	
	/**
	 * Retrieves all offerings.
	 *
	 * @return 	: 	A list of all offerings.
	 */
	
	@GetMapping("get_all_offering")
	private ResponseEntity<List<OfferingDTO>> GetAllOffering() 
	{
		return new ResponseEntity<List<OfferingDTO>>(offeringService.GetAllOffering(),HttpStatus.OK);
	}
	/**
	 * Retrieves an offering by its offering ID.
	 *
	 * @PathVariable offeringId		: 	offeringId The ID of the offering to retrieve.
	 * @return						:	The offering with the specified ID.
	 */
	@GetMapping("get_offering_by_offeringId/{offeringId}")
	private ResponseEntity<OfferingDTO> GetOfferingByOfferingID(@PathVariable String offeringId)
	{
		return new ResponseEntity<OfferingDTO>(offeringService.GetOfferingByOfferingID(offeringId),HttpStatus.OK);
	}
	
	/**
	 * Update Offering on Offering Id.
	 *
	 * @PathVariable Offering Id 	:	offer id which offer have to be updated.
	 * @RequestBody offering		:	offering The offering object to be update.
	 */
	@PutMapping("update_offering_by_offeringId/{offeringId}")
	private ResponseEntity<String> updateOfferingByOfferingId(@RequestBody Offering offering, @PathVariable String offeringId)
	{
		
		 return offeringService.updateOfferingByOfferingId(offering,offeringId);
	}
	
	/**
	 * Delete offering with the provided offering id.
	 * @return 
	 *
	 * @PathVariable offeringId 	:	offering id to delete offer.
	 * @return 						:	offering id of offer which offer have to be delete.
	 */
	@DeleteMapping("delete_offering_by_offeringId/{offeringId}")
	private ResponseEntity<String> deleteOfferingByOfferingId(@PathVariable String offeringId)
	{
		return offeringService.deleteOfferingByOfferingId(offeringId);
	}
}
