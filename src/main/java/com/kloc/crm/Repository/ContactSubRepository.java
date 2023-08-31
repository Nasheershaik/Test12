/**
 * @Author_name: AnkushJadhav
 * @Created_Date: 25/8/2023
 * @File_name: ContactRepository.java
 */
package com.kloc.crm.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.kloc.crm.Entity.ContactSub;

@Repository
public interface ContactSubRepository extends JpaRepository<ContactSub, String>
{
	
}