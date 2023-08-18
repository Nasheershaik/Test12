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
import java.util.logging.Logger;
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
import com.kloc.crm.dto.ContactDTO;

import java.io.IOException;

@Service
public class ContactServiceImpl implements ContactService {
	@Autowired
	ContactRepository contactRepository;
	@Autowired
	StatusRepo statusRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	private ContactDataFromExcel contactdatafrom;

	// Logger object to use logging messages in the class
	private static final Logger logger = Logger.getLogger(ContactDataFromExcel.class.getName());

	// Get all contacts
	@Override
	public List<ContactDTO> GetAllContact() {
		logger.info("Fetching all contacts...");

		try {
			List<Contact> findAll = contactRepository.findAll();
			if (findAll.isEmpty()) {
				logger.warning("No contacts available.");
				throw new DataNotFoundException("No contacts available.");
			} else {

				List<ContactDTO> contactDTOs = findAll.stream().map(contact -> {
					ContactDTO contactDTO = new ContactDTO();
					contactDTO.setContactId(contact.getContactId());
					contactDTO.setLifeCycleStage(contact.getLifeCycleStage().getStatusValue());
					contactDTO.setFirstName(contact.getFirstName());
					contactDTO.setLastName(contact.getLastName());
					contactDTO.setEmail(contact.getEmail());
					contactDTO.setCompany(contact.getCompany());
					contactDTO.setAddress(contact.getAddress());
					contactDTO.setCountry(contact.getCountry());
					if (contact.getSource().getStatusValue().equalsIgnoreCase("other"))
						contactDTO.setOtherSourcetype(contact.getOtherSourcetype());
					contactDTO.setSource(contact.getSource().getStatusValue());
					contactDTO.setWebsiteURL(contact.getWebsiteURL());
					contactDTO.setContactDestination(contact.getContactDestination());
					contactDTO.setContactDepartment(contact.getContactDepartment());
					contactDTO.setContactCreatedBy(contact.getContactCreatedBy().getUserId());
					contactDTO.setContactCreatedByName(contact.getContactCreatedBy().getUserName());
					contactDTO.setContactCreatedByEmail(contact.getContactCreatedBy().getEmail());
					contactDTO.setDate(contact.getDate());
					contactDTO.setStageDate(contact.getStageDate());
					contactDTO.setMobileNumber(contact.getMobileNumber());
					return contactDTO;
				}).collect(Collectors.toList());

				logger.info("Contacts fetched successfully.");
				return contactDTOs;
			}
		} catch (Exception e) {
			logger.severe("An error occurred while fetching contacts: " + e.getMessage());
			throw e; // Rethrow the exception
		}
	}

	// Get a contact by contact ID
	@Override
	public ContactDTO GetContactByContactId(String contactId) {
		logger.info("Fetching contact by contact id...");

		if (contactId == null || contactId.equals("")) {
			logger.warning("Contact id is missing.");
			throw new NullDataException("Please give contact id to get account details.");
		}

		try {
			Contact contact = contactRepository.findById(contactId).orElseThrow(
					() -> new DataNotFoundException("No account available with contact id : " + contactId));
			ContactDTO contactDTO = new ContactDTO();
			contactDTO.setContactId(contact.getContactId());
			contactDTO.setLifeCycleStage(contact.getLifeCycleStage().getStatusValue());
			contactDTO.setFirstName(contact.getFirstName());
			contactDTO.setLastName(contact.getLastName());
			contactDTO.setEmail(contact.getEmail());
			contactDTO.setCompany(contact.getCompany());
			contactDTO.setAddress(contact.getAddress());
			contactDTO.setCountry(contact.getCountry());
			if (contact.getSource().getStatusValue().equalsIgnoreCase("other"))
				contactDTO.setOtherSourcetype(contact.getOtherSourcetype());
			contactDTO.setSource(contact.getSource().getStatusValue());
			contactDTO.setOtherSourcetype(contact.getOtherSourcetype());
			contactDTO.setWebsiteURL(contact.getWebsiteURL());
			contactDTO.setContactDestination(contact.getContactDestination());
			contactDTO.setContactDepartment(contact.getContactDepartment());
			contactDTO.setContactCreatedBy(contact.getContactCreatedBy().getUserId());
			contactDTO.setContactCreatedByName(contact.getContactCreatedBy().getUserName());
			contactDTO.setContactCreatedByEmail(contact.getContactCreatedBy().getEmail());
			contactDTO.setDate(contact.getDate());
			contactDTO.setStageDate(contact.getStageDate());
			contactDTO.setMobileNumber(contact.getMobileNumber());

			logger.info("Contact fetched successfully.");
			return contactDTO;
		} catch (Exception e) {
			logger.severe("An error occurred while fetching contact: " + e.getMessage());
			throw e; // Rethrow the exception
		}
	}

	// Get a contact by email
	@Override
	public List<ContactDTO> GetAllContactByemail(String email) {
		logger.info("Fetching contacts by email...");

		if (email == null || email.equals("")) {
			logger.warning("Email is missing.");
			throw new NullDataException("Please enter email.");
		}

		try {

			List<Contact> findAll = contactRepository.findAll().stream()
					.filter(e -> e.getEmail().toLowerCase().equals(email.toLowerCase())).collect(Collectors.toList());

			if (findAll.isEmpty()) {
				logger.warning("No account found with email: " + email);
				throw new DataNotFoundException("Sorry! no account with email : " + email);
			} else {
				logger.info("Contacts fetched successfully by email.");
				List<ContactDTO> contactDTOs = findAll.stream().map(contact -> {
					ContactDTO contactDTO = new ContactDTO();
					contactDTO.setContactId(contact.getContactId());
					contactDTO.setLifeCycleStage(contact.getLifeCycleStage().getStatusValue());
					contactDTO.setFirstName(contact.getFirstName());
					contactDTO.setLastName(contact.getLastName());
					contactDTO.setEmail(contact.getEmail());
					contactDTO.setCompany(contact.getCompany());
					contactDTO.setAddress(contact.getAddress());
					contactDTO.setCountry(contact.getCountry());
					if (contact.getSource().getStatusValue().equalsIgnoreCase("other"))
						contactDTO.setOtherSourcetype(contact.getOtherSourcetype());
					contactDTO.setSource(contact.getSource().getStatusValue());
					contactDTO.setOtherSourcetype(contact.getOtherSourcetype());
					contactDTO.setWebsiteURL(contact.getWebsiteURL());
					contactDTO.setContactDestination(contact.getContactDestination());
					contactDTO.setContactDepartment(contact.getContactDepartment());
					contactDTO.setContactCreatedBy(contact.getContactCreatedBy().getUserId());
					contactDTO.setContactCreatedByName(contact.getContactCreatedBy().getUserName());
					contactDTO.setContactCreatedByEmail(contact.getContactCreatedBy().getEmail());
					contactDTO.setDate(contact.getDate());
					contactDTO.setStageDate(contact.getStageDate());
					contactDTO.setMobileNumber(contact.getMobileNumber());
					return contactDTO;
				}).collect(Collectors.toList());

				logger.info("Contacts fetched successfully by email.");
				return contactDTOs;
			}
		} catch (Exception e) {
			logger.severe("An error occurred while fetching contacts by email: " + e.getMessage());
			throw e; // Rethrow the exception
		}
	}

	// Get all contacts by company
	@Override
	public List<ContactDTO> GetAllContactByCompany(String company) {
		logger.info("Fetching contacts by company...");

		if (company == null || company.equals("")) {
			logger.warning("Company name is missing.");
			throw new NullDataException("Please enter company name.");
		}

		try {
			List<Contact> findAll = contactRepository.findAll().stream()
					.filter(e -> e.getCompany().toLowerCase().equals(company.toLowerCase()))
					.collect(Collectors.toList());

			if (findAll.isEmpty()) {
				logger.warning("No account found for company: " + company);
				throw new DataNotFoundException("Sorry! no account for company : " + company);
			} else {
				List<ContactDTO> contactDTOs = findAll.stream().map(contact -> {
					ContactDTO contactDTO = new ContactDTO();
					contactDTO.setContactId(contact.getContactId());
					contactDTO.setLifeCycleStage(contact.getLifeCycleStage().getStatusValue());
					contactDTO.setFirstName(contact.getFirstName());
					contactDTO.setLastName(contact.getLastName());
					contactDTO.setEmail(contact.getEmail());
					contactDTO.setCompany(contact.getCompany());
					contactDTO.setAddress(contact.getAddress());
					contactDTO.setCountry(contact.getCountry());
					if (contact.getSource().getStatusValue().equalsIgnoreCase("other"))
						contactDTO.setOtherSourcetype(contact.getOtherSourcetype());
					contactDTO.setSource(contact.getSource().getStatusValue());
					contactDTO.setOtherSourcetype(contact.getOtherSourcetype());
					contactDTO.setWebsiteURL(contact.getWebsiteURL());
					contactDTO.setContactDestination(contact.getContactDestination());
					contactDTO.setContactDepartment(contact.getContactDepartment());
					contactDTO.setContactCreatedBy(contact.getContactCreatedBy().getUserId());
					contactDTO.setContactCreatedByName(contact.getContactCreatedBy().getUserName());
					contactDTO.setContactCreatedByEmail(contact.getContactCreatedBy().getEmail());
					contactDTO.setDate(contact.getDate());
					contactDTO.setStageDate(contact.getStageDate());
					contactDTO.setMobileNumber(contact.getMobileNumber());
					return contactDTO;
				}).collect(Collectors.toList());

				logger.info("Contacts fetched successfully by company.");
				return contactDTOs;
			}
		} catch (Exception e) {
			logger.severe("An error occurred while fetching contacts by company: " + e.getMessage());
			throw e; // Rethrow the exception
		}
	}

	// Get all contacts by source
	@Override
	public List<ContactDTO> GetAllContactBysource(String source) {
		logger.info("Fetching contacts by source...");
		if (source == null || source.equals("")) {
			logger.warning("Source is missing.");
			throw new NullDataException("Please enter source.");
		} else {
			try {
				List<Status> allStatus = statusRepository.findAll().stream()
						.filter(e -> e.getTableName().toLowerCase().equals("contact")
								&& e.getStatusType().toLowerCase().equals("contact_source")
								&& e.getStatusValue().toLowerCase().equals(source.toLowerCase()))
						.collect(Collectors.toList());
				if (allStatus.size() > 1) {
					logger.warning("Duplicate source values in database.");
					throw new InternalError("Something went wrong.");
				} else if (allStatus.isEmpty()) {
					logger.warning("Source not found.");
					throw new DataNotFoundException("Source Not Found.");
				} else {
					List<Contact> findAll = contactRepository.findAll().stream()
							.filter(e -> e.getSource().getStatusId().equals(allStatus.get(0).getStatusId()))
							.collect(Collectors.toList());
					if (findAll.isEmpty()) {
						logger.warning("No contact found with this source.");
						throw new DataNotFoundException("No Contact with This Source.");
					} else {
						List<ContactDTO> contactDTOs = findAll.stream().map(contact -> {
							ContactDTO contactDTO = new ContactDTO();
							contactDTO.setContactId(contact.getContactId());
							contactDTO.setLifeCycleStage(contact.getLifeCycleStage().getStatusValue());
							contactDTO.setFirstName(contact.getFirstName());
							contactDTO.setLastName(contact.getLastName());
							contactDTO.setEmail(contact.getEmail());
							contactDTO.setCompany(contact.getCompany());
							contactDTO.setAddress(contact.getAddress());
							contactDTO.setCountry(contact.getCountry());
							if (contact.getSource().getStatusValue().equalsIgnoreCase("other"))
								contactDTO.setOtherSourcetype(contact.getOtherSourcetype());
							contactDTO.setSource(contact.getSource().getStatusValue());
							contactDTO.setOtherSourcetype(contact.getOtherSourcetype());
							contactDTO.setWebsiteURL(contact.getWebsiteURL());
							contactDTO.setContactDestination(contact.getContactDestination());
							contactDTO.setContactDepartment(contact.getContactDepartment());
							contactDTO.setContactCreatedBy(contact.getContactCreatedBy().getUserId());
							contactDTO.setContactCreatedByName(contact.getContactCreatedBy().getUserName());
							contactDTO.setContactCreatedByEmail(contact.getContactCreatedBy().getEmail());
							contactDTO.setDate(contact.getDate());
							contactDTO.setStageDate(contact.getStageDate());
							contactDTO.setMobileNumber(contact.getMobileNumber());
							return contactDTO;
						}).collect(Collectors.toList());

						logger.info("Contacts fetched successfully by source.");
						return contactDTOs;
					}
				}
			} catch (Exception e) {
				logger.severe("An error occurred while fetching contacts by source: " + e.getMessage());
				throw e; // Rethrow the exception
			}
		}
	}

	// Get all contacts by Status
	@Override
	public List<ContactDTO> GetAllContactByStatusType(String statusValue) {
		logger.info("Fetching contacts by status type and value...");
		if (statusValue == null || statusValue.equals("")) {
			logger.warning("Status value is missing.");
			throw new NullDataException("Please enter status value.");
		} else {
			try {
				List<Status> allStatus = statusRepository.findAll().stream()
						.filter(e -> e.getStatusValue().equalsIgnoreCase(statusValue)).collect(Collectors.toList());

				if (allStatus.size() > 1) {
					logger.warning("Duplicate status values in database.");
					throw new InternalError("Something went wrong.");
				} else if (allStatus.isEmpty()) {
					logger.warning("Status not found.");
					throw new DataNotFoundException("Account not found please check status value : " + statusValue);
				} else {
					List<Contact> findAll = contactRepository.findAll().stream()
							.filter(e -> e.getLifeCycleStage().getStatusId().equals(allStatus.get(0).getStatusId()))
							.collect(Collectors.toList());

					if (findAll.isEmpty()) {
						logger.warning("No accounts found with the given status type and value.");
						throw new DataNotFoundException("No accounts with the status value : " + statusValue);
					} else {
						List<ContactDTO> contactDTOs = findAll.stream().map(contact -> {
							ContactDTO contactDTO = new ContactDTO();
							contactDTO.setContactId(contact.getContactId());
							contactDTO.setLifeCycleStage(contact.getLifeCycleStage().getStatusValue());
							contactDTO.setFirstName(contact.getFirstName());
							contactDTO.setLastName(contact.getLastName());
							contactDTO.setEmail(contact.getEmail());
							contactDTO.setCompany(contact.getCompany());
							contactDTO.setAddress(contact.getAddress());
							contactDTO.setCountry(contact.getCountry());
							if (contact.getSource().getStatusValue().equalsIgnoreCase("other"))
								contactDTO.setOtherSourcetype(contact.getOtherSourcetype());
							contactDTO.setSource(contact.getSource().getStatusValue());
							contactDTO.setOtherSourcetype(contact.getOtherSourcetype());
							contactDTO.setWebsiteURL(contact.getWebsiteURL());
							contactDTO.setContactDestination(contact.getContactDestination());
							contactDTO.setContactDepartment(contact.getContactDepartment());
							contactDTO.setContactCreatedBy(contact.getContactCreatedBy().getUserId());
							contactDTO.setContactCreatedByName(contact.getContactCreatedBy().getUserName());
							contactDTO.setContactCreatedByEmail(contact.getContactCreatedBy().getEmail());
							contactDTO.setDate(contact.getDate());
							contactDTO.setStageDate(contact.getStageDate());
							contactDTO.setMobileNumber(contact.getMobileNumber());
							return contactDTO;
						}).collect(Collectors.toList());

						logger.info("Contacts fetched successfully by status type and value.");
						return contactDTOs;
					}
				}
			} catch (Exception e) {
				logger.severe("An error occurred while fetching contacts by status type and value: " + e.getMessage());
				throw e; // Rethrow the exception
			}
		}
	}

	// Get all contacts by stage date
	@Override
	public List<ContactDTO> GetAllContactByStageDate(LocalDate stageDate) {
		logger.info("Getting contacts by stage date...");

		if (stageDate == null || stageDate.toString().equals("")) {
			logger.warning("Stage date is missing.");
			throw new NullDataException("Please enter stage date.");
		} else {
			List<Contact> findAll = contactRepository.findAll().stream()
					.filter(e -> e.getStageDate().toString().toLowerCase().equals(stageDate.toString().toLowerCase()))
					.collect(Collectors.toList());

			if (findAll.isEmpty()) {
				logger.warning("No accounts found with stage date: " + stageDate.toString());
				throw new DataNotFoundException("No accounts with stage date: " + stageDate.toString());
			}
			List<ContactDTO> contactDTOs = findAll.stream().map(contact -> {
				ContactDTO contactDTO = new ContactDTO();
				contactDTO.setContactId(contact.getContactId());
				contactDTO.setLifeCycleStage(contact.getLifeCycleStage().getStatusValue());
				contactDTO.setFirstName(contact.getFirstName());
				contactDTO.setLastName(contact.getLastName());
				contactDTO.setEmail(contact.getEmail());
				contactDTO.setCompany(contact.getCompany());
				contactDTO.setAddress(contact.getAddress());
				contactDTO.setCountry(contact.getCountry());
				if (contact.getSource().getStatusValue().equalsIgnoreCase("other"))
					contactDTO.setOtherSourcetype(contact.getOtherSourcetype());
				contactDTO.setSource(contact.getSource().getStatusValue());
				contactDTO.setOtherSourcetype(contact.getOtherSourcetype());
				contactDTO.setWebsiteURL(contact.getWebsiteURL());
				contactDTO.setContactDestination(contact.getContactDestination());
				contactDTO.setContactDepartment(contact.getContactDepartment());
				contactDTO.setContactCreatedBy(contact.getContactCreatedBy().getUserId());
				contactDTO.setContactCreatedByName(contact.getContactCreatedBy().getUserName());
				contactDTO.setContactCreatedByEmail(contact.getContactCreatedBy().getEmail());
				contactDTO.setDate(contact.getDate());
				contactDTO.setStageDate(contact.getStageDate());
				contactDTO.setMobileNumber(contact.getMobileNumber());
				return contactDTO;
			}).collect(Collectors.toList());
			logger.info("Found contacts by stage date: " + stageDate.toString());
			return contactDTOs;
		}
	}

	// Get all contacts by country
	@Override
	public List<ContactDTO> GetAllContactBycountry(String country) {
		logger.info("Getting contacts by country...");

		if (country == null || country.isEmpty()) {
			logger.warning("Country name is missing.");
			throw new NullDataException("Please enter country name.");
		} else {
			List<Contact> findAll = contactRepository.findAll().stream()
					.filter(e -> e.getCountry().equalsIgnoreCase(country)).collect(Collectors.toList());

			if (findAll.isEmpty()) {
				logger.warning("No accounts found in the country: " + country);
				throw new DataNotFoundException("No accounts in the country: " + country);
			}

			logger.info("Found contacts in the country: " + country);

			List<ContactDTO> contactDTOs = findAll.stream().map(contact -> {
				ContactDTO contactDTO = new ContactDTO();
				contactDTO.setContactId(contact.getContactId());
				contactDTO.setLifeCycleStage(contact.getLifeCycleStage().getStatusValue());
				contactDTO.setFirstName(contact.getFirstName());
				contactDTO.setLastName(contact.getLastName());
				contactDTO.setEmail(contact.getEmail());
				contactDTO.setCompany(contact.getCompany());
				contactDTO.setAddress(contact.getAddress());
				contactDTO.setCountry(contact.getCountry());
				if (contact.getSource().getStatusValue().equalsIgnoreCase("other"))
					contactDTO.setOtherSourcetype(contact.getOtherSourcetype());
				contactDTO.setSource(contact.getSource().getStatusValue());
				contactDTO.setOtherSourcetype(contact.getOtherSourcetype());
				contactDTO.setWebsiteURL(contact.getWebsiteURL());
				contactDTO.setContactDestination(contact.getContactDestination());
				contactDTO.setContactDepartment(contact.getContactDepartment());
				contactDTO.setContactCreatedBy(contact.getContactCreatedBy().getUsername());
				contactDTO.setContactCreatedByName(contact.getContactCreatedBy().getUserName());
				contactDTO.setContactCreatedByEmail(contact.getContactCreatedBy().getEmail());
				contactDTO.setDate(contact.getDate());
				contactDTO.setStageDate(contact.getStageDate());
				contactDTO.setMobileNumber(contact.getMobileNumber());
				return contactDTO;
			}).collect(Collectors.toList());

			return contactDTOs;
		}
	}

	@Override
	public List<ContactDTO> GetAllContactOnContactCreatedBy(String contactCreatedBy) {
		logger.info("Fetching contacts created by user...");

		if (contactCreatedBy == null || contactCreatedBy.isEmpty()) {
			throw new InvalidInput("Please provide a valid user ID.");
		}

		try {
			User user = userRepository.findById(contactCreatedBy)
					.orElseThrow(() -> new DataNotFoundException("User not found."));

			List<Contact> findAll = contactRepository.findAll().stream()
					.filter(contact -> contact.getContactCreatedBy().equals(user)).collect(Collectors.toList());

			List<ContactDTO> contactDTOs = findAll.stream().map(contact -> {
				ContactDTO contactDTO = new ContactDTO();
				contactDTO.setContactId(contact.getContactId());
				contactDTO.setLifeCycleStage(contact.getLifeCycleStage().getStatusValue());
				contactDTO.setFirstName(contact.getFirstName());
				contactDTO.setLastName(contact.getLastName());
				contactDTO.setEmail(contact.getEmail());
				contactDTO.setCompany(contact.getCompany());
				contactDTO.setAddress(contact.getAddress());
				contactDTO.setCountry(contact.getCountry());
				if (contact.getSource().getStatusValue().equalsIgnoreCase("other"))
					contactDTO.setOtherSourcetype(contact.getOtherSourcetype());
				contactDTO.setSource(contact.getSource().getStatusValue());
				contactDTO.setOtherSourcetype(contact.getOtherSourcetype());
				contactDTO.setWebsiteURL(contact.getWebsiteURL());
				contactDTO.setContactDestination(contact.getContactDestination());
				contactDTO.setContactDepartment(contact.getContactDepartment());
				contactDTO.setContactCreatedBy(contact.getContactCreatedBy().getUserId());
				contactDTO.setContactCreatedByName(contact.getContactCreatedBy().getUserName());
				contactDTO.setContactCreatedByEmail(contact.getContactCreatedBy().getEmail());
				contactDTO.setDate(contact.getDate());
				contactDTO.setStageDate(contact.getStageDate());
				contactDTO.setMobileNumber(contact.getMobileNumber());
				return contactDTO;
			}).collect(Collectors.toList());

			logger.info("Contacts fetched successfully created by user.");
			return contactDTOs;
		} catch (Exception e) {
			logger.severe("An error occurred while fetching contacts created by user: " + e.getMessage());
			throw e; // Rethrow the exception
		}
	}

	@Override
	public List<ContactDTO> getAllContactCreateddByMail(String contactCreatedBy) {
		logger.info("Fetching contacts created by user...");

		if (contactCreatedBy == null || contactCreatedBy.isEmpty()) {
			throw new InvalidInput("Please provide a valid mail ID.");
		}
		try {
			User user = userRepository.findAll().stream().filter(e -> e.getEmail().equals(contactCreatedBy)).findFirst()
					.get();

			List<Contact> findAll = contactRepository.findAll().stream()
					.filter(contact -> contact.getContactCreatedBy().equals(user)).collect(Collectors.toList());

			List<ContactDTO> contactDTOs = findAll.stream().map(contact -> {
				ContactDTO contactDTO = new ContactDTO();
				contactDTO.setContactId(contact.getContactId());
				contactDTO.setLifeCycleStage(contact.getLifeCycleStage().getStatusValue());
				contactDTO.setFirstName(contact.getFirstName());
				contactDTO.setLastName(contact.getLastName());
				contactDTO.setEmail(contact.getEmail());
				contactDTO.setCompany(contact.getCompany());
				contactDTO.setAddress(contact.getAddress());
				contactDTO.setCountry(contact.getCountry());
				if (contact.getSource().getStatusValue().equalsIgnoreCase("other"))
					contactDTO.setOtherSourcetype(contact.getOtherSourcetype());
				contactDTO.setSource(contact.getSource().getStatusValue());
				contactDTO.setOtherSourcetype(contact.getOtherSourcetype());
				contactDTO.setWebsiteURL(contact.getWebsiteURL());
				contactDTO.setContactDestination(contact.getContactDestination());
				contactDTO.setContactDepartment(contact.getContactDepartment());
				contactDTO.setContactCreatedBy(contact.getContactCreatedBy().getUserId());
				contactDTO.setContactCreatedByName(contact.getContactCreatedBy().getUserName());
				contactDTO.setContactCreatedByEmail(contact.getContactCreatedBy().getEmail());
				contactDTO.setDate(contact.getDate());
				contactDTO.setStageDate(contact.getStageDate());
				contactDTO.setMobileNumber(contact.getMobileNumber());
				return contactDTO;
			}).collect(Collectors.toList());

			logger.info("Contacts fetched successfully created by user.");
			return contactDTOs;
		} catch (Exception e) {
			logger.severe("An error occurred while fetching contacts created by user: " + e.getMessage());
			throw e; // Rethrow the exception
		}
	}

	// Create a new contact
	@Override
	public ResponseEntity<String> CreateContact(Contact contact) {
		logger.info("Creating contact...");

		logger.info("Contact details: " + contact.toString());

		if (contact != null && !contact.toString().equals(new Contact().toString())) {
			// Check 'contact' status
			logger.info("Checking 'contact' status...");
			List<Status> allStatus = statusRepository.findAll().stream()
					.filter(e -> e.getStatusValue().toLowerCase().equals("contact")).collect(Collectors.toList());

			if (allStatus.size() > 1) {
				logger.warning("Multiple 'contact' statuses found.");
				throw new InternalError("Something went wrong.");
			} else if (allStatus.isEmpty()) {
				logger.warning("Initial 'contact' status not found.");
				throw new NullDataException("Contact can't be created, initial status not found.");
			} else {
				contact.setLifeCycleStage(allStatus.get(0));
				logger.info("'Contact' status set.");
			}

			// Validate first name
			logger.info("Validating first name...");
			if (contact.getFirstName() == null || contact.getFirstName().equals("")) {
				logger.warning("First name is missing.");
				throw new NullDataException("Please enter first name.");
			}

			// Validate source
			logger.info("Validating source...");
			if (contact.getSource() == null || contact.getSource().getStatusValue() == null
					|| contact.getSource().getStatusValue().equals("")) {
				logger.warning("Source is missing.");
				throw new NullDataException("Please enter source.");
			} else {
				List<Status> sourceStatus = statusRepository.findAll().stream()
						.filter(e -> e.getStatusType().equalsIgnoreCase("Contact_Source") && e.getStatusValue()
								.toLowerCase().equals(contact.getSource().getStatusValue().toLowerCase()))
						.collect(Collectors.toList());

				if (sourceStatus.size() > 1) {
					logger.warning("Multiple matching source statuses found.");
					throw new InternalError("Something went wrong.");
				} else if (sourceStatus.isEmpty()) {
					logger.warning("Source not found.");
					throw new DataNotFoundException("Source Not Found.");
				} else {
					if (contact.getSource().getStatusValue().toLowerCase().equals("other")) {
						if (contact.getOtherSourcetype() == null || contact.getOtherSourcetype().equals("")) {
							logger.warning("Other source type is missing.");
							throw new InvalidInput("Please enter source manually.");
						}
					} else {
						contact.setOtherSourcetype(null);
					}
					contact.setSource(sourceStatus.get(0));
					logger.info("Source validated and set.");
				}
			}
			logger.info("Validating contact creator...");
			if (contact.getContactCreatedBy() == null || contact.getContactCreatedBy().getUserId() == null
					|| contact.getContactCreatedBy().getUserId().equals("")) {
				logger.warning("Contact creator information is missing.");
				throw new NullDataException("Please enter who created.");
			} else {
				contact.setContactCreatedBy(userRepository.findById(contact.getContactCreatedBy().getUserId())
						.orElseThrow(() -> new InvalidInput("User not present.")));

			}

			// Validate mobile number
			logger.info("Validating mobile number...");
			if (contact.getMobileNumber() <= 999999999) {
				logger.warning("Invalid mobile number.");
				throw new InvalidInput("Please enter valid mobile number.");
			}

			logger.info("All validations successful. Creating contact...");

			contact.setDate(LocalDate.now());
			contact.setStageDate(LocalDate.now());

			Contact savedContact = contactRepository.save(contact);

			logger.info("Contact created with ID: " + savedContact.getContactId());
			return new ResponseEntity<>("Contact added. Your contact ID is: " + savedContact.getContactId(),
					HttpStatus.OK);
		} else {
			logger.warning("Data is empty or invalid.");
			throw new NullDataException("Data can't be empty. Please check it.");
		}
	}

	// Update a contact by contact ID
	@Override
	public ResponseEntity<String> UpdateContactByContactId(Contact contact, String contactId) {
		logger.info("Updating contact by contact ID...");

		if (contactId == null || contactId.equals("")) {
			logger.warning("Contact ID is missing.");
			throw new NullDataException("Please provide contact ID to get account details.");
		}

		if (contact != null && !contact.toString().equals(new Contact().toString())) {
			logger.info("Updating contact fields...");

			Contact contactFromDatabase = contactRepository.findById(contactId)
					.orElseThrow(() -> new DataNotFoundException("No account available with contact ID: " + contactId));

			if (contact.getFirstName() != null && !contact.getFirstName().equals("")) {
				logger.info("Updating first name...");
				contactFromDatabase.setFirstName(contact.getFirstName());
			}

			if (contact.getLastName() != null && !contact.getLastName().equals("")) {
				logger.info("Updating last name...");
				contactFromDatabase.setLastName(contact.getLastName());
			}
			if (contact.getEmail() != null && !contact.getEmail().equals("")) {
				logger.info("Updating email...");
				if (contactFromDatabase.getEmail().equals(contact.getEmail())) {
					logger.warning("Attempted to update email with existing value.");
					throw new InvalidInput("You have entered your existing email. Please enter an updated email.");
				} else {
					contactFromDatabase.setEmail(contact.getEmail());
					logger.info("Email updated.");
				}
			}

			if (contact.getCompany() != null && !contact.getCompany().equals("")) {
				logger.info("Updating company...");
				contactFromDatabase.setCompany(contact.getCompany());
				logger.info("Company updated.");
			}

			if (contact.getAddress() != null && !contact.getAddress().equals("")) {
				logger.info("Updating address...");
				contactFromDatabase.setAddress(contact.getAddress());
				logger.info("Address updated.");
			}

			if (contact.getCountry() != null && !contact.getCountry().equals("")) {
				logger.info("Updating country...");
				contactFromDatabase.setCountry(contact.getCountry());
				logger.info("Country updated.");
			}

			if (contact.getWebsiteURL() != null && !contact.getWebsiteURL().equals("")) {
				logger.info("Updating website URL...");
				contactFromDatabase.setWebsiteURL(contact.getWebsiteURL());
				logger.info("Website URL updated.");
			}

			if (contact.getContactDestination() != null && !contact.getContactDestination().equals("")) {
				logger.info("Updating contact destination...");
				contactFromDatabase.setContactDestination(contact.getContactDestination());
				logger.info("Contact destination updated.");
			}

			if (contact.getContactDepartment() != null && !contact.getContactDepartment().equals("")) {
				logger.info("Updating contact department...");
				contactFromDatabase.setContactDepartment(contact.getContactDepartment());
				logger.info("Contact department updated.");
			}

			if (contact.getMobileNumber() > 999999999) {
				logger.info("Updating mobile number...");
				contactFromDatabase.setMobileNumber(contact.getMobileNumber());
				logger.info("Mobile number updated.");
			}

			contactRepository.save(contactFromDatabase);

			logger.info("Contact updated, contact ID: " + contactId);
			return new ResponseEntity<>("Contact updated, contact ID: " + contactId, HttpStatus.OK);
		} else {
			logger.warning("Data is empty or invalid.");
			throw new NullDataException("Data can't be empty. Please check it.");
		}
	}

	// this is for deleting the contact corresponding to the provided contact id.
	@Override
	public ResponseEntity<String> deleteContactByContactId(String contactId) {
		logger.info("Deleting contact by contact ID...");

		if (contactId == null || contactId.equals("")) {
			logger.warning("Contact ID is missing.");
			throw new NullDataException("Please provide contact ID of the account you want to delete.");
		} else {
			logger.info("Fetching contact by contact ID: " + contactId);
			Contact contact = contactRepository.findById(contactId)
					.orElseThrow(() -> new DataNotFoundException("No account available with contact ID: " + contactId));

			if (contact != null) {
				logger.info("Deleting contact...");
				contactRepository.deleteById(contactId);
				logger.info("Contact deleted, contact ID: " + contactId);
				return new ResponseEntity<>("Contact deleted.", HttpStatus.OK);
			} else {
				logger.warning("Contact not found.");
				throw new DataNotFoundException("No account available with contact ID: " + contactId);
			}
		}
	}

	@Override
	public ResponseEntity<String> addContactsFromExcel(MultipartFile file, String userId) {
		logger.info("Adding contacts from Excel...");

		try {
			List<Contact> listOfContact = contactdatafrom.convertExcelToListOfContact(file.getInputStream(), userId);

			if (listOfContact.isEmpty()) {
				logger.warning("No data found in the Excel file.");
				throw new DataNotFoundException("Data not found.");
			} else {
				logger.info("Saving contacts to the database...");
				contactRepository.saveAll(listOfContact);
				logger.info("Data added.");
				return new ResponseEntity<>("Data added.", HttpStatus.OK);
			}
		} catch (IOException e) {
			logger.severe("Exception occurred while processing Excel file: " + e.getMessage());
			throw new InternalError("Something went wrong.");
		}
	}
}