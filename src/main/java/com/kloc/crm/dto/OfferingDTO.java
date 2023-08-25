/**
 * This class represents a Data Transfer Object (DTO) for an offering.
 * It is used to transfer offering-related information between different parts of the application.
 *
 * @Auther_name: AnkushJadhav
 * @File_Name: OfferingDTO.java
 * @Created_Date: 3/8/2023
 */
package com.kloc.crm.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class OfferingDTO 
{
    // Unique identifier for the offering.
    private String offeringId;
    
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
    
    // The category to which the offering belongs.
    private String offeringCategory;
    
    // The type or nature of the offering (e.g., product, service).
    private String offeringType;
    
    // The date until which the offering is valid.
    private LocalDate validTillDate;
}
