/**
 * @author Mjilani
 * @createdDate : 06-07-2023
 * @fileName : Customer.java
 * @data Annotation is used to build all required getter and setters based on fields
 * @NoArgsConstructor and @AllArgsConstructor is used to generate Constructors based on no argument and  all feilds
 */
package com.kloc.crm.Entity;
import java.time.LocalDate;
import java.util.List;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer
{
	/**
	 * Entity Class which is having all the attributes with respective of its fields
	 * This class is responsible for generating the tables and fields in the database
	 */
	
	/**
	 * custom id generator 
	 */
	@GenericGenerator(
		    name = "customer_seq",
		 type= com.kloc.crm.Entity.IdGenerator.class, 
		    parameters = {
	                 @Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "customer_"),
	                 @Parameter(name = IdGenerator.INCREMENT_PARAM, value = "1"),
	                 @Parameter(name =IdGenerator.NUMBER_FORMAT_PARAMETER, value = "%02d")
		    }
		)
	
	/**
	 * This attribute is the Primary key for this Particular table
	 * @column attribute represents the fields of the table in database
	 */
		@Id
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
		@Column(nullable = false,name="Id")
	    private String customerId;
	
	
	@Column(name="Date")
	private LocalDate customerCreatedDate;
	
//	@ManyToOne
//	@JoinColumn(name="OpportunityId")
//	private Opportunity Opportunity;

//	@OneToOne
//	@JoinColumn
//	private Status status;
	
	/**mapping with contact table where as a one customer can have multiple contacts**/
	/**one contact can have multiple customers**/
	@ManyToOne
	@JoinColumn(name="contact_id")
	private Contact contact;
}
