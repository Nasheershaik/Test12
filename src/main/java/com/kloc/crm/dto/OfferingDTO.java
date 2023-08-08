/**
 * @author: windows
 * @Created_Date:Aug 3, 2023
 * @File_Name:OfferingDTO.java
 *
 */
package com.kloc.crm.dto;

import java.time.LocalDate;

import lombok.Data;

/**
 * @author windows
 *
 */
@Data
public class OfferingDTO 
{
	private String offeringId;
	private String offeringName;
	private long CTC;
	private long projectCost;
	private long actualCost;
	private String costType;
	private String offeringCategory;
	private String offeringType;
	private LocalDate validTillDate;
}
