/**
 * This class is responsible for converting data from an Excel file to a list of Contact objects.
 * It checks the Excel file format, reads the data from the file, and converts it into Contact objects.
 * The converted list of contacts is returned for further processing.
 *
 * @author Ankush
 * @created_date Jul 26, 2023
 * @file_name ContactDataFromExcel.java
 */
package com.kloc.crm.FileData;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.kloc.crm.Entity.Contact;
import com.kloc.crm.Entity.Status;
import com.kloc.crm.Exception.DataNotFoundException;
import com.kloc.crm.Exception.InvalidInput;
import com.kloc.crm.Repository.StatusRepo;
import com.kloc.crm.Repository.UserRepository;

@Component
public class ContactDataFromExcel 
{

    @Autowired
    private StatusRepo statusRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * Checks if the given file is in the Excel format (XLSX).
     *
     * @param file The file to be checked
     * @return True if the file is in XLSX format, otherwise False
     */
    public boolean checkExcelFormate(MultipartFile file) 
    {
        return file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    /**
     * Converts Excel data to a list of Contact objects.
     *
     * @param excelData The InputStream containing the Excel data
     * @return A list of Contact objects extracted from the Excel data
     * @throws IOException If an IO exception occurs while reading the Excel data
     */
    public List<Contact> convertExcelToListOfContact(InputStream excelData, String userId) throws IOException 
    {
        List<Contact> listOfContact = new ArrayList<>();
        XSSFSheet sheet = new XSSFWorkbook(excelData).getSheet("Contacts");

        // Find the initial status for Contact
        List<Status> contactStatus = statusRepository.findAll().stream()
                .filter(e -> (e.getTableName().toLowerCase().equals("contact"))
                        && (e.getStatusType().toLowerCase().equals("contact"))
                        && (e.getStatusValue().toLowerCase().equals("contact")))
                .collect(Collectors.toList());

        // Ensure there is exactly one initial status for Contact
        if (contactStatus.size() > 1)
            throw new InternalError("Something went wrong.");
        else if (contactStatus.isEmpty())
            throw new DataNotFoundException("Initial status not found.");
        else 
        {
            int rowNumber = 0;
            Iterator<Row> iterator = sheet.iterator();
            while (iterator.hasNext())
            {
                Row row = iterator.next();

                // Skip the header row
                if (rowNumber == 0)
                {
                    rowNumber++;
                    continue;
                }
                // Create a new Contact object
                Contact contact = new Contact();
                Iterator<Cell> cells = row.iterator();
                int cellId = 0;
                while (cells.hasNext()) 
                {
                    Cell cell = cells.next();
                    switch (cellId) {
                        case 0:
                            if (cell.getCellType() != CellType.BLANK) 
                            {
                                if (cell.getCellType() != CellType.NUMERIC)
                                    contact.setFirstName(cell.getStringCellValue());
                                else
                                    throw new InvalidInput("No numbers in name.");
                            } else
                                throw new InvalidInput("Please enter name.");
                            break;
                        case 1:
                            contact.setLastName(cell.getStringCellValue());
                            break;
                        case 2:
                            contact.setEmail(cell.getStringCellValue());
                            break;
                        case 3:
                            contact.setCompany(cell.getStringCellValue());
                            break;
                        case 4:
                            contact.setAddress(cell.getStringCellValue());
                            break;
                        case 5:
                            contact.setCountry(cell.getStringCellValue());
                            break;
                        case 6:
                        	 if (cell.getCellType() != CellType.BLANK) 
                             {
                        		 String stringCellValue = cell.getStringCellValue();
                        		 if (stringCellValue == null || stringCellValue.equals(""))
                        		 {
									throw new InvalidInput("Source can not be empty.");
                        		 }
                        		 else
                        		 {
                        			 List<Status> allAvailableSource = statusRepository.findAll().stream().filter(e -> e.getStatusType().equalsIgnoreCase("Contact_Source") && e.getStatusValue().equalsIgnoreCase(stringCellValue)).toList();
                        			 if(allAvailableSource.isEmpty())
                        			 {
                        				 contact.setOtherSourcetype(stringCellValue);
                        				 contact.setSource(statusRepository.findAll().stream().filter(e -> e.getStatusType().equalsIgnoreCase("Contact_Source") && e.getStatusValue().equalsIgnoreCase("other")).findFirst().get());
                        			 }
                        			 else
                        				 contact.setSource(allAvailableSource.get(0));
                        		 }
                             }
                            break;
                        case 7:
                            contact.setWebsiteURL(cell.getStringCellValue());
                            break;
                        case 8:
                            contact.setContactDestination(cell.getStringCellValue());
                            break;
                        case 9:
                            contact.setContactDepartment(cell.getStringCellValue());
                            break;
                        case 10:
                            if (cell.getCellType() == CellType.NUMERIC)
                                contact.setMobileNumber((long) cell.getNumericCellValue());
                            else
                                throw new InvalidInput("Please enter proper mobile numbers");
                            break;
                        default:
                            break;
                    }
                    cellId++;
                }

                // Set the initial lifecycle stage and dates for the contact
                contact.setContactCreatedBy(userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("User not found")));
                contact.setLifeCycleStage(contactStatus.get(0));
                contact.setDate(LocalDate.now());
                contact.setStageDate(LocalDate.now());

                // Add the contact to the list
                listOfContact.add(contact);
            }
            return listOfContact;
        }
    }
}
