/**
 * Service interface for managing Contact entities in the CRM system.
 *
 * @Author_name: AnkushJadhav
 * @File_name: ContactService.java
 * @Created_Date: 5/7/2023
 */
package com.kloc.crm.Service;
import java.util.List;
import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.kloc.crm.Entity.Contact;
import com.kloc.crm.dto.ContactDTO;
public interface ContactService
{
	/**
	 * Creates a new contact.
	 *
	 * @param contact The contact object to be created. This should be provided in the request body.
	 * @return A ResponseEntity with a status code and message.
	 */
	ResponseEntity<String> CreateContact(Contact contact);
	
	/**
	 * Adds new contacts from an Excel file for a specific user.
	 *
	 * @param contactsFromExcel The Excel file containing contacts.
	 * @param userId The user ID for whom the contacts are being added.
	 * @return A ResponseEntity with a status code and message.
	 */
	ResponseEntity<String> AddContactsFromExcel(MultipartFile excelFile,String userId);
	
	/**
	 * Retrieves a contact by its contact ID.
	 *
	 * @param contactId The ID of the contact to retrieve.
	 * @return A ResponseEntity containing the contact with the specified ID.
	 */
	ContactDTO GetContactByContactId(String contactId);
	
	/**
	 * Retrieves all contacts.
	 *
	 * @return A list of all contacts.
	 */
	List<ContactDTO> GetAllContact();
	
	/**
	 * Retrieves all contacts within a specified date range.
	 *
	 * @param startDate The starting date of the range.
	 * @param endDate The ending date of the range.
	 * @return A list of contacts within the specified date range.
	 */
	List<ContactDTO> GetAllContactInDateRange(LocalDate startDate,LocalDate endDate); 
	
	/**
	 * Retrieves contacts by their status type and status value.
	 *
	 * @param statusType The type of the contacts to retrieve.
	 * @param statusValue The value of the contacts to retrieve.
	 * @return A list of contacts with the specified status type and value.
	 */
	List<ContactDTO> GetAllContactByStatusTypeAndStatusValue(String statusType, String statusValue);
	
	/**
	 * Retrieves all contacts within a date range and a specific status value.
	 *
	 * @param startDate The starting date of the range.
	 * @param endDate The ending date of the range.
	 * @param statusValue The status value to filter contacts.
	 * @return A ResponseEntity containing a list of contacts that match the criteria.
	 */
	List<ContactDTO> GetAllContactInDateRangeWithStatusValue(LocalDate startDate,LocalDate endDate, String statusValue); 
	 
	/**
	 * Retrieves contacts by their email.
	 *
	 * @param email The email of the contact(s) to retrieve.
	 * @return A list of contacts with the specified email.
	 */
	List<ContactDTO> GetAllContactByEmail(String email);
	
	/**
	 * Retrieves contacts by their email.
	 *
	 * @param email The email of the contact(s) to retrieve.
	 * @return A list of contacts with the specified email.
	 */
	List<ContactDTO> GetAllContactByCompany(String company);
	
	/**
	 * Retrieves contacts by their source.
	 *
	 * @param source The source of the contacts to retrieve.
	 * @return A list of contacts with the specified source.
	 */
	List<ContactDTO> GetAllContactBySource(String source);
	
	/**
	 * Retrieves contacts by their stage date.
	 *
	 * @param stageDate The stage date of the contacts to retrieve.
	 * @return A list of contacts with the specified stage date.
	 */
	List<ContactDTO> GetAllContactByStageDate(LocalDate stageDate);
	
	/**
	 * Retrieves contacts by their country.
	 *
	 * @param country The name of the country to filter contacts.
	 * @return A list of contacts from the specified country.
	 */
	List<ContactDTO> GetAllContactByCountry(String country);
	
	/**
	 * Retrieves contacts created by a specific user.
	 *
	 * @param contactCreatedBy The user ID of the contact creator.
	 * @return A list of contacts created by the specified user.
	 */
	List<ContactDTO> GetAllContactByUserId(String userId);
	
	/**
	 * Retrieves contacts created by a specific user.
	 *
	 * @param contactCreatedBy The user ID of the contact creator.
	 * @return A list of contacts created by the specified user.
	 */
	List<ContactDTO> GetAllContactByUserEmail(String userEmail);
	
	/**
	 * Updates a contact by their contact ID.
	 *
	 * @param contact The updated contact details provided in the request body.
	 * @param contactId The ID of the contact to be updated.
	 * @return A ResponseEntity with a status code and message.
	 */
	ResponseEntity<String> UpdateContactByContactId(Contact contact, String contactId);
	
	/**
	 * Deletes a contact by their contact ID.
	 *
	 * @param contactId The ID of the contact to be deleted.
	 * @return A ResponseEntity indicating whether the contact was deleted or not.
	 */
	ResponseEntity<String> DeleteContactByContactId(String contactId);
	
}