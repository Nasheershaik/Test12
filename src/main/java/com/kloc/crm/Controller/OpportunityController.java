/**
 *@date : 06-July-2023
 * @fileName : OpportunityController.java
 *This Controller class  is responsible for handling incoming HTTP requests,
   processing the necessary logic, and generating appropriate responses.
 *@RestController is the combination of both @controller and @RequestBody  also its responsible
 *for building restful API's.
 */
package com.kloc.crm.Controller;
import java.time.LocalDate;
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

import com.kloc.crm.Entity.Customer;
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
	@PostMapping("save/{contactSub_Id}")//to handle post http request
	public ResponseEntity<Opportunity> saveOpportunity(@RequestBody Opportunity opportunity,@PathVariable String contactSub_Id)
	{
		logger.trace("Request recived to save opportunity with respective to the contact");
		if(contactSub_Id==null || contactSub_Id.equals(" "))
			{
		      logger.warn("Contact_id should not be null");
		      throw new  InvalidInput("Pleas enter valid contact id");
			}
		Opportunity opportunityFromDatabase=oppartunityService.saveOppartunity(opportunity, contactSub_Id);
		logger.info("Successfully saved opportunity based on contact_id");
	 return new ResponseEntity<>( opportunityFromDatabase,HttpStatus.OK);	
	}
	
	/**create opportunity based on contact and offering**/
   @PostMapping("saveOpportunity/{contactSub_Id}/{offering_id}")
	public ResponseEntity<Opportunity> saveOpportunities(@RequestBody Opportunity opportunity,@PathVariable String contactSub_Id,@PathVariable String offering_id)
	{
	   logger.trace("Request to save opportunity based on contact-id: {} and offering_id: {}", contactSub_Id, offering_id);
      if(contactSub_Id==null || contactSub_Id.equals(" ")||offering_id==null || offering_id.equals(" "))
		{
	      logger.warn("offering id or  Contact_id should not be null");
	      throw new  InvalidInput("Please  enter valid contact id and offering id");
		}
      Opportunity OpportunityFromdata=oppartunityService.saveOpportunities(opportunity, contactSub_Id, offering_id);
      logger.info("Successfully saved opportunity based on contact_id and offering id:{}",contactSub_Id,offering_id);
	 return new ResponseEntity<>( OpportunityFromdata,HttpStatus.OK);	
	}
  /**Responsible for Retrieving endPoint Opportunities 
   * @GetMapping is the responsible for handling HTTP GET Request
   * @return all the Opportunities
   */
	@GetMapping("getAllOpportunities") // to fetch all data
	@ResponseStatus(HttpStatus.OK)
	public List<Opportunity> getAllOpportunityFromEntity()
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
	
	
	@GetMapping("/getOportunity/{id}")
	public ResponseEntity<Opportunity> getOpportunityFromOpportunityId(@PathVariable("id") String id) {
	    logger.trace("Request to fetch opportunity by id: {}", id);

	    if (id == null || id.isEmpty()) {
	        logger.warn("id should not be null or empty");
	        throw new InvalidInput("Please enter a valid opportunity id to retrieve the data");
	    }

	    Opportunity opportunityFromDatabase = oppartunityService.getOpportunitybyId(id);

	    if (opportunityFromDatabase != null) {
	        logger.info("Retrieved opportunity based on id: {}", id);
	        return new ResponseEntity<>(opportunityFromDatabase, HttpStatus.OK);
	    } else {
	        logger.warn("Opportunity not found for provided opportunity id: {}", id);
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}
	
	
	
	
	/**get all opportunities based on contact id**/
	@GetMapping("getopportunityByContactSubId/{contactSub_id}")
	public ResponseEntity<List<Opportunity>>getOpportunitiesFromContactSubId(@PathVariable String contactSub_id)
	{
		 logger.trace("Request to fetch opportunities based on contact id: {}", contactSub_id);
		if(contactSub_id==null || contactSub_id.equals(" "))
		{
			logger.debug("Contact id sholuld not be null");
			throw new InvalidInput("Please enter valid contact Id");
		}
		List<Opportunity> opportunityFromdatabase = oppartunityService.getOpportunityByContactSubId(contactSub_id);
		logger.info("Retrieved opportunities based on contact id: {}", contactSub_id);
	 return new ResponseEntity<>(opportunityFromdatabase,HttpStatus.OK);
	}
	
	
	@GetMapping("/getAllOpportunitesByType/{status_type}")
	public ResponseEntity<List<Opportunity>> getAllOpportunitesFromTypesOfOpportunity(@PathVariable String  status_type)
	{
		logger.trace("Request to fecth all opportunties based on Status_type");
		if(status_type ==null || status_type.equals(""))
		{
			logger.debug("Status_type should not br null");
			throw new InvalidInput("Please enter valid status type");
		}
		List<Opportunity> oppportunityFromStatusType=oppartunityService.getAllOpportunityByType(status_type);
		logger.info("Retrived all opportunites based on opportunitytype");
		return new ResponseEntity<List<Opportunity>>(oppportunityFromStatusType,HttpStatus.OK);
	}
	
	/**Responsible for Updating endPoint Opportunities 
	 * @PUTMapping is the responsible for handling HTTP Put Request
	 * @return all the updated  Opportunities
	 */
  	@PutMapping("{id}/{contactSub_id}/{offering_id}")
	public ResponseEntity<Opportunity> updatebyId(@RequestBody Opportunity opportunity,@PathVariable("id") String id,@PathVariable String contactSub_id,@PathVariable String offering_id)
	{
  		 logger.trace("Request to update the opportunity based on opportunity_id: {}, contact_id: {}, and offering_id: {}", id, contactSub_id, offering_id);
  	  if(contactSub_id==null || contactSub_id.equals(" ")||offering_id==null || offering_id.equals(" ")||offering_id==null ||offering_id.equals(" "))
  			{
  		      logger.warn("offering id,Contact_id  and opportunity id should not be null");
  		      throw new  InvalidInput("Please  enter valid contact id opportunityId and offering id");
  			}
  	      Opportunity opportunitiesfromdatabase=oppartunityService.updateOpportunity(opportunity, id,contactSub_id,offering_id);
  	    logger.info("Successfully updated the opportunity based on opportunity_id: {}, contact_id: {}, and offering_id: {}", id, contactSub_id, offering_id);
  	  return new ResponseEntity<>(opportunitiesfromdatabase,HttpStatus.OK)	;
	}
  	
	/**update contact id based on opportunity id**/
	@PutMapping("updateContactIdBased/{opportunity_id}/{contactSub_id}")
	public ResponseEntity<Opportunity> updateByContactId(@PathVariable("opportunity_id") String opportunity_id,@PathVariable("contactSub_id") String contactSub_id,
			 @RequestBody Opportunity opportunity)
	{
		logger.trace("Request to update opportunity based on opportunity id and contact id:{}opportunityid :{}contact_id",opportunity_id,contactSub_id);
		if(opportunity_id==null || opportunity_id.equals(" ")|| contactSub_id==null ||contactSub_id.equals(" "))
		{
			logger.warn("contact id and opportunity id should not be null");
			throw new InvalidInput("Please enter valid opportunity id and contact id");
		}
		  Opportunity opportunityfromdatabase=oppartunityService.updateOpportunityByContactSubId(opportunity,opportunity_id, contactSub_id);
		  
		  if (opportunityfromdatabase != null) {
		logger.info("Succesfully updated opportunity  absed on id and contact_id:{}",opportunity_id,contactSub_id);
		return new ResponseEntity<Opportunity>(opportunityfromdatabase,HttpStatus.OK);
		 }
		  else
		  {
			  logger.warn("Opportunity not found for provided opportunity id and contact id");
	            return new ResponseEntity<>(HttpStatus.OK);  
		  }
	 }
	/**Responsible for deleting endPoint Opportunities based on id,if id not found through RunTime exception
	 * @DeleteMapping is the responsible for handling HTTP DELETE Request
	 * @return all the  Opportunities except deleted
	 */	
	@DeleteMapping("deleteOpportunityAndSub/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") String id)
	{
		//to delete from database
		oppartunityService.deleteOpportunity(id);
		logger.info("Opportunites deleted successfully");
		return new ResponseEntity<String>("Opprtunity deleted successfully",HttpStatus.OK);
  }
	/**Responsible for Retriving endPoint Opportunities based on date range and status type of opportunity
	 * @GetMapping is the responsible for handling HTTP DELETE Request
	 * @return all the  Opportunities based on date 
	 */	
	@GetMapping("/getAllOpportuntiesByDate/{fromdate}/{toDate}/{opportunity_type}")
	public ResponseEntity<List<Opportunity>> getAllOpportunityFromDates(@PathVariable LocalDate fromdate,@PathVariable LocalDate toDate,
			@PathVariable String opportunity_type)
	{
		logger.trace("Request to Retrive all  opportunities based on date range and status type:{}fromdate,{}toDate,{}opportunity_type",fromdate,toDate,opportunity_type);
		if(opportunity_type==null || opportunity_type.equals(""))
		{
			logger.warn("opportunity_type Should not be null please enter Valid opportuntiy_type");
			throw new InvalidInput("Please enter Valid Opportunity_type");
		}
		List<Opportunity> opportuntiesFromDataBase=oppartunityService.getAllOpportunityByDate(fromdate, toDate,opportunity_type);
		logger.info("Succesfully retrived  all  opportunities based on date range and status type:{}fromdate,{}toDate,{}opportunity_type",fromdate,toDate,opportunity_type);
		return new ResponseEntity<List<Opportunity>>(opportuntiesFromDataBase,HttpStatus.OK);
	}
	
	/**Responsible for Retriving endPoint Opportunities of Specific customer based on contact_id
	 * @GetMapping is the responsible for handling HTTP DELETE Request
	 * @return all the  Opportunities for Particular customer
	 */	
	  @GetMapping("getAllopportuntiesByCustomer/{contact_id}")
	 public ResponseEntity<List<Opportunity>> getAllOppportunitesOfSpecificCustomer(Customer customer,@PathVariable String contact_id)
	 {
		  logger.trace("Request to Retrive all  opportunities for particular customer based on contact_id:{}contact_id:",contact_id);
		  if(contact_id==null || contact_id.equals(""))
		  {
			  logger.warn("contact_id should not be null");
		  }
		 List<Opportunity> getAllopportunitesByCustomer=oppartunityService.getAllOpportuntiesByCustomer(customer, contact_id);
		 logger.info("Retrived All opportunites for particular customer from Specific contact-id");
		 return new ResponseEntity<List<Opportunity>>(getAllopportunitesByCustomer,HttpStatus.OK);
	 }

	  @GetMapping("getAllOpportunitesOfCustomer/{customer_id}")
	  public ResponseEntity<List<Opportunity>> getAllOportunitesFromCustomerId(@PathVariable String customer_id)
	  {
		  if(customer_id==null || customer_id.equals(" "))
		  {
			  logger.warn("Please enter Valid Cyustomer id");
		  }
		  List<Opportunity> listOfOpportuntiesOfCustomer=oppartunityService.getAllOpportunitesByCustomerId(customer_id);
		  return new ResponseEntity<List<Opportunity>>(listOfOpportuntiesOfCustomer,HttpStatus.OK);
	  }
	  
}
	  

