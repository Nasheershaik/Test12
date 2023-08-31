/**
 * Represents a contact in the CRM system.
 * 
 * @Author_name: AnkushJadhav
 * @File_name: Contact.java
 * @Created_Date: 5/7/2023
 */
package com.kloc.crm.Entity;

import java.time.LocalDate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Contact 
{
    // Unique identifier for the contact.
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contactIdGenerator")
    @GenericGenerator(name = "contactIdGenerator", 
        type =  com.kloc.crm.Entity.IdGenerator.class, 
        parameters = {
            @Parameter(name = IdGenerator.INCREMENT_PARAM, value = "1"),
            @Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "contact_"),
            @Parameter(name = IdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d")
    })
    private String contactId;

    // The life cycle stage of the contact, e.g., new contact.
//    @ManyToOne
//    @JsonBackReference("lifeCycleStage")
//    @JoinColumn(name = "life_cycle_stage_id")
//    private Status lifeCycleStage;

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
    @ManyToOne
    @JsonBackReference("source")
    @JoinColumn(name = "source_id")
    private Status source;

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

    // The user who created the contact.
    @ManyToOne
    @JsonBackReference("contactCreatedBy")
    @JoinColumn(name = "contact_created_by")
    private User contactCreatedBy;

    // The date when the contact was created.
    private LocalDate date;

    // The date representing the stage of the contact.
//    private LocalDate stageDate;

    // The mobile number of the contact.
    private long mobileNumber;
}
