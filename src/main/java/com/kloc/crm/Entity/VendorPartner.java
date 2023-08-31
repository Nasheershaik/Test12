package com.kloc.crm.Entity;

import java.time.LocalDate;

/**
 * Represents a vendor or partner in the CRM system.
 * This class defines attributes and metadata for a vendor or partner entity.
 * 
 * @author Nasheer
 * @File_name Contact.java
 * @Created_Date 5/7/2023
 */
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Data;

@Data
@Entity
public class VendorPartner {
    // Primary key for the entity
    @Id
    // Generating strategy for the primary key using a custom generator
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "VendorPartnerIdGenerator")
    @GenericGenerator(name = "VendorPartnerIdGenerator",
            type = com.kloc.crm.Entity.IdGenerator.class,
            parameters = {
                @Parameter(name = IdGenerator.INCREMENT_PARAM, value = "1"),
                @Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "VendorPartner_"),
                @Parameter(name = IdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d")
            })
    @Column(name = "VendorPartnerId")
    private String VendorPartnerId; // Unique identifier for the vendor/partner

    private String firstName;       // First name of the vendor/partner
    private String lastName;        // Last name of the vendor/partner
    private String email;           // Email address of the vendor/partner
    private String company;         // Company name of the vendor/partner
    private String address;         // Address of the vendor/partner
    private String country;         // Country where the vendor/partner is located
    private String Designation;     // Designation/position of the vendor/partner
    private String Department;      // Department to which the vendor/partner belongs
    private LocalDate date;         // Date associated with the vendor/partner
    private long mobileNumber;      // Mobile number of the vendor/partner
    private String partnerSkills;   // Skills possessed by the vendor/partner
    private String PartnerType;     // Type of partner (e.g., technology, business)
    private String VendorType;      // Type of vendor (e.g., supplier, service provider)
    private String VendorDescription; // Description of the vendor
    private String PartnerDescription; // Description of the partner
    private String type;            // Type of entity (vendor or partner)
}
