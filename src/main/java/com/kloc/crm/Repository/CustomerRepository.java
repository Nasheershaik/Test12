package com.kloc.crm.Repository;
/**
 * @author Mjilani
 * @createdDate : 06-07-2023
 * @fileName : CustomerRepository.java
 * **/
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kloc.crm.Entity.Customer;

import jakarta.persistence.Id;
/**
 * Repository interface for managing User entities.
 * Provides CRUD operations and custom query methods for accessing and manipulating User data.
 */

@Repository
public interface CustomerRepository  extends JpaRepository<Customer,String>
{

	/**
	 * This interface will take Entity and Data type of the primary key of an Entity class as an arguments
	 * 
	 */
}
