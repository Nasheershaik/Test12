/**
 * @author: windows
 * @Created_Date:Jul 26, 2023
 * @File_Name:ContactDataFromExcel.java
 *
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

/**
 * @author windows
 *
 */
@Component
public class ContactDataFromExcel
{

	@Autowired
	private StatusRepo statusRepository;

	// Check the file is excel type or not
	public boolean checkExcelFormate(MultipartFile file)
	{
		return file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	}

	// It converts excel data to the list of contacts
	public List<Contact> convertExcelToListOfContact(InputStream excelData) throws IOException
	{
		List<Contact> listOfContact = new ArrayList<>();
		XSSFSheet sheet = new XSSFWorkbook(excelData).getSheet("Contacts");
		List<Status> contactStatus = statusRepository.findAll().stream()
				.filter(e -> (e.getTableName().toLowerCase().equals("contact"))
						&& (e.getStatusType().toLowerCase().equals("contact"))
						&& (e.getStatusValue().toLowerCase().equals("contact")))
				.collect(Collectors.toList());
		if (contactStatus.size() > 1)
			throw new InternalError("something went wrong.");
		else if (contactStatus.isEmpty())
			throw new DataNotFoundException("Initial status not found.");
		else
		{
			int rowNumber = 0;
			Iterator<Row> iterator = sheet.iterator();
			while (iterator.hasNext()) 
			{
				Row row = iterator.next();
				if (rowNumber == 0)
				{
					rowNumber++;
					continue;
				}
				Contact contact = new Contact();
				Iterator<Cell> cells = row.iterator();
				int cellId = 0;
				while (cells.hasNext()) 
				{
					Cell cell = cells.next();
					switch (cellId)
					{
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
							contact.setSource(null);
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
							contact.setContactCreatedBy(null);
							break;
						case 11:
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
				contact.setLifeCycleStage(contactStatus.get(0));
				contact.setDate(LocalDate.now());
				contact.setStageDate(LocalDate.now());
				listOfContact.add(contact);
				System.out.println(listOfContact.toString());
			}
			return listOfContact;
		}
	}
}
