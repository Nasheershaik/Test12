package com.kloc.crm.Controller;
/**
 * @Author :Nasheer
 *@Date :06-July-2023
 *@FileName:EmailController.java
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.kloc.crm.Entity.Email;
import com.kloc.crm.Service.EmailService;

/**
 * The EmailController class handles HTTP requests related to emails.
 * It provides endpoints for retrieving, creating, updating, and deleting emails.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/app/emails")
public class EmailController {

    @Autowired
    private final EmailService emailService;

    /**
     * Constructs a new EmailController with the specified EmailService.
     * @param emailService The EmailService used to interact with emails.
     */
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * Retrieves all emails.
     * @return ResponseEntity containing a list of emails and the HTTP status code.
     */
    @GetMapping("/getallemails")
    public ResponseEntity<List<Email>> getAllEmails() {
        List<Email> emails = emailService.getAllEmails();
        return new ResponseEntity<>(emails, HttpStatus.OK);
    }

    /**
     * Creates a new email.
     * @param email The email to be created, received in the request body.
     * @return ResponseEntity containing the created email and the HTTP status code.
     */
    @PostMapping("/createemail")
    public ResponseEntity<Email> createEmail(@RequestBody Email email) {
        Email savedEmail = emailService.saveEmail(email);
        return new ResponseEntity<>(savedEmail, HttpStatus.CREATED);
    }

    /**
     * Updates an email by its ID.
     * @param emailId The ID of the email to update, received as a path variable.
     * @param updatedEmail The updated email data received in the request body.
     * @return ResponseEntity containing the updated email and the HTTP status code.
     */
    @PutMapping("/{emailId}")
    public ResponseEntity<Email> updateEmailById(@PathVariable String emailId, @RequestBody Email updatedEmail) {
        Email existingEmail = emailService.findByid(emailId);
        if (existingEmail != null) {
            existingEmail.setToAddress(updatedEmail.getToAddress());
            existingEmail.setEmailDate(updatedEmail.getEmailDate());
            existingEmail.setEmailMsg(updatedEmail.getEmailMsg());
            Email updatedEmail1 = emailService.saveEmail(existingEmail);
            return new ResponseEntity<>(updatedEmail1, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves an email by its ID.
     * @param emailId The ID of the email to retrieve, received as a path variable.
     * @return ResponseEntity containing the retrieved email and the HTTP status code.
     */
    @GetMapping("/{emailId}")
    public ResponseEntity<Email> getEmailById(@PathVariable String emailId) {
        Email email = emailService.findByid(emailId);
        return new ResponseEntity<>(email, HttpStatus.OK);
    }

    /**
     * Deletes an email by its ID.
     * @param emailId The ID of the email to delete, received as a path variable.
     * @return ResponseEntity containing the deleted email and the HTTP status code.
     */
    @DeleteMapping("/deleteemail/{emailId}")
    public ResponseEntity<Email> deleteEmailById(@PathVariable String emailId) {
        Email deletedEmail = emailService.DeleteByid(emailId);
        return new ResponseEntity<>(deletedEmail, HttpStatus.OK);
    }
}
