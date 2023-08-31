package com.kloc.crm.Service;

import java.util.List;
import com.kloc.crm.Entity.VendorPartner;


public interface VendorPartnerService{
	/**
     * Creates a new VendorPartner entity in the CRM system.
     * 
     * @param vendorpartner The VendorPartner object to be created.
     * @return The created VendorPartner object.
     */
	VendorPartner createVendorPartner(VendorPartner vendorpartner);
	
	/**
     * Retrieves a list of all VendorPartner entities from the CRM system.
     * 
     * @return A list of VendorPartner objects.
     */
	List<VendorPartner> GetAllVendorPartner();
	
	/**
     * Retrieves a specific VendorPartner entity by its unique VendorPartnerId.
     * 
     * @param VendorPartnerId The unique identifier of the VendorPartner.
     * @return The VendorPartner object if found, or null if not found.
     */
	VendorPartner GetvendorByvendorId(String VendorPartnerId);
	
	 /**
     * Updates an existing VendorPartner entity in the CRM system.
     * 
     * @param vendorpartner The VendorPartner object with updated information.
     * @param VendorPartnerId The unique identifier of the VendorPartner to be updated.
     * @return The updated VendorPartner object.
     */
	VendorPartner updateVendorpartner(VendorPartner vendorpartner,String VendorPartnerId);
	
	
	/**
     * Deletes a VendorPartner entity from the CRM system.
     * 
     * @param VendorPartnerId The unique identifier of the VendorPartner to be deleted.
     * @return The deleted VendorPartner object if deletion was successful, or null if not found.
     */
	VendorPartner deleteVendorpartner(String VendorPartnerId);
	
	
	
	
	
	
	
}