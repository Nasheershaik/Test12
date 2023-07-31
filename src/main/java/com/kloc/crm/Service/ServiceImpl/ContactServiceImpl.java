package com.kloc.crm.Service.ServiceImpl;

/**
 * This class is the implementation of the Contact service
 * 
 * @author_name  : Ankush
 * @File_name	 : ContactServiceImpl.java
 * @Created_Date : 5/7/2023
 */
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.kloc.crm.Entity.Contact;
import com.kloc.crm.Entity.Status;
import com.kloc.crm.Entity.User;
import com.kloc.crm.Exception.DataNotFoundException;
import com.kloc.crm.Exception.InvalidInput;
import com.kloc.crm.Exception.NullDataException;
import com.kloc.crm.FileData.ContactDataFromExcel;
import com.kloc.crm.Repository.ContactRepository;
import com.kloc.crm.Repository.StatusRepo;
import com.kloc.crm.Repository.UserRepository;
import com.kloc.crm.Service.ContactService;

import java.io.IOException;
@Service
public class ContactServiceImpl implements ContactService
{
	@Autowired
	ContactRepository contactRepository;
	@Autowired
	StatusRepo statusRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	private ContactDataFromExcel contactdatafrom;

    // Get all contacts
    @Override
    public List<Contact> GetAllContact()
    {
    	List<Contact> findAll = contactRepository.findAll();
    	if(findAll.isEmpty())
    		throw new DataNotFoundException("Sorry! no account available.");
    	else
    		return findAll;
    }

    // Get a contact by contact ID
    @Override
    public Contact GetContactByContactId(String contactId)
    {
    	if(contactId == null || contactId.equals(""))
    		throw new NullDataException("Please give contact id to get account details.");
    	else
    		return contactRepository.findById(contactId).orElseThrow(()-> new DataNotFoundException("No account available with contact id : "+contactId));
    }

    // Get a contact by email
    @Override
    public List<Contact> GetAllContactByemail(String email)
    {
    	if(email == null || email.equals(""))
			throw new NullDataException("Please enter email.");
		else
		{
			List<Contact> allContact = contactRepository.findAll().stream().filter(e -> e.getEmail().toLowerCase().equals(email.toLowerCase())).collect(Collectors.toList());
//			if(allContact.size()>1)
//				throw new InternalError("something went wrong.");
			 if(allContact.isEmpty())
				throw new DataNotFoundException("Sorry! no account with email : "+email);        	 
			else
				return allContact;
		}
    }

    // Get all contacts by company
    @Override
    public List<Contact> GetAllContactByCompany(String company)
    {
    	if(company == null || company.equals(""))
			throw new NullDataException("Please enter company name.");
    	else
    	{
    		List<Contact> findAll = contactRepository.findAll().stream().filter(e -> e.getCompany().toLowerCase().equals(company.toLowerCase())).collect(Collectors.toList());
    		if(findAll.isEmpty())
    			throw new DataNotFoundException("Sorry! no account for company : "+company);
    		else
    			return findAll;
    	}
    }

    // Get all contacts by source
    @Override
    public List<Contact> GetAllContactBysource(String source)
    {
    	if(source == null || source.equals(""))
			throw new NullDataException("Please enter source.");
    	else
    	{
    		List<Status> allStatus = statusRepository.findAll().stream().filter(e -> ( e.getTableName().toLowerCase().equals("contact") ) && ( e.getStatusType().toLowerCase().equals("contact source") ) && ( e.getStatusValue().toLowerCase().equals(source.toLowerCase()))).collect(Collectors.toList());
			if (allStatus.size() > 1) 
				throw new InternalError("something went wrong.");
			else if (allStatus.isEmpty())
				throw new DataNotFoundException("Source Not Found.");
			else
			{
				List<Contact> findAll = contactRepository.findAll().stream().filter(e -> e.getSource().getStatusId().equals(allStatus.get(0).getStatusId())).collect(Collectors.toList());
				if(findAll.isEmpty())
					throw new DataNotFoundException("No Contact with This Source.");
				else
					return findAll;
			}
    	}
    }

    // Get all contacts by Status
    @Override
    public List<Contact> GetAllContactByStatusType(String statusType, String statusValue)
    {
    	if(statusType == null || statusType.equals(""))
			throw new NullDataException("Please enter status type.");
    	else
    	{
    		if(statusValue == null || statusValue.equals(""))
    			throw new NullDataException("Please enter status value.");
    		else
    		{
    			List<Status> allStatus = statusRepository.findAll().stream().filter(e -> e.getStatusType().toLowerCase().equals(statusType.toLowerCase()) &&  e.getStatusValue().toLowerCase().equals(statusValue.toLowerCase())).collect(Collectors.toList());
    			if (allStatus.size() > 1) 
					throw new InternalError("something went wrong.");
    			else if (allStatus.isEmpty())
    				throw new DataNotFoundException("Account not found please check status type : "+statusType+" and status value : "+statusValue);
    			else
    			{
    				List<Contact> findAll = contactRepository.findAll().stream().filter(e -> e.getLifeCycleStage().getStatusId().equals(allStatus.get(0).getStatusId())).collect(Collectors.toList());
    				if(findAll.isEmpty())
    					throw new DataNotFoundException("No accounts with the status type : "+statusType+", and status value : "+statusValue);
    				else
    					return findAll;
    			}
    		}
    	}
    }
    // Get all contacts by stage date
    @Override
    public List<Contact> GetAllContactByStageDate(LocalDate stageDate)
    {
    	if(stageDate == null || stageDate.toString().equals(""))
			throw new NullDataException("Please enter stage date.");
    	else
    	{
    		List<Contact> findAll = contactRepository.findAll().stream().filter(e -> e.getStageDate().toString().toLowerCase().equals(stageDate.toString().toLowerCase())).collect(Collectors.toList());
    		if(findAll.isEmpty())
    			throw new DataNotFoundException("no accounts with stage date : "+stageDate.toString());
    		else
    			return findAll;
    	}
    }
    // Get all contacts by country
    @Override
    public List<Contact> GetAllContactBycountry(String country)
    {
    	if(country == null || country.equals(""))
			throw new NullDataException("Please enter country name.");
    	else
    	{
    		List<Contact> findAll = contactRepository.findAll().stream().filter(e -> e.getCountry().toLowerCase().equals(country.toLowerCase())).collect(Collectors.toList());
    		if(findAll.isEmpty())
    			throw new DataNotFoundException("No account in the country : "+country);
    		else
    			return findAll;
    	}
    }
    
    // Create a new contact
    @Override
    public ResponseEntity<String> CreateContact(Contact contact)
    {
    	System.out.println(contact.toString());
    	if(contact != null && !contact.toString().equals(new Contact().toString()))
    	{
    		List<Status> allStatus = statusRepository.findAll().stream().filter(e -> e.getStatusValue().toLowerCase().equals("contact")).collect(Collectors.toList());
    		if (allStatus.size()>1)
				throw new InternalError("something went wrong.");
    		else if (allStatus.isEmpty())
    			throw new NullDataException("Contact can't be created initial status not found.");
    		else
    			contact.setLifeCycleStage(allStatus.get(0));
    		if(contact.getFirstName() == null || contact.getFirstName().equals(""))
    			throw new NullDataException("Please enter first name.");
    		if(contact.getSource() == null || contact.getSource().getStatusValue() == null || contact.getSource().getStatusValue().equals(""))
    			throw new NullDataException("Please enter source.");
    		else
    		{
    			List<Status> sourceStatus = statusRepository.findAll().stream().filter(e -> e.getStatusValue().toLowerCase().equals(contact.getSource().getStatusValue().toLowerCase())).collect(Collectors.toList());
    			if (sourceStatus.size() > 1) 
    				throw new InternalError("something went wrong.");
    			else if (sourceStatus.isEmpty())
    				throw new DataNotFoundException("Source Not Found.");
    			else
    			{
    				contact.setSource(sourceStatus.get(0));
    				if (contact.getSource().getStatusValue().toLowerCase().equals("other"))
    				{
    					if (contact.getOtherSourcetype() == null || contact.getOtherSourcetype().equals("")) 
    						throw new InvalidInput("Please enter source manually.");
					}
    			}
    		}
    		if(contact.getContactCreatedBy() == null || contact.getContactCreatedBy().getUserId() == null || contact.getContactCreatedBy().getUserId().equals(""))
    			throw new NullDataException("Please enter who created.");
    		else
    		{
    			User user = userRepository.findById(contact.getContactCreatedBy().getUserId()).orElseThrow(() -> new InvalidInput("User not present."));
    			if (user == null || user.toString().equals(new User().toString()))
    				throw new InvalidInput("User not present.");
    			else
    				contact.setContactCreatedBy(user);
    		}
    		if(contact.getMobileNumber() <= 999999999)
    			throw new InvalidInput("Please enter valid mobile number.");
    		contact.setDate(LocalDate.now());
    		contact.setStageDate(LocalDate.now());
    		return new ResponseEntity<>("Contact added your contact id is : "+contactRepository.save(contact).getContactId(),HttpStatus.CREATED);
    	}
    	else
    		throw new NullDataException("Data can't be empty please check it.");
    }

    // Update a contact by contact ID
    @Override
    public ResponseEntity<String> UpdateContactByContactId(Contact contact, String contactId)
    {
    	if(contactId == null || contactId.equals(""))
    		throw new NullDataException("Please give contact id to get account details.");
//    	no fields object can no have to be updated handle that
    	if(contact != null && !contact.toString().equals(new Contact().toString()))
    	{
        	Contact contactFromDatabase = contactRepository.findById(contactId).orElseThrow(()-> new DataNotFoundException("No account available with contact id : "+contactId));
    		if(contact.getFirstName() != null && !contact.getFirstName().equals(""))
    			contactFromDatabase.setFirstName(contact.getFirstName());
    		if(contact.getLastName() != null && !contact.getLastName().equals(""))
    			contactFromDatabase.setLastName(contact.getLastName());
    		if(contact.getEmail() != null && !contact.getEmail().equals(""))
    		{
    			if(contactFromDatabase.getEmail().equals(contact.getEmail()))
    				throw new InvalidInput("You are entered your existing email enter updated email.");
    			else
    				contactFromDatabase.setEmail(contact.getEmail());
    		}
    		if(contact.getCompany() != null && !contact.getCompany().equals(""))
    			contactFromDatabase.setCompany(contact.getCompany());
    		if(contact.getAddress() != null && !contact.getAddress().equals(""))
    			contactFromDatabase.setAddress(contact.getAddress());
    		if(contact.getCountry() != null && !contact.getCountry().equals(""))
    			contactFromDatabase.setCountry(contact.getCountry());
    		if(contact.getWebsiteURL() != null && !contact.getWebsiteURL().equals(""))
    			contactFromDatabase.setWebsiteURL(contact.getWebsiteURL());
    		if(contact.getContactDestination() != null && !contact.getContactDestination().equals(""))
    			contactFromDatabase.setContactDestination(contact.getContactDestination());
    		if(contact.getContactDepartment() != null && !contact.getContactDepartment().equals(""))
    			contactFromDatabase.setContactDepartment(contact.getContactDepartment());
    		if(contact.getMobileNumber() > 999999999)
    			contactFromDatabase.setMobileNumber(contact.getMobileNumber());
    		
    		
    		contactFromDatabase.setDate(LocalDate.now());
    		contactRepository.save(contactFromDatabase);
    		return new ResponseEntity<>("Contact updated contact id is : "+contactId,HttpStatus.ACCEPTED);
    	}
    	else
    		throw new NullDataException("Data can't be empty please check it.");
	}

    
    // this is for deleting the contact corresponding to the provided contact id.
    @Override
    public ResponseEntity<String> deleteContactByContactId(String contactId)
    {
    	if(contactId == null || contactId.equals(""))
    		throw new NullDataException("Please give contact id of the account which you want to delete.");
    	else
    	{
    		Contact contact = contactRepository.findById(contactId).orElseThrow(() -> new DataNotFoundException("No account available with contact id : "+contactId));
    		if(contact != null)
    		{
    			contactRepository.deleteById(contactId);
    			return new ResponseEntity<>("Contact deleted.",HttpStatus.NO_CONTENT);
    		}
    		else
    			throw new DataNotFoundException("No account available with contact id : "+contactId);
    	}
    }

	@Override
	public ResponseEntity<String> addContactsFromExcel(MultipartFile file)
	{
		try
		{
			List<Contact> listOfContact = contactdatafrom.convertExcelToListOfContact(file.getInputStream());
			if (listOfContact.isEmpty())
				throw new DataNotFoundException("Data not found.");
			else
			{
				contactRepository.saveAll(listOfContact);
				return new ResponseEntity<>("Data added.",HttpStatus.ACCEPTED);
			}
		}
		catch (IOException e)
		{
			throw new InternalError("Something went wrong.");
		}
			
	}
}