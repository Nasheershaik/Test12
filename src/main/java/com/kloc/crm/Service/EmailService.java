package com.kloc.crm.Service;
/**
 * @Author :Nasheer
 *@Date :06-July-2023
 *@FileName:EmailService
 */
import java.util.List;

import org.springframework.stereotype.Service;

import com.kloc.crm.Entity.*;

@Service
public interface EmailService {
	List<Email> getAllEmails();
	
	Email saveEmail(Email email);
	
	Email findByid(String  EmailId);
	
	Email DeleteByid(String  EmailId);

	boolean sendEmail(String email, String subject, String message);
	
	
	
}