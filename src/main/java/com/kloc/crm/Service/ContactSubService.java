/**
 * @Author_name: AnkushJadhav
 * @Created_Date: 25/8/2023
 * @File_Name:ContactSubService.java
 *
 */
package com.kloc.crm.Service;

import java.time.LocalDate;
import java.util.List;

import com.kloc.crm.Entity.ContactSub;

public interface ContactSubService 
{
	ContactSub CreateContactSub(ContactSub contactSub);
	ContactSub UpdateContactSub(ContactSub contactSub);
	List<ContactSub> GetAllContactSubByContactId(String contactId);
	//List<ContactSub> GetAllContactSubByTaskId(String taskId);
	List<ContactSub> GetAllContactSubByLifeCycleStage(String statusId);
	List<ContactSub> GetAllContactSubByStageDate(LocalDate date);
}
