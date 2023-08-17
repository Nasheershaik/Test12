/**
 * @author : Mjilani
 *@date : 07-7-2023
 * @fileName : OpportunitySubController
 *This Controller class  is responsible for handling incoming HTTP requests,
   processing the necessary logic, and generating appropriate responses.
 *@RestController is the combination of both @controller and @RequestBody  also its responsible
 *for building restful API's.
 */
package com.kloc.crm.Controller;
import java.time.LocalDate;
import java.util.List;
import java.util.ListIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
import com.kloc.crm.Entity.Opportunity;
import com.kloc.crm.Entity.OpportunitySub;
import com.kloc.crm.Exception.DataNotFoundException;
import com.kloc.crm.Exception.InvalidInput;
import com.kloc.crm.Repository.OpportunitySubRepository;
import com.kloc.crm.Service.OpportunitySubServcie;
@SuppressWarnings("unused")
@RestController
@CrossOrigin("*")
@RequestMapping("/app")
public class OpportunitySubController 
{
	@Autowired
	private OpportunitySubServcie opportunitySubServcie;
	
	private static final Logger logger = LogManager.getLogger(OpportunitySubController.class.getName());
	
	/**
	 * @param opportunitySubRepository
	 * @param opportunitySubServcie
	 */
	public OpportunitySubController(OpportunitySubRepository opportunitySubRepository,
			OpportunitySubServcie opportunitySubServcie) 
	{
		super();
		this.opportunitySubServcie = opportunitySubServcie;
	}
	/**
	 * Responsible for Creating EndPoint Opportunities
	 * 
	 * @PostMapping is the responsible for handling HTTP Post request
	 * @return Newly Created Opportunity
	 **/
	@PostMapping("saveOpportunitySub/{opportunityId}") // to handle post http request
	public ResponseEntity<OpportunitySub> saveOpportunity(@RequestBody OpportunitySub opportunity,
			@PathVariable("opportunityId") String opportunityId) {
		  logger.trace("Received request to save OpportunitySub based on Opportunity ID: {}", opportunityId);
		    logger.debug("Received opportunity data: {}", opportunity);
		    
		if(opportunityId==null || opportunityId.trim().isEmpty())
		{
			logger.error("opportuntiy id should not e  null");
			throw  new InvalidInput("Please enter valid opportunity id");
		}
		OpportunitySub opportunitySubFromDatabase=opportunitySubServcie.saveOpportunity(opportunity, opportunityId);
		
		logger.info("Successfully saved opportunity sub absed on oppportunity id:{}",opportunityId);
		return new ResponseEntity<>(opportunitySubFromDatabase,HttpStatus.CREATED);
	}
	/**
	 * Responsible for Retrieving endPoint Opportunities
	 * 
	 * @GetMapping is the responsible for handling HTTP GET Request
	 * @return all the Opportunities
	 */
	@GetMapping ("getAllOpportunitySub")// to fetch all data
	@ResponseStatus(HttpStatus.OK)
	public List<OpportunitySub> getAllOpportunities() {
		
		   logger.trace("Received request to fetch all opportunities");
	        logger.debug("Fetching all opportunities from the database");
//
	        List<OpportunitySub> opportunitySubFromDatabase = opportunitySubServcie.getAllOppartunities();
	        
	        if (opportunitySubFromDatabase.isEmpty()) {
	       logger.warn("No opportunities found in the database.");
	       } else {
	        	      logger.info("Successfully retrieved all opportunities. Count: {}", opportunitySubFromDatabase.size());
	        	       logger.trace("Opportunities data: {}", opportunitySubFromDatabase);
	        	    }
	       return opportunitySubFromDatabase;
	}
	/**
	 * Responsible for Retrieving endPoint Opportunities based on id.
	 * 
	 * @GetMapping is the responsible for handling HTTP GET Request.
	 * @return all the Opportunities based on id.
	 */
	@GetMapping("getOpportunitySub/{id}")
	public ResponseEntity<OpportunitySub> getOpportunityById(@PathVariable("id") String id) {
	    logger.trace("Request received to retrieve OpportunitySub based on ID: {}", id);
		if(id==null || id.trim().isEmpty())
		{
			logger.error("id should not be null");
			throw new InvalidInput("Please enetr valid opportunity Sub id");
		}
		logger.debug("Fetching OpportunitySub with ID: {}", id);
		OpportunitySub opportunitySubFromDatabase=opportunitySubServcie.getOpportunitybyId(id);
		
		 if (opportunitySubFromDatabase == null) {
	            logger.warn("OpportunitySub not found with ID: {}", id);
	            throw new DataNotFoundException("OpportunitySub not found with ID: " + id);
	        }
		 
		logger.info("Succesffully retirved all opportunity Sub based on id");
		logger.debug("OpportunitySub data: {}", opportunitySubFromDatabase);
		return new ResponseEntity< >(opportunitySubFromDatabase, HttpStatus.OK);
	}
	/** get opportunitySub entities By date **/
	@GetMapping("date/{LocalDate}")
	public ResponseEntity<List<OpportunitySub>> getOpportunitySub(@PathVariable("LocalDate") LocalDate Localdate)
	{
		logger.trace("Request received to fetch OpportunitySub based on LocalDate: {}", Localdate);
		if(Localdate==null)
		{
			logger.error("date should not be null");
			throw new InvalidInput("Please enter valid date");
		}
		  logger.debug("Fetching OpportunitySubs for LocalDate: {}", Localdate);
		List<OpportunitySub> opportunitySubFromDatabase=opportunitySubServcie.getOpportunityByDate(Localdate);
		
		logger.info("Succesffully retirved all opportunity Sub based on LocalDate : {}",Localdate);
		 logger.debug("OpportunitySubs data: {}", opportunitySubFromDatabase);
		return new ResponseEntity<List<OpportunitySub>>(opportunitySubFromDatabase,
				HttpStatus.OK);
	}
	/**
	 * Responsible for Updating endPoint Opportunities
	 * 
	 * @PUTMapping is the responsible for handling HTTP Put Request
	 * @return all the updated Opportunities
	 */
	@PutMapping("{id}/{opportunity_id}")
	public ResponseEntity<OpportunitySub> updatebyId(@PathVariable("id") String id,
			@RequestBody OpportunitySub opportunitysub, @PathVariable String opportunity_id)
	{
		logger.info("request recived to update opportunity based on id and opportunity id");
		if(id==null ||id.equals(" ")|| opportunity_id==null|| opportunity_id.equals(" "))
		{
			logger.error("id and opportunity i should not be null");
			throw new InvalidInput("Please eneter valid id and opportunity id");
		}
		logger.debug("Updating OpportunitySub for id: {} and opportunity_id: {}", id, opportunity_id);
		OpportunitySub opportunitySubFromDatabase=opportunitySubServcie.updateOpportunitySub(opportunitysub, opportunity_id, opportunity_id);
		logger.info("Succesfully updated opportunity sub entities based on id and opportunity id");
		return new ResponseEntity<OpportunitySub>(
				opportunitySubFromDatabase, HttpStatus.OK);
	}
	
	/** update opportunitySubid by opportunity id **/
	@PutMapping("update/{opportunitySub_id}/{opportunity_id}")
	public ResponseEntity<OpportunitySub> updateByOpportunityId(
			@PathVariable("opportunitySub_id") String opportunitySub_id,
			@PathVariable("opportunity_id") String opportunity_id) {
		 logger.trace("Request received to update OpportunitySub by opportunitySub_id '{}' and opportunity_id '{}'", opportunitySub_id, opportunity_id);

	        if (opportunity_id == null || opportunity_id.trim().isEmpty() || opportunitySub_id == null || opportunitySub_id.trim().isEmpty()) {
	            logger.error("opportunity_id and opportunitySub_id should not be null or empty");
	            throw new InvalidInput("Please enter valid opportunity_id and opportunitySub_id");
	        }
	        logger.debug("Updating OpportunitySub for opportunitySub_id: {} and opportunity_id: {}", opportunitySub_id, opportunity_id);
		   OpportunitySub opportunitySubFromDatabase=opportunitySubServcie.updateOpportunitySubByOpportunityId(opportunitySub_id, opportunity_id);
		   logger.info("Succesfully updated opportunity Sub by opoortunitySub id and pportunity id");
		 		return new ResponseEntity<>(
				opportunitySubFromDatabase,
				HttpStatus.OK);
	}
	/** this is the controller to execute opportunity sub entities**/
	@PutMapping("latestUpdate/{opportunitySubId}/{opportunityId}")
    public  ResponseEntity< OpportunitySub>updateOpportunitySubEntity(@RequestBody OpportunitySub opportunitySub,
                                                     @PathVariable("opportunitySubId") String opportunitySubId,
                                                     @PathVariable("opportunityId") String opportunityId) 
    {
		logger.trace("Request received to update OpportunitySub and Opportunity based on opportunitySubId '{}' and opportunityId '{}'", opportunitySubId, opportunityId);
		if (opportunitySubId == null || opportunitySubId.trim().isEmpty() || opportunityId == null || opportunityId.trim().isEmpty()) {
			logger.error("OpportunitySubId and opportunityId should not be null or empty");
			throw new InvalidInput("Please enter valid OpportunitySubId and opportunityId");
			}
		logger.debug("Updating OpportunitySub with id: {} and associated Opportunity with id: {}", opportunitySubId, opportunityId);
		OpportunitySub opportunitySubFromDatabase=opportunitySubServcie.updateOpportunitySubEntity(opportunitySub, opportunitySubId, opportunityId);
		logger.info("Successfully updated OpportunitySub with id: {} and associated Opportunity with id: {}", opportunitySubId, opportunityId);
		return new ResponseEntity<OpportunitySub>(opportunitySubFromDatabase,HttpStatus.OK);
    }
//	/** trail and error API for updating of opportunity sub **/
//	@PutMapping("updating/{opportunitySub_id}/id/{opportunity_id}")
//	public ResponseEntity<OpportunitySub> updateOpportunitySubandOpportunityId(
//			@PathVariable("opportunitySub_id") OpportunitySub opportunitySub_id, @PathVariable("id") String id,
//			@PathVariable("opportunity_id") String opportunity_id) {
//		return new ResponseEntity<OpportunitySub>(opportunitySubServcie
//				.updateOpportunitySubWithOpportunityIdAndOpprtunitySub(opportunitySub_id, id, opportunity_id),
//				HttpStatus.OK);
//	}

	@DeleteMapping("deleteOpportunitySub/id")
	public ResponseEntity<String> deleteOpportuntiySub(@PathVariable ("id") String id)
	{
		opportunitySubServcie.deleteOpportunitySub(id);
		logger.info("Opportunity SUb deleted Successfully");
		return new ResponseEntity<String>("Opportunity Sub Deleted",HttpStatus.OK);
	}
	
	@GetMapping("getAllOpportunitySubByOpportunity/{opportunity_id}")
	public ResponseEntity<List<OpportunitySub>> getOpportunitySubByOpportunityId(@PathVariable ("opportunity_id") String opportunity_id)
	{
		logger.trace("Request to fetch all the opportunity Sub by opportunity id",opportunity_id);
		if(opportunity_id==null ||opportunity_id.equals(" "))
		{
			throw new InvalidInput("Please enter opportunity_id");
		}
		List<OpportunitySub> opportunitySubByOpportunity=opportunitySubServcie.getAllOpportuntiySubByOpportunityId(opportunity_id);
		if(!opportunitySubByOpportunity.isEmpty())
		{
			logger.info("Sucessfully retrived all the opportunitySub based on opportunity_Id");
			return new ResponseEntity<>(opportunitySubByOpportunity,HttpStatus.OK);
		}
		else
		{
			return ResponseEntity.notFound().build();
		}
	}
}
