/**
 * @Author_name: AnkushJadhav
 * @Created_Date:28/8/2023
 * @File_Name:ContactSubDTO.java
 *
 */
package com.kloc.crm.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ContactSubDTO 
{
	private String contactSubId;
	private String contactId;
	private String lifeCycleStage;
	private LocalDate stageDate;
}
