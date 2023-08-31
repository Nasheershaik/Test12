/**
 * @Author_name: AnkushJadhav
 * @Created_Date: 25/8/2023
 * @File_Name: ContactSubServiceImpl.java
 *
 */
package com.kloc.crm.Service.ServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kloc.crm.Entity.ContactSub;
import com.kloc.crm.Exception.NullDataException;
import com.kloc.crm.FileData.ContactDataFromExcel;
import com.kloc.crm.Repository.ContactRepository;
import com.kloc.crm.Repository.ContactSubRepository;
import com.kloc.crm.Repository.StatusRepo;
import com.kloc.crm.Repository.TaskRepository;
import com.kloc.crm.Repository.UserRepository;
import com.kloc.crm.Service.ContactSubService;

/**
 * @author windows
 *
 */
@Service
public class ContactSubServiceImpl implements ContactSubService
{
	@Autowired
	private ContactSubRepository contactSubRepository;
	// Logger object to use logging messages in the class
	private static final Logger logger = Logger.getLogger(ContactDataFromExcel.class.getName());
	@Override
	public ContactSub CreateContactSub(ContactSub contactSub)
	{
		return contactSubRepository.save(contactSub);
	}
	@Override
	public ContactSub UpdateContactSub(ContactSub contactSub) 
	{
		return contactSubRepository.save(contactSub);
	}
	@Override
	public List<ContactSub> GetAllContactSubByContactId(String contactId)
	{
		return contactSubRepository.findAll().stream().filter(e -> e.getContactId().getContactId().equals(contactId)).toList();
	}
//	@Override
//	public List<ContactSub> GetAllContactSubByTaskId(String taskId) 
//	{
//		return contactSubRepository.findAll().stream().filter(e -> e.getTaskId().getTaskId().equals(taskId)).toList();
//	}
	@Override
	public List<ContactSub> GetAllContactSubByLifeCycleStage(String statusId) 
	{
		return contactSubRepository.findAll().stream().filter(e -> e.getLifeCycleStage().getStatusId().equalsIgnoreCase(statusId)).toList();
	}
	@Override
	public List<ContactSub> GetAllContactSubByStageDate(LocalDate date)
	{
		return contactSubRepository.findAll().stream().filter(e -> e.getStageDate().equals(date)).toList();
	}
	
	
}
