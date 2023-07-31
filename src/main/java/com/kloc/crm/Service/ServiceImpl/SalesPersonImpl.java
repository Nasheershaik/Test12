/**
 * @Author :Avinash
 * @File_Name:SalesPersonImpl.java
 * @Date:05-Jul-2023
 */
package com.kloc.crm.Service.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kloc.crm.Entity.SalesPerson;
import com.kloc.crm.Entity.User;
import com.kloc.crm.Exception.DataNotFoundException;
import com.kloc.crm.Exception.InvalidInput;
import com.kloc.crm.Repository.SalesPersonRepository;
import com.kloc.crm.Repository.UserRepository;
import com.kloc.crm.Service.SalesPersonService;


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
    	if (userId == null || userId.equals(""))
    		throw new InvalidInput("please enter user id");
    	if(sp == null)
    	{
    		throw new DataNotFoundException("please enter sales person details.");
    	}
    	else
    	{
    		if (sp.getTarget() <= 0)
				throw new InvalidInput("please enter valid target.");
    		if (sp.getDuration() <= 0)
				throw new InvalidInput("please enter valid duration.");
    		if (sp.getAmount() <= 0)
				throw new InvalidInput("please enter valid amount.");
    		if (sp.getFrequency() <= 0)
				throw new InvalidInput("please enter valid frequency.");
    		if (sp.getCurrency() == null || sp.getCurrency().equals(""))
				throw new InvalidInput("please enter currency");
    		
    		User user = userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("User", "Userid", userId));
    		sp.setUser(user);
    		System.out.println("saving user");
    	return	salesPersonRepository.save(sp);
    		
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
    	List<SalesPerson> findAll = salesPersonRepository.findAll();
    	if (findAll.isEmpty())
			throw new DataNotFoundException("No sales person available");
    	else
    		return findAll;
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
    	if(id == null || id.equals(""))
    		throw new InvalidInput("id cant be empty.");
    	else
    		return salesPersonRepository.findById(id).orElseThrow(() -> new DataNotFoundException("SalesPerson", "id", id));
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
    	if (id == null || id.equals(""))
    	{
    			throw new InvalidInput("please enter id.");
    	}
    	if (sp == null)
    		throw new InvalidInput("please enter sales person details.");
    	else
    	{
    		SalesPerson existingSp = salesPersonRepository.findById(id).orElseThrow(() -> new DataNotFoundException("SalesPerson", "id", id));
    		if (sp.getTarget() > 0)
    			existingSp.setTarget(sp.getTarget());
    		if (sp.getDuration() > 0)
    			existingSp.setDuration(sp.getDuration());
    		if (sp.getAmount() > 0)
    			existingSp.setAmount(sp.getAmount());
    		if (sp.getFrequency() > 0)
    			existingSp.setFrequency(sp.getFrequency());
    		if (sp.getCurrency() != null && !sp.getCurrency().equals(""))
    			existingSp.setCurrency(sp.getCurrency());
    		return salesPersonRepository.save(existingSp);
    	}
    }

	@Override
	public List<SalesPerson> getAllSalesPersonsByTarget(int id)
	{
		if (id <= 0)
			throw new InvalidInput("Please Enter valid id.");
		else 
		{
			List<SalesPerson> findByTarget = salesPersonRepository.findAllByTarget(id);
			if (findByTarget.isEmpty())
				throw new DataNotFoundException("sales person not found with this target.");
			else
				return findByTarget;
		}
	}
}
