package com.kloc.crm.Entity;
import java.time.LocalDate;

/**
 * @Author :Nasheer
 *@Date :06-July-2023
 *@FileName:Email.java
 */
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email{	
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "email-id-generator")
	@GenericGenerator(name = "email-id-generator",type = com.kloc.crm.Entity.IdGenerator.class,
	    parameters = {
	    	@Parameter(name = IdGenerator.INCREMENT_PARAM, value = "1"),
	        @Parameter(name = "valuePrefix", value = "Email__"),
	        @Parameter(name = "numberFormat", value = "%06d") 
	})
	
	private String emailId;
	
	private String toAddress;
	
	private LocalDate emailDate;
	
	private String emailMsg;
	
	private  String emailType; 
	@ManyToOne
	@JsonBackReference
	private Task task;
	
}