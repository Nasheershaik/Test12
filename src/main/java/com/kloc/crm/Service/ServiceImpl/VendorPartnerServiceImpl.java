package com.kloc.crm.Service.ServiceImpl;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kloc.crm.Entity.VendorPartner;
import com.kloc.crm.Exception.DataNotFoundException;
import com.kloc.crm.Repository.VendorPartnerRepository;
import com.kloc.crm.Service.VendorPartnerService;
@Service
/**
 * Implementation of the VendorPartnerService interface providing functionality
 * to manage VendorPartner entities in the CRM system.
 * 
 * This class is responsible for handling the business logic related to VendorPartner entities.
 * It interacts with the VendorPartnerRepository to perform CRUD operations.
 * 
 * @author Nasheer
 */
public class VendorPartnerServiceImpl implements VendorPartnerService {
	
	
	@Autowired 
	private final VendorPartnerRepository vendorpartnerRepo;

	
	public VendorPartnerServiceImpl(VendorPartnerRepository vendorpartnerRepo) {
	    this.vendorpartnerRepo = vendorpartnerRepo;
	}

	@Override
	public VendorPartner createVendorPartner(VendorPartner vendorpartner) {
		// Set the creation date to the current date
		vendorpartner.setDate(LocalDate.now());
		 // Save the new VendorPartner entity using the repository
		return vendorpartnerRepo.save(vendorpartner);
	}
	

	@Override
	public List<VendorPartner> GetAllVendorPartner() {
		 // Retrieve a list of all VendorPartner entities using the repository
		return vendorpartnerRepo.findAll();
	}

	@Override
	public VendorPartner GetvendorByvendorId(String VendorPartnerId) {
		 // Retrieve a specific VendorPartner entity by its ID from the repository
		return vendorpartnerRepo.findById(VendorPartnerId).orElseThrow(()-> new DataNotFoundException("Enter Proper ID"));
	}

	@Override
	public VendorPartner updateVendorpartner(VendorPartner vendorpartner, String VendorPartnerId) {
	    VendorPartner vendorpart = vendorpartnerRepo.findById(VendorPartnerId).orElseThrow(() -> new DataNotFoundException("Enter proper ID"));
	    vendorpart.setFirstName(vendorpartner.getFirstName());
	    vendorpart.setLastName(vendorpartner.getLastName());
	    vendorpart.setEmail(vendorpartner.getEmail());
	    vendorpart.setCompany(vendorpartner.getCompany());
	    vendorpart.setAddress(vendorpartner.getAddress());
	    vendorpart.setCountry(vendorpartner.getCountry());
	    vendorpart.setMobileNumber(vendorpartner.getMobileNumber());
	  //  vendorpart.setType(vendorpartner.getType());
	    vendorpart.setPartnerSkills(vendorpartner.getPartnerSkills());
	    vendorpart.setPartnerType(vendorpartner.getPartnerType());
	    vendorpart.setVendorType(vendorpartner.getVendorType());
	    vendorpart.setDesignation(vendorpartner.getDesignation());
	    vendorpart.setDepartment(vendorpartner.getDepartment());
	    vendorpart.setVendorType(vendorpartner.getVendorType());
	    vendorpart.setVendorDescription(vendorpartner.getVendorDescription());
	    
	    return vendorpartnerRepo.save(vendorpart);
	}

	@Override
	public VendorPartner deleteVendorpartner(String VendorPartnerId) {
		VendorPartner vendorpartner = vendorpartnerRepo.findById(VendorPartnerId).orElseThrow(()-> new DataNotFoundException("Please Enter proper ID"));
		vendorpartnerRepo.delete(vendorpartner);
		return  vendorpartner;
		
		
		
	}

	
	
}