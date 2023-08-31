/**
 * @Author_name: AnkushJadhav
 * @Created_Date: 25/8/2023
 * @File_Name: ContactSub.java
 *
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
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class ContactSub 
{
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contactSubIdGenerator")
    @GenericGenerator(name = "contactSubIdGenerator", 
        type =  com.kloc.crm.Entity.IdGenerator.class, 
        parameters = {
            @Parameter(name = IdGenerator.INCREMENT_PARAM, value = "1"),
            @Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "contactSub_"),
            @Parameter(name = IdGenerator.NUMBER_FORMAT_PARAMETER, value = "%06d")
    })
	private String contactSubId;
	
	@ManyToOne
//	@JsonBackReference("contactId")
	@JoinColumn(name = "contact_id")
	private Contact contactId;
	@ManyToOne
	@JsonBackReference("lifeCycleStage")
	@JoinColumn(name = "life_cycle_stage_id")
	private Status lifeCycleStage;
	private LocalDate stageDate;
	
}
