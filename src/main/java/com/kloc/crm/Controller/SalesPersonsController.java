/**
 * @Author :Avinash
 * @File_Name:SalesPersonsController.java
 * @Date:06-Jul-2023
 */
package com.kloc.crm.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kloc.crm.Entity.SalesPerson;
import com.kloc.crm.Service.SalesPersonService;


@RestController
@RequestMapping("/app")
@CrossOrigin("*")
/**
 * Controller class for managing SalesPerson entities.
 */
public class SalesPersonsController {
    
    // Service for handling SalesPerson-related operations
    @Autowired
    private SalesPersonService salesPersonService;
    
    /**
     * Endpoint for creating a new SalesPerson.
     *
     * @param sp       The SalesPerson object to be saved
     * @param user_id  The ID of the associated User
     * @return         The created SalesPerson object with HTTP status OK
     */
    @PostMapping("/saveSalesPerson/{user_id}")
    public ResponseEntity<SalesPerson> saveSalesPerson(@RequestBody SalesPerson sp, @PathVariable("user_id") String user_id) {
        return new ResponseEntity<>(salesPersonService.saveSalesPerson(sp, user_id), HttpStatus.OK);
    }
    
    /**
     * Endpoint for retrieving all SalesPersons.
     *
     * @return  A list of all SalesPerson objects
     */
    @GetMapping("/getAllSalesPerson")
    public List<SalesPerson> getAllSalesMan() {
        return salesPersonService.getAllSalesPersons();
    }
    
    /**
     * Endpoint for retrieving a SalesPerson by their ID.
     *
     * @param id  The ID of the SalesPerson to retrieve
     * @return    The SalesPerson object with the specified ID with HTTP status OK
     */
    @GetMapping("/getSalesPerson/{id}")
    public ResponseEntity<SalesPerson> getSalesPersonByTheirId(@PathVariable("id") String id) {
        return new ResponseEntity<>(salesPersonService.getSalePersonById(id), HttpStatus.OK);
    }
    
    /**
     * Endpoint for updating a SalesPerson.
     *
     * @param salesperson  The updated SalesPerson object
     * @param id           The ID of the SalesPerson to update
     * @return             The updated SalesPerson object with HTTP status CREATED
     */
    @PutMapping("/updateSalesperson/{id}")
    public ResponseEntity<SalesPerson> updateSalesman(@RequestBody SalesPerson salesperson, @PathVariable("id") String id) {
        return new ResponseEntity<>(salesPersonService.updateSalesPerson(salesperson, id), HttpStatus.CREATED);
    }
    
    @GetMapping("/getByTargets/{maxTarget}")
    public ResponseEntity<List<SalesPerson>> getAllSalesPersonsByTheirTargets(@PathVariable("maxTarget") int maxTarget){
    	return new ResponseEntity<List<SalesPerson>>(salesPersonService.getAllSalesPersonsByTarget(maxTarget),HttpStatus.OK);
    }
    
    @GetMapping("/getIdByEmail/{email}")
    public ResponseEntity<String> getSalesPersonIdByUsersEmail(@PathVariable("email") String email){
    	return new ResponseEntity<>(salesPersonService.getSalesPersonIdByEmail(email),HttpStatus.OK);
    	
    }
}

