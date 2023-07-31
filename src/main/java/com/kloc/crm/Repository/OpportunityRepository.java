package com.kloc.crm.Repository;
import java.util.Optional;

/**
 * @author Mjilani
 * @createdDate : 06-07-2023
 * @fileName : OpportunityRepository.java
 * **/
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kloc.crm.Entity.Contact;
import com.kloc.crm.Entity.Opportunity;

/**
 * Repository interface for managing User entities.
 * Provides CRUD operations and custom query methods for accessing and manipulating User data.
 */
@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity,String>
{
	
	/**
	 * This interface will take Entity and Data type of the primary key of an Entity class as an arguments
	 * 
	 */
}


