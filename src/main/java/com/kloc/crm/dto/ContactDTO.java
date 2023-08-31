/**
 * This class represents a Data Transfer Object (DTO) for a contact in the CRM system.
 * It is used to transfer contact-related information between different parts of the application.
 *
 * @Author_name: AnkushJadhav 
 * @File_Name: ContactDTO.java
 * @Created_Date: 3/8/2023
 */
package com.kloc.crm.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class ContactDTO
{
    // Unique identifier for the contact.
    private String contactId;
    
    // The lifecycle stage of the contact.
    private List<ContactSubDTO> lifeCycleStage;
    
    // The first name of the contact.
    private String firstName;
    
    // The last name of the contact.
    private String lastName;
    
    // The email address of the contact.
    private String email;
    
    // The company to which the contact belongs.
    private String company;
    
    // The address of the contact.
    private String address;
    
    // The country of the contact.
    private String country;
    
    // The source from which the contact was acquired.
    private String source;
    
    // The type of source when 'other' is selected.
    private String otherSourcetype;
    
    // The website URL associated with the contact.
    private String websiteURL;
    
    // The social media link associated with the contact.
    private String socialMediaLink;
    
    // The designation or job title of the contact.
    private String contactDesignation;
    
    // The department or division to which the contact belongs.
    private String contactDepartment;
    
    // The ID of the user who created the contact.
    private String contactCreatedBy;
    
    // The name of the user who created the contact.
    private String contactCreatedByName;
    
    // The email of the user who created the contact.
    private String contactCreatedByEmail;
    
    // The date when the contact was created.
    private LocalDate date;
    
    // The mobile number of the contact.
    private long mobileNumber;
}
