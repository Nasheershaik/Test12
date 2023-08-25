/**
 * Repository interface for managing Offering entities in the CRM system.
 * 
 * @Author_name  : AnkushJadhav
 * @File_name	 : OfferingRepository.java
 * @Created_Date : 5/7/2023
 */
package com.kloc.crm.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.kloc.crm.Entity.Offering;

@Repository
public interface OfferingRepository extends JpaRepository<Offering, String>
{
}
