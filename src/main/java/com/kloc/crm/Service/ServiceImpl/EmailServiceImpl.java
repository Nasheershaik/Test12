package com.kloc.crm.Service.ServiceImpl;
/**
 * @Author :Nasheer
 *@Date :06-July-2023
 *@FileName:EmailServiceImpl.java
 */
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.kloc.crm.Entity.Email;
import com.kloc.crm.Exception.DataNotFoundException;
import com.kloc.crm.Repository.EmailRepo;
import com.kloc.crm.Service.EmailService;

/**
 * The EmailServiceImpl class implements the EmailService interface and provides
 * methods to interact with emails in the CRM system.
 */


@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private EmailRepo emailRepo;
    
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")	
    private String sender;

    /**
     * Constructs a new EmailServiceImpl with the specified EmailRepo.
     * @param emailRepo The EmailRepo used to interact with emails.
     */
    public EmailServiceImpl(EmailRepo emailRepo) {
        this.emailRepo = emailRepo;
    }

    /**
     * Retrieves all emails.
     * @return A list of all emails.
     */
    @Override
    public List<Email> getAllEmails() {
        return emailRepo.findAll();
    }

    /**
     * Retrieves an email by its ID.
     * @param emailId The ID of the email to retrieve.
     * @return The retrieved email.
     */
    @Override
    public Email findByid(String emailId) {
        return emailRepo.findById(emailId).orElseThrow(()-> new DataNotFoundException("wrong emailID"));
        
    }

    /**
     * Saves an email.
     * @param email The email to save.
     * @return The saved email.
     */
    @Override
    public Email saveEmail(Email email) {
        return emailRepo.save(email);
    }

    /**
     * Deletes an email by its ID.
     * @param emailId The ID of the email to delete.
     * @return The deleted email, or null if the email was not found.
     */
    @Override
    public Email DeleteByid(String emailId) {
       return  emailRepo.findById(emailId).orElseThrow(()-> new DataNotFoundException("wrong emailID"));
       
    }

    @Override
	public boolean sendEmail(String to, String subject, String body)
	{
		try {
				SimpleMailMessage mailMessage = new SimpleMailMessage();
				mailMessage.setFrom(sender);
				mailMessage.setTo(to);
				mailMessage.setSubject(subject);
				mailMessage.setText(body);
				
				
				javaMailSender.send(mailMessage);
				return true;
			}catch(Exception e)
			{
				return false;
			}
	}










}
