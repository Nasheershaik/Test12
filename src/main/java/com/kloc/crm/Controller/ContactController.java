/**
 * This class is the controller for managing contacts in the CRM system.
 */
package com.kloc.crm.Controller;

/**
 * The controller class that handles HTTP requests related to contacts.
 *
 * @Author_name: AnkushJadhav
 * @File_name: ContactController.java
 * @Created_Date: 5/7/2023
 */
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.time.LocalDate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kloc.crm.Entity.Contact;
import com.kloc.crm.Service.ContactService;
import com.kloc.crm.dto.ContactDTO;

@CrossOrigin("*")
@RestController
@RequestMapping("ContactController")
public class ContactController
{
	@Autowired
	ContactService contactService;
	
	/**
	 * Creates a new contact.
	 *
	 * @param contact The contact object to be created. This should be provided in the request body.
	 * @return A ResponseEntity with a status code and message.
	 */
	@PostMapping("create_contact")
	private ResponseEntity<String> CreateContact(@RequestBody Contact contact) 
	{
		return contactService.CreateContact(contact);
	} 
	
	/**
	 * Adds new contacts from an Excel file for a specific user.
	 *
	 * @param contactsFromExcel The Excel file containing contacts.
	 * @param userId The user ID for whom the contacts are being added.
	 * @return A ResponseEntity with a status code and message.
	 */
	@PostMapping("add_contact_from_excel/{userId}")
	private ResponseEntity<String> addContactsFromExcel(@RequestParam("Contacts") MultipartFile excelFile, @PathVariable String userId) 
	{
		return contactService.AddContactsFromExcel(excelFile,userId);
	} 
	
	/**
	 * Retrieves a contact by its contact ID.
	 *
	 * @param contactId The ID of the contact to retrieve.
	 * @return A ResponseEntity containing the contact with the specified ID.
	 */
	@GetMapping("get_contact_by_contactId/{contactId}")
	private ResponseEntity<ContactDTO> GetContactByContactId(@PathVariable String contactId) 
	{
		return new ResponseEntity<ContactDTO>(contactService.GetContactByContactId(contactId),HttpStatus.OK);
	}
	
	/**
	 * Retrieves all contacts.
	 *
	 * @return A list of all contacts.
	 */
	@GetMapping("get_all_contact")
	private ResponseEntity<List<ContactDTO>> GetAllContact() 
	{
		return new ResponseEntity<List<ContactDTO>>(contactService.GetAllContact(),HttpStatus.OK);
	}
	
	/**
	 * Retrieves all contacts within a specified date range.
	 *
	 * @param startDate The starting date of the range.
	 * @param endDate The ending date of the range.
	 * @return A list of contacts within the specified date range.
	 */
	@GetMapping("get_all_contact_in_date_range/{startDate}/{endDate}")
	private ResponseEntity<List<ContactDTO>> GetAllContactInDateRange(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate) 
	{
		return new ResponseEntity<List<ContactDTO>>(contactService.GetAllContactInDateRange(startDate,endDate),HttpStatus.OK);
	}
	
	/**
	 * Retrieves contacts by their status type and status value.
	 *
	 * @param statusType The type of the contacts to retrieve.
	 * @param statusValue The value of the contacts to retrieve.
	 * @return A list of contacts with the specified status type and value.
	 */
	@GetMapping("get_all_contact_by_status_type_and_status_value/{statusType}/{statusValue}")
	private ResponseEntity<List<ContactDTO>> GetAllContactByStatusTypeStatusValue(@PathVariable String statusType, @PathVariable String statusValue) 
	{
		return new ResponseEntity<List<ContactDTO>>(contactService.GetAllContactByStatusTypeAndStatusValue(statusType,statusValue),HttpStatus.OK);
	}

	/**
	 * Retrieves all contacts within a date range and a specific status value.
	 *
	 * @param startDate The starting date of the range.
	 * @param endDate The ending date of the range.
	 * @param statusValue The status value to filter contacts.
	 * @return A ResponseEntity containing a list of contacts that match the criteria.
	 */
	@GetMapping("get_all_contact_in_date_range_with_status_value/{startDate}/{endDate}/{statusValue}")
	private ResponseEntity<List<ContactDTO>> GetAllContactInDateRangeWithStatusValue(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate, @PathVariable String statusValue) 
	{
		return new ResponseEntity<List<ContactDTO>>(contactService.GetAllContactInDateRangeWithStatusValue(startDate,endDate,statusValue),HttpStatus.OK);
	}
	
	/**
	 * Retrieves contacts by their email.
	 *
	 * @param email The email of the contact(s) to retrieve.
	 * @return A list of contacts with the specified email.
	 */
	@GetMapping("get_all_contact_by_email/{email}")
	private ResponseEntity<List<ContactDTO>> GetAllContactByEmail(@PathVariable String email) 
	{
		return new ResponseEntity<List<ContactDTO>>(contactService.GetAllContactByEmail(email),HttpStatus.OK);
	}
	
	/**
	 * Retrieves contacts by their company name.
	 *
	 * @param company The name of the company to filter contacts.
	 * @return A list of contacts belonging to the specified company.
	 */
	@GetMapping("get_all_contact_by_company/{company}")
	private ResponseEntity<List<ContactDTO>> GetAllContactByCompany(@PathVariable String company) 
	{
		return new ResponseEntity<List<ContactDTO>>(contactService.GetAllContactByCompany(company),HttpStatus.OK);
	}
	
	/**
	 * Retrieves contacts by their source.
	 *
	 * @param source The source of the contacts to retrieve.
	 * @return A list of contacts with the specified source.
	 */
	@GetMapping("get_all_contact_by_source/{source}")
	private ResponseEntity<List<ContactDTO>> GetAllContactBysource(@PathVariable String source) 
	{
		return new ResponseEntity<List<ContactDTO>>(contactService.GetAllContactBySource(source),HttpStatus.OK);
	}
	
	/**
	 * Retrieves contacts by their stage date.
	 *
	 * @param stageDate The stage date of the contacts to retrieve.
	 * @return A list of contacts with the specified stage date.
	 */
	@GetMapping("get_all_contact_by_stageDate/{stageDate}")
	private ResponseEntity<List<ContactDTO>> GetAllContactByStageDate(@PathVariable LocalDate stageDate) 
	{
		return new ResponseEntity<List<ContactDTO>>(contactService.GetAllContactByStageDate(stageDate),HttpStatus.OK);
	}
	
	/**
	 * Retrieves contacts by their country.
	 *
	 * @param country The name of the country to filter contacts.
	 * @return A list of contacts from the specified country.
	 */
	@GetMapping("get_all_contact_by_country/{country}")
	private ResponseEntity<List<ContactDTO>> GetAllContactBycountry(@PathVariable String country) 
	{
		return new ResponseEntity<List<ContactDTO>>(contactService.GetAllContactByCountry(country),HttpStatus.OK);
	}
	
	/**
	 * Retrieves contacts created by a specific user.
	 *
	 * @param contactCreatedBy The user ID of the contact creator.
	 * @return A list of contacts created by the specified user.
	 */
	@GetMapping("get_all_contact_by_userId/{userId}")
	private ResponseEntity<List<ContactDTO>> GetAllContactOnContactCreatedBy(@PathVariable String userId) 
	{
		return new ResponseEntity<List<ContactDTO>>(contactService.GetAllContactByUserId(userId),HttpStatus.OK);
	}
	
	/**
	 * Retrieves contacts created by a specific user based on their email.
	 *
	 * @param userEmail The email of the contact creator.
	 * @return A list of contacts created by the user with the specified email.
	 */
	@GetMapping("get_all_contact_by_userEmail/{userEmail}")
	private ResponseEntity<List<ContactDTO>> GetAllContactByUserEmail(@PathVariable String userEmail) 
	{
		return new ResponseEntity<List<ContactDTO>>(contactService.GetAllContactByUserEmail(userEmail),HttpStatus.OK);
	}
	
	/**
	 * Updates a contact by their contact ID.
	 *
	 * @param contact The updated contact details provided in the request body.
	 * @param contactId The ID of the contact to be updated.
	 * @return A ResponseEntity with a status code and message.
	 */
	@PutMapping("update_contact_by_contactId/{contactId}")
	private ResponseEntity<String> UpdateContactByContactId(@RequestBody Contact contact, @PathVariable String contactId) 
	{
		return contactService.UpdateContactByContactId(contact, contactId);
	}
	
	/**
	 * Deletes a contact by their contact ID.
	 *
	 * @param contactId The ID of the contact to be deleted.
	 * @return A ResponseEntity indicating whether the contact was deleted or not.
	 */
	@DeleteMapping("delete_contact_by_contactId/{contactId}")
	private ResponseEntity<String> deleteContactByContactId(@PathVariable String contactId)
	{
		return contactService.DeleteContactByContactId(contactId);
	}
}