package com.kloc.crm.Repository;


/**
 * Repository interface for managing Contact entities in the CRM system.
 * 
 * @author_name  : Ankush
 * @File_name	 : ContactRepository.java
 * @Created_Date : 5/7/2023
 */

import java.util.List;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import com.kloc.crm.Entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, String>
{
List<Contact> findAllByCompanyIs(String company);
	
	
	
	List<Contact> findAllByStageDateIs(LocalDate stageDate);
	List<Contact> findAllByCountryIs(String country);
}