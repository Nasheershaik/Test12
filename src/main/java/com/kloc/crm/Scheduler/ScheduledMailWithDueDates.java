package com.kloc.crm.Scheduler;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kloc.crm.Entity.Email;
import com.kloc.crm.Entity.Task;
import com.kloc.crm.Entity.User;
import com.kloc.crm.Repository.EmailRepo;

@Component
public class ScheduledMailWithDueDates {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private EmailRepo emailRepository;

    @Value("${spring.mail.username}")
    private String sender;
    public String sendSimpleMail(Task task)
    {
    	try {
            // Creating a simple mail message
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(task.getAssignedManager().getEmail());
            mailMessage.setCc(task.getSalesPerson().getUser().getEmail());
            mailMessage.setText(
                    "Hi.." + task.getSalesPerson().getUser().getUserName() + "\n" + "This is a reminder for an overdue task." + "\n"
                            + "Thanks & Regards" + "\n" + "Ritesh Singh");
            mailMessage.setSubject("Reminder: Overdue Task");

            // Sending the mail
            javaMailSender.send(mailMessage);
            Email email = new Email();
            email.setEmailDate(LocalDate.now());
            return "Reminder Mail Sent Successfully...";
        } catch (Exception e) {
            return "Error while Sending Reminder Mail";
        }
    }
}

