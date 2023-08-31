package com.kloc.crm.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kloc.crm.Entity.VendorPartner;


/**
 * Repository interface for managing VendorPartner entities in the CRM system.
 * This interface extends JpaRepository to inherit basic CRUD operations for VendorPartner entities.
 * The first type parameter specifies the entity type, and the second type parameter specifies the type of the entity's primary key.
 * 
 * This interface doesn't require method declarations for basic CRUD operations, as JpaRepository provides them out of the box.
 * Additional custom queries can be added if needed.
 * 
 * @see JpaRepository
 * @see VendorPartner
 * @see String
 * @author Nasheer
 */
public interface VendorPartnerRepository extends JpaRepository<VendorPartner,String>{



	
	
}
