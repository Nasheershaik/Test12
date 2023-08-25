/**
 * @Author :Avinash
 * @File_Name:SalesPersonImpl.java
 * @Date:05-Jul-2023
 */
package com.kloc.crm.Service.ServiceImpl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kloc.crm.Entity.SalesPerson;
import com.kloc.crm.Entity.User;
import com.kloc.crm.Exception.DataNotFoundException;
import com.kloc.crm.Exception.InvalidInput;
import com.kloc.crm.Repository.SalesPersonRepository;
import com.kloc.crm.Repository.UserRepository;
import com.kloc.crm.Service.SalesPersonService;
import com.kloc.crm.Service.UserService;


@Service
/**
 * Implementation of the SalesPersonService i
		if (sp.get)nterface for managing SalesPerson entities.	
 */
public class SalesPersonImpl implements SalesPersonService {
    
    // Repository for performing database operations on SalesPerson entities
    @Autowired
    private SalesPersonRepository salesPersonRepository;
    
    // Repository for performing database operations on User entities
    @Autowired
    private UserRepository userRepository;
    
//    Create a logger using Log4j 2.x
 	private static final Logger logger = LogManager.getLogger(SalesPersonService.class);
    
    /**
     * Saves a new SalesPerson associated with a User.
     *
     * @param sp       The SalesPerson object to be saved
     * @param userId   The ID of the associated User
     * @return         The saved SalesPerson object
     * @throws DataNotFoundException if the User with the specified ID is not found
     */
    @Override
    public SalesPerson saveSalesPerson(SalesPerson sp, String userId)
    {
    	 try {
    	        logger.info("Saving salesperson for user ID: {}", userId);

    	        if (userId == null || userId.equals("")) {
    	            logger.error("Invalid input: Please provide a valid user ID");
    	            throw new InvalidInput("Please enter a valid user ID");
    	        }

    	        if (sp == null) {
    	            logger.error("Invalid input: Please provide salesperson details");
    	            throw new DataNotFoundException("Please enter salesperson details");
    	        }
    	        else 
    	        {
    	        	if (sp.getMaxTarget()<0) {
    	        		logger.error("Invalid input: Please enter a valid target");
    	        		throw new InvalidInput("Please enter a valid target");
    	        	}
    	        	
    	        	if (sp.getDuration().equals("")||sp.getDuration()==null) {
    	        		logger.error("Invalid input: Please enter a valid duration");
    	        		throw new InvalidInput("Please enter a valid duration");
    	        	}
    	        	
    	        	if (sp.getThreshold() <0) {
    	        		logger.error("Invalid input: Please enter a valid amount");
    	        		throw new InvalidInput("Please enter a valid amount");
    	        	}
    	        	
    	        	if (sp.getFrequency() <= 0) {
    	        		logger.error("Invalid input: Please enter a valid frequency");
    	        		throw new InvalidInput("Please enter a valid frequency");
    	        	}
    	        	
    	        	if (sp.getCurrency() == null || sp.getCurrency().equals("")) {
    	        		logger.error("Invalid input: Please enter a valid currency");
    	        		throw new InvalidInput("Please enter a valid currency");
    	        	}
    	        	
    	        	
    	        	User user = userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("User", "Userid", userId));
    	        	sp.setUser(user);
    	        	
    	        	logger.info("User saved.");
    	        	return salesPersonRepository.save(sp);
    	        }

    	    } catch (Exception e) {
    	        // Log the exception if any error occurs
    	        logger.error("Error saving salesperson for user ID {}: {}", userId, e.getMessage(), e);
    	        throw e;
    	    }
    }
    
    /**
     * Retrieves a list of all SalesPersons.
     *
     * @return  A list of all SalesPerson objects
     */
    @Override
    public List<SalesPerson> getAllSalesPersons()
    {
    	 try {
    	        logger.info("Getting all salespersons");

    	        List<SalesPerson> findAll = salesPersonRepository.findAll();
    	        if (findAll.isEmpty()) {
    	            throw new DataNotFoundException("No salespersons available");
    	        } else {
    	            // Log a success message
    	            logger.info("Retrieved all salespersons");

    	            return findAll;
    	        }
    	    } catch (Exception e) {
    	        // Log the exception if any error occurs
    	        logger.error("Error getting all salespersons: {}", e.getMessage(), e);
    	        throw e;
    	    }
    }
    
    /**
     * Retrieves a SalesPerson by their ID.
     *
     * @param id  The ID of the SalesPerson to retrieve
     * @return    The SalesPerson object with the specified ID
     * @throws DataNotFoundException if the SalesPerson with the specified ID is not found
     */
    @Override
    public SalesPerson getSalePersonById(String id)
    {
    	 try {
    	        logger.info("Getting salesperson by ID: {}", id);

    	        if (id == null || id.isEmpty()) {
    	            throw new InvalidInput("ID cannot be empty.");
    	        } else {
    	            SalesPerson salesPerson = salesPersonRepository.findById(id).orElseThrow(() -> new DataNotFoundException("SalesPerson", "id", id));

    	            // Log a success message
    	            logger.info("Retrieved salesperson by ID: {}", id);

    	            return salesPerson;
    	        }
    	    } catch (Exception e) {
    	        // Log the exception if any error occurs
    	        logger.error("Error getting salesperson by ID {}: {}", id, e.getMessage(), e);
    	        throw e;
    	    }
    }
    
    /**
     * Updates an existing SalesPerson.
     *
     * @param sp  The updated SalesPerson object
     * @param id  The ID of the SalesPerson to update
     * @return    The updated SalesPerson object
     * @throws DataNotFoundException if the SalesPerson with the specified ID is not found
     */
    @Override
    public SalesPerson updateSalesPerson(SalesPerson sp, String id)
    {
    	 try {
    	        logger.info("Updating salesperson with ID: {}", id);

    	        if (id == null || id.equals("")) {
    	            logger.error("Invalid input: Please provide a valid ID");
    	            throw new InvalidInput("Please enter a valid ID");
    	        }

    	        if (sp == null) {
    	            logger.error("Invalid input: Please provide salesperson details");
    	            throw new InvalidInput("Please enter salesperson details");
    	        }
    	        else 
    	        {
    	        	SalesPerson existingSp = salesPersonRepository.findById(id).orElseThrow(() -> new DataNotFoundException("SalesPerson", "id", id));
    	    		if (sp.getMaxTarget() >= 0)
    	    			existingSp.setMaxTarget(sp.getMaxTarget());
    	    		if (!sp.getDuration().equals("")|| sp.getDuration()!=null)
    	    			existingSp.setDuration(sp.getDuration());
    	    		if (sp.getThreshold()>= 0)
    	    			existingSp.setThreshold(sp.getThreshold());
    	    		if (sp.getFrequency() > 0)
    	    			existingSp.setFrequency(sp.getFrequency());
    	    		if (sp.getCurrency() != null && !sp.getCurrency().equals(""))
    	    			existingSp.setCurrency(sp.getCurrency());
    	        	logger.info("Sales person updated.");
    	        	return salesPersonRepository.save(existingSp);
    	        }

    	    } catch (Exception e) {
    	        // Log the exception if any error occurs
    	        logger.error("Error updating salesperson with ID {}: {}", id, e.getMessage(), e);
    	        throw e;
    	    }
    }

	@Override
	public List<SalesPerson> getAllSalesPersonsByTarget(int maxTarget)
	{
		 try {
		        logger.info("Getting all salespersons by target : {}", maxTarget);

		        if (maxTarget < 0) {
		            throw new InvalidInput("Please enter a valid Target.");
		        } else {
		            List<SalesPerson> findByTarget = salesPersonRepository.findAllByMaxTarget(maxTarget);

		            if (findByTarget.isEmpty()) {
		                throw new DataNotFoundException("No salesperson found with this target ID.");
		            } else {
		                // Log a success message
		                logger.info("Retrieved salespersons by target ID: {}", maxTarget);

		                return findByTarget;
		            }
		        }
		    } catch (Exception e) {
		        // Log the exception if any error occurs
		        logger.error("Error getting salespersons by target ID {}: {}", maxTarget, e.getMessage(), e);
		        throw e;
		    }
	}

	@Override
	public String getSalesPersonIdByEmail(String email)
	{
		 // Check if the email is empty or null
	    if (email == null || email.isEmpty()) {
	        logger.error("Invalid input: Please provide a valid email address");
	        throw new InvalidInput("Please provide a valid email address");
	    }

	    // Find the user by email
	    User user = userRepository.findByEmail(email);

	    // Check if the user exists
	    if (user == null) {
	        logger.warn("No user found with email: {}", email);
	        throw new DataNotFoundException("No user found with this email");
	    }

	    String userId = user.getUserId();
	    String salesPersonId = null;

	    // Find all salespersons
	    List<SalesPerson> salesPersons = salesPersonRepository.findAll();

	    // Check if the list of salespersons is empty
	    if (salesPersons.isEmpty()) {
	        logger.warn("No salespersons available in SalesPerson table");
	        throw new DataNotFoundException("No salespersons available in SalesPerson table");
	    }

	    // Iterate through the salespersons to find a match based on user ID
	    for(SalesPerson sp:salesPersons) {
			if(sp.getUser().getUserId().equals(userId)) {
				salesPersonId	 = sp.getSalespersonId();
				System.out.println(salesPersonId);
//				if( salesPersonId.equals(""))
//					throw new InvalidInput("No SalesPerson has been added to salesperson table");
			}
	  
		}

	    // Check if a salesperson ID was found
	    if (salesPersonId == null) {
	        logger.warn("No salesperson available with email ID: {}", email);
	        throw new DataNotFoundException("No salesperson available with this email ID in SalesPerson table. Please add the corresponding salesperson.");
	    }

	    logger.info("Salesperson ID found: {}", salesPersonId);
	    return salesPersonId;
	
	}
	
	
}
