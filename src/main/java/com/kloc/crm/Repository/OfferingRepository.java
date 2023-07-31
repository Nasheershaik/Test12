package com.kloc.crm.Repository;


/**
 * Repository interface for managing Offering entities in the CRM system.
 * 
 * @author_name  : Ankush
 * @File_name	 : OfferingRepository.java
 * @Created_Date : 5/7/2023
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kloc.crm.Entity.Offering;

@Repository
public interface OfferingRepository extends JpaRepository<Offering, String>
{
}
