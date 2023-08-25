/**
 * Represents an offering in the CRM system.
 * 
 * @Author_name: AnkushJadhav
 * @File_name: Offering.java
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
public class Offering 
{
    // Unique identifier for the offering.
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

    // The category of the offering, e.g., Fixed Bid Project or Variable Bid Project.
    @ManyToOne
    @JsonBackReference("offeringCategory")
    @JoinColumn(name = "offering_category")
    private Status offeringCategory;

    // The name or title of the offering.
    private String offeringName;

    // Cost to Company (CTC) of the offering.
    private long CTC;

    // The maximum price for the offering.
    private long ceilingPrice;

    // The minimum price for the offering.
    private long floorPrice;

    // The currency in which the offering is priced.
    private String currency;

    // The type of the offering, e.g., product or service.
    @ManyToOne
    @JsonBackReference("offeringType")
    @JoinColumn(name = "offring_type")
    private Status offeringType;

    // The date until which the offering is valid.
    private LocalDate validTillDate;
}
