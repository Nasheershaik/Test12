/**
 *@date : 06-July-2023
 * @fileName : OpportunityController.java
 *This Controller class  is responsible for handling incoming HTTP requests,
   processing the necessary logic, and generating appropriate responses.
 *@RestController is the combination of both @controller and @RequestBody  also its responsible
 *for building restful API's.
 */
package com.kloc.crm.Controller;
import java.util.List;
import org.antlr.v4.runtime.atn.SemanticContext.AND;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
import com.kloc.crm.Exception.InvalidInput;
import com.kloc.crm.Service.OpportunityService;

@RestController
@CrossOrigin("*")
@RequestMapping("/app")
public class OpportunityController
{
	@Autowired
	private OpportunityService oppartunityService;
    
	private static final Logger logger = LogManager.getLogger(OpportunityController.class.getName());
	/**
	 * @param oppartunityService 
	 * constructor injection
	 * we can use @Autowired also if we used setter injection
	 */
	public OpportunityController(OpportunityService oppartunityService)
	{
		super();
		this.oppartunityService = oppartunityService;
	}
	
	/** Responsible for Creating EndPoint Opportunities
	 * @PostMapping is the responsible for handling HTTP Post request
	 * @return Newly Created Opportunity
	 */
	@PostMapping("save/{contact_Id}")//to handle post http request
	public ResponseEntity<Opportunity> saveOpportunity(@RequestBody Opportunity opportunity,@PathVariable String contact_Id)
	{
		logger.trace("Request recived to save opportunity with respective to the contact");
		if(contact_Id==null || contact_Id.equals(" "))
			{
		      logger.warn("Contact_id should not be null");
		      throw new  InvalidInput("Pleas enter valid contact id");
			}
		Opportunity opportunityFromDatabase=oppartunityService.saveOppartunity(opportunity, contact_Id);
		logger.info("Successfully saved opportunity based on contact_id");
	 return new ResponseEntity<>( opportunityFromDatabase,HttpStatus.CREATED);	
	}

	
	/**create opportunity based on contact and offering**/
   @PostMapping("saveOpportunity/{contact_Id}/{offering_id}")
	public ResponseEntity<Opportunity> saveOpportunities(@RequestBody Opportunity opportunity,@PathVariable String contact_Id,@PathVariable String offering_id)
	{
	   logger.trace("Request to save opportunity based on contact-id: {} and offering_id: {}", contact_Id, offering_id);
      if(contact_Id==null || contact_Id.equals(" ")||offering_id==null || offering_id.equals(" "))
		{
	      logger.warn("offering id or  Contact_id should not be null");
	      throw new  InvalidInput("Please  enter valid contact id and offering id");
		}
      
      Opportunity OpportunityFromdata=oppartunityService.saveOpportunities(opportunity, contact_Id, offering_id);
      logger.info("Successfully saved opportunity based on contact_id and offering id:{}",contact_Id,offering_id);
	 return new ResponseEntity<>( OpportunityFromdata,HttpStatus.CREATED);	
	}
   
   
  /**Responsible for Retrieving endPoint Opportunities 
   * @GetMapping is the responsible for handling HTTP GET Request
   * @return all the Opportunities
   */
	@GetMapping("/opportunties") // to fetch all data
	@ResponseStatus(HttpStatus.OK)
	public List<Opportunity> getAllOpportunities()
	{
		logger.trace("Request to fetch all the opportunities");
		List<Opportunity> opportunities=oppartunityService.getAllOppartunities();
		logger.info("Successfully retrived all opportunities");
		return  opportunities;
	}
	
	  /**Responsible for Retrieving endPoint Opportunities based on id
	   * @GetMapping is the responsible for handling HTTP GET Request.
	   * @return all the Opportunities based on id.
	   */
	@GetMapping("{id}") 
	public ResponseEntity<Opportunity> getOpportunityById(@PathVariable("id") String id)
	{
		logger.trace("Request to fetch all opportunities based on opportunity id:{} id",id);
		if(id==null || id.equals(""))
		{
			logger.warn("id should  not be null");
			throw new InvalidInput("Please enter valid opportunity id to retrive the data");
		}
		Opportunity opportunityFromDatabase=oppartunityService.getOpportunitybyId(id);
		 
		if(opportunityFromDatabase!=null)
		{
		logger.info("Retrieved opportunity based on id: {}", id);
		return new ResponseEntity<>( opportunityFromDatabase,HttpStatus.OK);
		}
		else
		{
			 logger.warn("Opportunity not found for provided opportunity id and contact id");
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  
		}
	}
	
	
	/**get all opportunities based on contact id**/
	@GetMapping("getopportunityByContactId/{contact_id}")
	public ResponseEntity<List<Opportunity>>getOpportunityByContactId(@PathVariable String contact_id)
	{
		 logger.trace("Request to fetch opportunities based on contact id: {}", contact_id);
		if(contact_id==null || contact_id.equals(" "))
		{
			logger.debug("Contact id sholuld not be null");
			throw new InvalidInput("Please enter valid contact Id");
		}
		List<Opportunity> opportunityFromdatabase = oppartunityService.getOpportunityByContactId(contact_id);
		logger.info("Retrieved opportunities based on contact id: {}", contact_id);
	 return new ResponseEntity<>(opportunityFromdatabase,HttpStatus.OK);
	}
	
	
	/**Responsible for Updating endPoint Opportunities 
	 * @PUTMapping is the responsible for handling HTTP Put Request
	 * @return all the updated  Opportunities
	 */
  	@PutMapping("{id}/{contact_id}/{offering_id}")
	public ResponseEntity<Opportunity> updatebyId(@RequestBody Opportunity opportunity,@PathVariable("id") String id,@PathVariable String contact_id,@PathVariable String offering_id)
	{
  		 logger.trace("Request to update the opportunity based on opportunity_id: {}, contact_id: {}, and offering_id: {}", id, contact_id, offering_id);
  	  if(contact_id==null || contact_id.equals(" ")||offering_id==null || offering_id.equals(" ")||offering_id==null ||offering_id.equals(" "))
  			{
  		      logger.warn("offering id,Contact_id  and opportunity id should not be null");
  		      throw new  InvalidInput("Please  enter valid contact id opportunityId and offering id");
  			}
  	      Opportunity opportunitiesfromdatabase=oppartunityService.updateOpportunity(opportunity, id,contact_id,offering_id);
  	    logger.info("Successfully updated the opportunity based on opportunity_id: {}, contact_id: {}, and offering_id: {}", id, contact_id, offering_id);
  	  return new ResponseEntity<>(opportunitiesfromdatabase,HttpStatus.OK)	;
	}
  	
  	
	/**update contact id based on opportunity id**/
	@PutMapping("updateContactIdBased/{opportunity_id}/{contact_id}")
	public ResponseEntity<Opportunity> updateByContactId(@PathVariable("opportunity_id") String opportunity_id,@PathVariable("contact_id") String contact_id)
	{
		logger.trace("Request to update opportunity based on opportunity id and contact id:{}opportunityid :{}contact_id",opportunity_id,contact_id);
		if(opportunity_id==null || opportunity_id.equals(" ")|| contact_id==null ||contact_id.equals(" "))
		{
			logger.warn("contact id and opportunity id should not be null");
			throw new InvalidInput("Please enter valid opportunity id and contact id");
		}
		  Opportunity opportunityfromdatabase=oppartunityService.updateOpportunityByContactId(opportunity_id, contact_id);
		  
		  if (opportunityfromdatabase != null) {
		logger.info("Succesfully updated opportunity  absed on id and contact_id:{}",opportunity_id,contact_id);
		return new ResponseEntity<Opportunity>(opportunityfromdatabase,HttpStatus.OK);
		 }
		  else
		  {
			  logger.warn("Opportunity not found for provided opportunity id and contact id");
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  
		  }
	 }
	
	/**Responsible for deleting endPoint Opportunities based on id,if id not found through RunTime exception
	 * @DeleteMapping is the responsible for handling HTTP DELETE Request
	 * @return all the  Opportunities except deleted
	 */	
	@DeleteMapping("/opportunityid/{id}")
	public ResponseEntity<String> deleteOpportunity(@PathVariable("id") String id)
	{
		//to delete from database
		oppartunityService.deleteOpportunity(id);
		logger.info("Opportunites deleted successfully");
		return new ResponseEntity<String>("Opprtunity deleted successfully",HttpStatus.OK);
	}
}
