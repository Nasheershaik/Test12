package com.kloc.crm.Entity;

import java.time.LocalDate;
/**
 * Represents a contact in the CRM system.
 * 
 * @author_name  : Ankush
 * @File_name	 : Contact.java
 * @Created_Date : 5/7/2023
 */
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
	/*
	 * This is the mapping of status table.
	 * from where we get status like new contacted depending on the life cycle of the contact.
	 */
	@ManyToOne
	@JsonBackReference("lifeCycleStage")
	@JoinColumn(name = "life_cycle_stage_id")
	private Status lifeCycleStage;
	private String firstName;
	private String lastName;
	private String email;
	private String company;
	private String address;
	private String country;
	@ManyToOne
	@JsonBackReference("source")
	@JoinColumn(name = "source_id")
	private Status source;
	private String otherSourcetype;
	private String websiteURL;
	private String contactDestination;
	private String contactDepartment;
	@ManyToOne
	@JsonBackReference("contactCreatedBy")
	@JoinColumn(name = "contact_created_by")
	private User contactCreatedBy;
	private LocalDate date;
	private LocalDate stageDate;
	private long mobileNumber;
}
