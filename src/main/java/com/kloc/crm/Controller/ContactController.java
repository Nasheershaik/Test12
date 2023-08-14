/**
 * This class is the controller for managing contacts in the CRM system.
 */
package com.kloc.crm.Controller;

/**
 * The controller class that handles HTTP requests related to contacts.
 *
 * @author_name  : Ankush
 * @File_name	 : ContactController.java
 * @Created_Date : 5/7/2023
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
	 * @RequestBody contact The contact object to be created.
	 */
	@PostMapping("create_contact")
	private ResponseEntity<String> CreateContact(@RequestBody Contact contact) 
	{
		return contactService.CreateContact(contact);
	} 
	
	/**
	 * Creates a new contact.
	 *
	 * @RequestBody contact The contact object to be created.
	 */
	@PostMapping("add_contact_from_excel/{userId}")
	private ResponseEntity<String> addContactsFromExcel(@RequestParam("Contacts") MultipartFile contactsFromExcel, @PathVariable String userId) 
	{
		return contactService.addContactsFromExcel(contactsFromExcel,userId);
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
	 * Retrieves a contact by its contact ID.
	 *
	 * @PathVariable contactId The ID of the contact to retrieve.
	 * @return       The contact with the specified ID.
	 */
	@GetMapping("get_contact_by_contactId/{contactId}")
	private ResponseEntity<ContactDTO> GetContactByContactId(@PathVariable String contactId) 
	{
		return new ResponseEntity<ContactDTO>(contactService.GetContactByContactId(contactId),HttpStatus.OK);
	}
	
	/**
	 * Retrieves a contact by its email.
	 *
	 * @PathVariable email The email of the contact to retrieve.
	 * @return 		 The contact with the specified email.
	 */
	@GetMapping("get_all_contact_by_email/{email}")
	private ResponseEntity<List<ContactDTO>> GetAllContactByemail(@PathVariable String email) 
	{
		return new ResponseEntity<List<ContactDTO>>(contactService.GetAllContactByemail(email),HttpStatus.OK);
	}
	/**
	 * Retrieves all contacts belonging to a specific company.
	 *
	 * @PathVariable company The name of the company.
	 * @return 		 A list of contacts belonging to the specified company.
	 */
	@GetMapping("get_all_contact_by_company/{company}")
	private ResponseEntity<List<ContactDTO>> GetAllContactByCompany(@PathVariable String company) 
	{
		return new ResponseEntity<List<ContactDTO>>(contactService.GetAllContactByCompany(company),HttpStatus.OK);
	}
	/**
	 * Retrieves all contacts with a specific source.
	 *
	 * @PathVariable source The source of the contacts.
	 * @return 		 A list of contacts with the specified source.
	 */
	@GetMapping("get_all_contact_by_source/{source}")
	private ResponseEntity<List<ContactDTO>> GetAllContactBysource(@PathVariable String source) 
	{
		return new ResponseEntity<List<ContactDTO>>(contactService.GetAllContactBysource(source),HttpStatus.OK);
	}
	/**
	 * Retrieves all contacts with a specific Status.
	 *
	 * @PathVariable Status Type of the contacts.
	 * @PathVariable Status Value of the contacts.
	 * @return 		 A list of contacts with the specified Status.
	 */
	@GetMapping("get_all_contact_by_status_type_status_value/{statusValue}")
	private ResponseEntity<List<ContactDTO>> GetAllContactByStatusType(@PathVariable String statusValue) 
	{
		return new ResponseEntity<List<ContactDTO>>(contactService.GetAllContactByStatusType(statusValue),HttpStatus.OK);
	}
	
	/**
	 * Retrieves all contacts with a specific stage date.
	 *
	 * @PathVariable stageDate The stage date of the contacts.
	 * @return		 A list of contacts with the specified stage date.
	 */
	@GetMapping("get_all_contact_by_stageDate/{stageDate}")
	private ResponseEntity<List<ContactDTO>> GetAllContactByStageDate(@PathVariable LocalDate stageDate) 
	{
		return new ResponseEntity<List<ContactDTO>>(contactService.GetAllContactByStageDate(stageDate),HttpStatus.OK);
	}
	
	/**
	 * Retrieves all contacts from a specific country.
	 *
	 * @PathVariable country The name of the country.
	 * @return		 A list of contacts from the specified country.
	 */
	@GetMapping("get_all_contact_by_country/{country}")
	private ResponseEntity<List<ContactDTO>> GetAllContactBycountry(@PathVariable String country) 
	{
		return new ResponseEntity<List<ContactDTO>>(contactService.GetAllContactBycountry(country),HttpStatus.OK);
	}
	
	/**
	 * Retrieves all contacts from a contact created by.
	 *
	 * @PathVariableountry  The user id of the contact created by.
	 * @return				A list of contacts from the contact created by.
	 */
	@GetMapping("get_all_contact_by_contact_created_by/{contactCreatedBy}")
	private ResponseEntity<List<ContactDTO>> GetAllContactOnContactCreatedBy(@PathVariable String contactCreatedBy) 
	{
		return new ResponseEntity<List<ContactDTO>>(contactService.GetAllContactOnContactCreatedBy(contactCreatedBy),HttpStatus.OK);
	}
	/**
	 * Retrieves all contacts from a contact created by.
	 *
	 * @PathVariableountry  The user mail of the contact created by.
	 * @return				A list of contacts from the contact created by Mail.
	 */
	@GetMapping("get_all_contact_by_contact_created_by_Mail/{contactCreatedBy}")
	private ResponseEntity<List<ContactDTO>> GetAllContactContactCreatedByMail(@PathVariable String contactCreatedBy) 
	{
		return new ResponseEntity<List<ContactDTO>>(contactService.getAllContactCreateddByMail(contactCreatedBy),HttpStatus.OK);
	}
	/**
	 * Updates a contact by their contact ID.
	 *
	 * @RequestBody  contact   The updated contact details.
	 * @PathVariable contactId The ID of the contact to be updated.
	 */
	@PutMapping("update_contact_by_contactId/{contactId}")
	private ResponseEntity<String> UpdateContactByContactId(@RequestBody Contact contact, @PathVariable String contactId) 
	{
		return contactService.UpdateContactByContactId(contact, contactId);
	}
	
	/**
	 * delete contact on contact id
	 *
	 * @param parameter1 contact id which have to be deleted
	 * @return respnce if contact have deleted or not.
	 */
	@DeleteMapping("delete_contact_by_contactId/{contactId}")
	private ResponseEntity<String> deleteContactByContactId(@PathVariable String contactId)
	{
		return contactService.deleteContactByContactId(contactId);
	}
}