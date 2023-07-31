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
	@PostMapping("add_contact_from_excel")
	private ResponseEntity<String> addContactsFromExcel(@RequestParam("Contacts") MultipartFile contactsFromExcel) 
	{
		return contactService.addContactsFromExcel(contactsFromExcel);
	} 
	
	/**
	 * Retrieves all contacts. 
	 *
	 * @return A list of all contacts.
	 */
	@GetMapping("get_all_contact")
	private ResponseEntity<List<Contact>> GetAllContact() 
	{
		return new ResponseEntity<List<Contact>>(contactService.GetAllContact(),HttpStatus.FOUND);
	}
	
	/**
	 * Retrieves a contact by its contact ID.
	 *
	 * @PathVariable contactId The ID of the contact to retrieve.
	 * @return       The contact with the specified ID.
	 */
	@GetMapping("get_contact_by_contactId/{contactId}")
	private ResponseEntity<Contact> GetContactByContactId(@PathVariable String contactId) 
	{
		return new ResponseEntity<Contact>(contactService.GetContactByContactId(contactId),HttpStatus.FOUND);
	}
	
	/**
	 * Retrieves a contact by its email.
	 *
	 * @PathVariable email The email of the contact to retrieve.
	 * @return 		 The contact with the specified email.
	 */
//	have to change api url
	@GetMapping("get_contact_by_email/{email}")
	private ResponseEntity<List<Contact>> GetAllContactByemail(@PathVariable String email) 
	{
		return new ResponseEntity<List<Contact>>(contactService.GetAllContactByemail(email),HttpStatus.FOUND);
	}
	/**
	 * Retrieves all contacts belonging to a specific company.
	 *
	 * @PathVariable company The name of the company.
	 * @return 		 A list of contacts belonging to the specified company.
	 */
	@GetMapping("get_all_contact_by_company/{company}")
	private ResponseEntity<List<Contact>> GetAllContactByCompany(@PathVariable String company) 
	{
		return new ResponseEntity<List<Contact>>(contactService.GetAllContactByCompany(company),HttpStatus.FOUND);
	}
	/**
	 * Retrieves all contacts with a specific source.
	 *
	 * @PathVariable source The source of the contacts.
	 * @return 		 A list of contacts with the specified source.
	 */
	@GetMapping("get_all_contact_by_source/{source}")
	private ResponseEntity<List<Contact>> GetAllContactBysource(@PathVariable String source) 
	{
		return new ResponseEntity<List<Contact>>(contactService.GetAllContactBysource(source),HttpStatus.FOUND);
	}
	/**
	 * Retrieves all contacts with a specific Status.
	 *
	 * @PathVariable Status Type of the contacts.
	 * @PathVariable Status Value of the contacts.
	 * @return 		 A list of contacts with the specified Status.
	 */
	@GetMapping("get_all_contact_by_status_type_status_value/{statusType}/{statusValue}")
	private ResponseEntity<List<Contact>> GetAllContactByStatusType(@PathVariable String statusType,@PathVariable String statusValue) 
	{
		return new ResponseEntity<List<Contact>>(contactService.GetAllContactByStatusType(statusType, statusValue),HttpStatus.FOUND);
	}
	
	/**
	 * Retrieves all contacts with a specific stage date.
	 *
	 * @PathVariable stageDate The stage date of the contacts.
	 * @return		 A list of contacts with the specified stage date.
	 */
	@GetMapping("get_all_contact_by_stageDate/{stageDate}")
	private ResponseEntity<List<Contact>> GetAllContactByStageDate(@PathVariable LocalDate stageDate) 
	{
		return new ResponseEntity<List<Contact>>(contactService.GetAllContactByStageDate(stageDate),HttpStatus.FOUND);
	}
	
	/**
	 * Retrieves all contacts from a specific country.
	 *
	 * @PathVariable country The name of the country.
	 * @return		 A list of contacts from the specified country.
	 */
	@GetMapping("get_all_contact_by_country/{country}")
	private ResponseEntity<List<Contact>> GetAllContactBycountry(@PathVariable String country) 
	{
		return new ResponseEntity<List<Contact>>(contactService.GetAllContactBycountry(country),HttpStatus.FOUND);
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