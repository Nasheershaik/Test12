package com.kloc.crm.Entity;

import java.time.LocalDate;
/**
 * Represents an offering in the CRM system.
 * 
 * @author_name  : Ankush
 * @File_name	 : Offering.java
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
public class Offering 
{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offeringIdGenerator")
	@GenericGenerator(name = "offeringIdGenerator", 
			type =  com.kloc.crm.Entity.IdGenerator.class,
			parameters = {
			@Parameter(name = IdGenerator.INCREMENT_PARAM, value = "1"),
			@Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "offering_"),
			@Parameter(name = IdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d")
	})
	private String offeringId;
	/*
	 * this is mapping to the status table.
	 * in this we get offering category for example Fixed Bid Project or Variable Bid Project.
	 */
	@ManyToOne
	@JsonBackReference("offeringCategory")
	@JoinColumn(name = "offering_category")
	private Status offeringCategory;
	private String offeringName;
	private long CTC;
	private long projectCost;
	private long actualCost;
	private String costType;
	
	/*
	 * this is mapping to the status table
	 * in status table we get offering type for example product and service.
	 */
	@ManyToOne
	@JsonBackReference("offeringType")
	@JoinColumn(name = "offring_type")
	private Status offeringType;
	private LocalDate validTillDate;
}
