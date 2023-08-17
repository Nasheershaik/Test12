package com.kloc.crm.Repository;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Mjilani
 * @createdDate : 06-07-2023
 * @fileName : OpportunitySubRepository
 * **/
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kloc.crm.Entity.Opportunity;
import com.kloc.crm.Entity.OpportunitySub;

/**
 * Repository interface for managing User entities.
 * Provides CRUD operations and custom query methods for accessing and manipulating User data.
 * This interface will take Entity and Data type of the primary key of an Entity class as an arguments
 */
@Repository
public interface OpportunitySubRepository extends JpaRepository<OpportunitySub,String>{

	List<OpportunitySub> findByOpportunityId(Opportunity opportunityId);}
