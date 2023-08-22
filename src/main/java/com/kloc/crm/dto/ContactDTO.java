/**
// * @author: windows
 * @Created_Date:Aug 3, 2023
 * @File_Name:ContactDTO.java
 *
 */
package com.kloc.crm.dto;

import java.time.LocalDate;
import lombok.Data;

/**
 * @author Ankush
 *
 */
@Data
public class ContactDTO
{
	private String contactId;
	private String lifeCycleStage;
	private String firstName;
	private String lastName;
	private String email;
	private String company;
	private String address;
	private String country;
	private String source;
	private String otherSourcetype;
	private String websiteURL;
	private String contactDestination;
	private String contactDepartment;
	private String contactCreatedBy;
	private String contactCreatedByName;
	private String contactCreatedByEmail;
	private LocalDate date;
	private LocalDate stageDate;
	private long mobileNumber;
}
