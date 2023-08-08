/**
 * @Author :Avinash
 * @File_Name:SalesPersonService.java
 * @Date:05-Jul-2023
 */
package com.kloc.crm.Service;

import java.util.List;

import com.kloc.crm.Entity.SalesPerson;


/**
 * Interface for managing SalesPerson entities.
 */
public interface SalesPersonService {
    
    /**
     * Saves a new SalesPerson associated with a User.
     *
     * @param sp       The SalesPerson object to be saved
     * @param userId   The ID of the associated User
     * @return         The saved SalesPerson object
     */
    SalesPerson saveSalesPerson(SalesPerson sp, String userId);
    
    /**
     * Retrieves a list of all SalesPersons.
     *
     * @return  A list of all SalesPerson objects
     */
    List<SalesPerson> getAllSalesPersons();
    
    /**
     * Retrieves a SalesPerson by their ID.
     *
     * @param id  The ID of the SalesPerson to retrieve
     * @return    The SalesPerson object with the specified ID
     */
    
    List<SalesPerson> getAllSalesPersonsByTarget(int id);
    	
    
    SalesPerson getSalePersonById(String id);
    
    /**
     * Updates an existing SalesPerson.
     *
     * @param sp  The updated SalesPerson object
     * @param id  The ID of the SalesPerson to update
     * @return    The updated SalesPerson object
     */
    SalesPerson updateSalesPerson(SalesPerson sp, String id);
    
    
    String getSalesPersonIdByEmail(String email);
    
}

