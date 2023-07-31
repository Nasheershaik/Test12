package com.kloc.crm.Service;
/**
 * Service interface for managing Contact entities in the CRM system.
 *
 * @author_name  : Ankush
 * @File_name	 : ContactService.java
 * @Created_Date : 5/7/2023
 */
import java.util.List;
import java.time.LocalDate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.kloc.crm.Entity.Contact;
public interface ContactService
{
	/**
	 * Creates a new contact.
	 *
	 * @param contact The contact object to be created.
	 */
	ResponseEntity<String> CreateContact(Contact contact);
	
	/**
	 * this method to delete contact on contact id.
	 *
	 * @param parameter1 contact id which have to be deleter.
	 * @return understandable responce if contact deleted or not.
	 */
	ResponseEntity<String> addContactsFromExcel(MultipartFile file);
	
	/**
	 * Retrieves all contacts.
	 *
	 * @return A list of all contacts.
	 */
	List<Contact> GetAllContact();
	
	/**
	 * Retrieves a contact by their contact ID.
	 *
	 * @param contactId The ID of the contact to retrieve.
	 * @return The contact with the specified ID.
	 */
	Contact GetContactByContactId(String contactId);
	
	/**
	 * Retrieves a contact by their email.
	 *
	 * @param email The email of the contact to retrieve.
	 * @return The contact with the specified email.
	 */
	List<Contact> GetAllContactByemail(String email);
	
	/**
	 * Retrieves all contacts belonging to a specific company.
	 *
	 * @param company The name of the company.
	 * @return A list of contacts belonging to the specified company.
	 */
	List<Contact> GetAllContactByCompany(String company);
	
	/**
	 * Retrieves all contacts with a specific source.
	 *
	 * @param source The source of the contacts.
	 * @return A list of contacts with the specified source.
	 */
	List<Contact> GetAllContactBysource(String source);
	
	/**
	 * Retrieves all contacts with a specific life cycle stage.
	 *
	 * @param lifeCycleStage The life cycle stage of the contacts.
	 * @return A list of contacts with the specified life cycle stage.
	 */
	List<Contact> GetAllContactByStatusType(String statusType, String statusValue);
	
	/**
	 * Retrieves all contacts with a specific stage date.
	 *
	 * @param stageDate The stage date of the contacts.
	 * @return A list of contacts with the specified stage date.
	 */
	List<Contact> GetAllContactByStageDate(LocalDate stageDate);
	
	/**
	 * Retrieves all contacts from a specific country.
	 *
	 * @param country The name of the country.
	 * @return A list of contacts from the specified country.
	 */
	List<Contact> GetAllContactBycountry(String country);
	
	/**
	 * Updates a contact by their contact ID.
	 *
	 * @param contact    The updated contact details.
	 * @param contactId  The ID of the contact to be updated.
	 */
	ResponseEntity<String> UpdateContactByContactId(Contact contact, String contactId);
	
	/**
	 * this method to delete contact on contact id.
	 *
	 * @param parameter1 contact id which have to be deleter.
	 * @return understandable responce if contact deleted or not.
	 */
	ResponseEntity<String> deleteContactByContactId(String contactId);
	
}