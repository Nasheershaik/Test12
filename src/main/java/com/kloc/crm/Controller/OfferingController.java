/**
 * The controller class that handles HTTP requests related to offerings.
 * 
 * @Author_name: AnkushJadhav
 * @File_name: OfferingController.java
 * @Created_Date: 5/7/2023
 */
package com.kloc.crm.Controller;

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
	 * @param offering The offering object to be created, provided in the request body.
	 * @return A ResponseEntity with a status code and message.
	 */
	@PostMapping("create_offering")
	public ResponseEntity<String> CreateOffering(@RequestBody Offering offering)
	{
		return offeringService.CreateOffering(offering);
	}
	
	/**
	 * Retrieves all offerings.
	 *
	 * @return A ResponseEntity containing a list of all offerings.
	 */
	@GetMapping("get_all_offering")
	private ResponseEntity<List<OfferingDTO>> GetAllOffering() 
	{
		return new ResponseEntity<List<OfferingDTO>>(offeringService.GetAllOffering(),HttpStatus.OK);
	}

	/**
	 * Retrieves offerings within a specified price range.
	 *
	 * @param flooringPrice The minimum price of the offerings to retrieve.
	 * @param ceilingPrice The maximum price of the offerings to retrieve.
	 * @return A ResponseEntity containing a list of offerings within the specified price range.
	 */
	@GetMapping("get_all_offering_by_price_range/{floorPrice}/{ceilingPrice}")
	private ResponseEntity<List<OfferingDTO>> GetAllOfferingByPriceRange(@PathVariable long floorPrice,@PathVariable long ceilingPrice) 
	{
		return new ResponseEntity<List<OfferingDTO>>(offeringService.GetAllOfferingByPriceRange(floorPrice, ceilingPrice),HttpStatus.OK);
	}
	
	/**
	 * Retrieves an offering by its offering ID.
	 *
	 * @param offeringId The ID of the offering to retrieve.
	 * @return A ResponseEntity containing the offering with the specified ID.
	 */
	@GetMapping("get_offering_by_offeringId/{offeringId}")
	private ResponseEntity<OfferingDTO> GetOfferingByOfferingID(@PathVariable String offeringId)
	{
		return new ResponseEntity<OfferingDTO>(offeringService.GetOfferingByOfferingID(offeringId),HttpStatus.OK);
	}
	
	/**
	 * Updates an offering by its offering ID.
	 *
	 * @param offeringId The ID of the offering to be updated.
	 * @param offering The offering object with updated details provided in the request body.
	 * @return A ResponseEntity with a status code and message.
	 */
	@PutMapping("update_offering_by_offeringId/{offeringId}")
	private ResponseEntity<String> updateOfferingByOfferingId(@RequestBody Offering offering, @PathVariable String offeringId)
	{
		
		 return offeringService.UpdateOfferingByOfferingId(offering,offeringId);
	}
	
	/**
	 * Deletes an offering with the provided offering ID.
	 *
	 * @param offeringId The ID of the offering to be deleted.
	 * @return A ResponseEntity with the ID of the deleted offering.
	 */
	@DeleteMapping("delete_offering_by_offeringId/{offeringId}")
	private ResponseEntity<String> deleteOfferingByOfferingId(@PathVariable String offeringId)
	{
		return offeringService.DeleteOfferingByOfferingId(offeringId);
	}
}
