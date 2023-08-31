package com.kloc.crm.Controller;

import com.kloc.crm.Entity.VendorPartner;
import com.kloc.crm.Service.VendorPartnerService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/vendorpartners")
@CrossOrigin("*")
public class VendorPartnerController {
    
    // Logger for logging events in this class
    private static final Logger Log = LogManager.getLogger(VendorPartnerController.class);

    @Autowired
    private VendorPartnerService vendorPartnerService;

    // Create a new VendorPartner entity
    @PostMapping("/createvendorpartner")
    public ResponseEntity<VendorPartner> createVendorPartner(@RequestBody VendorPartner vendorpartner) {
        Log.info("Creating a new VendorPartner...");
        VendorPartner createdVendorPartner = vendorPartnerService.createVendorPartner(vendorpartner);
        Log.info("VendorPartner created successfully.");
        return new ResponseEntity<>(createdVendorPartner, HttpStatus.CREATED);
    }

    // Retrieve a list of all VendorPartner entities
    @GetMapping("/getallvendorpartner")
    public ResponseEntity<List<VendorPartner>> getAllVendorPartners() {
        Log.debug("Fetching all VendorPartners...");
        List<VendorPartner> vendorPartners = vendorPartnerService.GetAllVendorPartner();
        Log.debug("Fetched " + vendorPartners.size() + " VendorPartners.");
        return new ResponseEntity<>(vendorPartners, HttpStatus.OK);
    }

    // Retrieve a specific VendorPartner entity by its ID
    @GetMapping("/{id}")
    public ResponseEntity<VendorPartner> getVendorPartnerById(@PathVariable("id") String VendorPartnerId) {
        Log.debug("Fetching VendorPartner with ID: " + VendorPartnerId);
        VendorPartner vendorPartner = vendorPartnerService.GetvendorByvendorId(VendorPartnerId);
        Log.debug("Fetched VendorPartner: " + vendorPartner);
        return new ResponseEntity<>(vendorPartner, HttpStatus.OK);
    }

    // Update an existing VendorPartner entity
    @PutMapping("updateVendorPartner/{id}")
    public ResponseEntity<VendorPartner> updateVendorPartner(
            @PathVariable("id") String VendorPartnerId,
            @RequestBody VendorPartner vendorpartner) {
        Log.info("Updating VendorPartner with ID: " + VendorPartnerId);
        VendorPartner updatedVendorPartner = vendorPartnerService.updateVendorpartner(vendorpartner, VendorPartnerId);
        Log.info("VendorPartner updated successfully.");
        return new ResponseEntity<>(updatedVendorPartner, HttpStatus.OK);
    }

    // Delete a VendorPartner entity
    @DeleteMapping("deletevendorpartner/{id}")
    public ResponseEntity<VendorPartner> deleteVendorPartner(@PathVariable("id") String VendorPartnerId) {
        Log.warn("Deleting VendorPartner with ID: " + VendorPartnerId);
        VendorPartner deletedVendorPartner = vendorPartnerService.deleteVendorpartner(VendorPartnerId);
        Log.warn("VendorPartner deleted successfully.");
        return new ResponseEntity<>(deletedVendorPartner, HttpStatus.OK);
    }
}
