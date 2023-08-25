/**
 * Repository interface for managing Contact entities in the CRM system.
 * 
 * @Author_name: AnkushJadhsv
 * @File_name: ContactRepository.java
 * @Created_Date: 5/7/2023
 */
package com.kloc.crm.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.kloc.crm.Entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, String>
{
}