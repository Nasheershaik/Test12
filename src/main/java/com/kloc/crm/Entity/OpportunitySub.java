/**
 * @author Mjilani
 * @createdDate 06-07-2023
 * @filename OpportunitySUb.java
 * @data Annotation is used to build all required getter and setters based on fields
 * @NoArgsConstructor and @AllArgsConstructor is used to generate Constructors based on no argument and  all fields
 */
package com.kloc.crm.Entity;
import java.sql.Date;
import java.time.LocalDate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OpportunitySub 
{
	/**
	 * Entity Class which is having all the attributes with respective of its fields
	 * This class is responsible for generating the tables and fields in the database
	 */
	
	/**
	 * custom id generator 
	 */
	@GenericGenerator(
		    name = "opportunitysub_seq",
		   type = com.kloc.crm.Entity.IdGenerator.class,
		    parameters = {
	                 @Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "opportunitysub_"),
	                 @Parameter(name = IdGenerator.INCREMENT_PARAM, value = "1"),
	                 @Parameter(name =IdGenerator.NUMBER_FORMAT_PARAMETER, value = "%02d")
		    }
		)
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "opportunitysub_seq")
	private  String opportunitySubId;
	
	@ManyToOne
	@JoinColumn(name="opportunity_id")
	@JsonBackReference
	private Opportunity opportunityId;
	
//	@Column
//	private String opportunityType;
//	
	@ManyToOne
	@JoinColumn
	private Status status; 
	
	@Column
	private LocalDate opportunityStatusDate;
	
	@Column(name="Installments")
	private int noOfInstallements;
	

	private double price;
	
	@Column
	private LocalDate duration;
	
	
	private String currency;
	
}