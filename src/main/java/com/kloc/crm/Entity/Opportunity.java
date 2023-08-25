/**
 * @author Mjilani
 * @createdDate  : 07-07-2023
 * @fileName : Opportunity.java
 * @data Annotation is used to build all required getter and setters based on fields
 * @NoArgsConstructor and @AllArgsConstructor is used to generate Constructors based on no argument and  all fields
 */
package com.kloc.crm.Entity;
import java.time.LocalDate;
import java.util.List;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name="Opportunity")
public class Opportunity
{
	/**
	 * Entity Class which is having all the attributes with respective of its fields
	 * This class is responsible for generating the tables and fields in the database
	 */
	/**
	 * custom id generator 
	 */
	@GenericGenerator(
	    name = "opportunity_seq",
	    type = com.kloc.crm.Entity.IdGenerator.class,
	    parameters = {
                 @Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "opportunity_"),
                 @Parameter(name = IdGenerator.INCREMENT_PARAM, value = "1"),
                 @Parameter(name =IdGenerator.NUMBER_FORMAT_PARAMETER, value = "%02d")
	    }
	)
	/**
	 * This attribute is the Primary key for this Particular table
	 * @column attribute represents the fields of the table in database
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "opportunity_seq")
	private String opportunityId;
	
	@Column(name="Opportunity_Name")
	private String opportunityName;
	
	@Column(name="Opportunity_size")
	private long opportunitySize;
	
//	@ManyToOne
//	@JoinColumn
//	private Customer customer_Id;
	
	@OneToOne
	@JoinColumn(name = "contact_id")
	private Contact contact;

	@ManyToOne
	@JoinColumn(name="offering_id")
    private Offering offering;
	
	@Column
	private LocalDate opportunityCreatedDate;
	
}
