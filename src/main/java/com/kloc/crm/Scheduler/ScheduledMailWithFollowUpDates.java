package com.kloc.crm.Scheduler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Hibernate;
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
public class ScheduledMailWithFollowUpDates {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private EmailRepo emailRepository;

    @Value("${spring.mail.username}")
    private String sender;

    private Map<Task, LocalDate> tasksWithFollowUpDates = new HashMap<>();

    // Method to schedule email for a specific task with its follow-up date
    public void scheduleEmailForTask(Task task, LocalDate followUpDate) {
        tasksWithFollowUpDates.put(task, followUpDate);
    }

    @Scheduled(cron = "0 0 7 * * ?")
    public void sendScheduledEmails() {
        LocalDate currentDate = LocalDate.now();

        for (Map.Entry<Task, LocalDate> entry : tasksWithFollowUpDates.entrySet()) {
            Task task = entry.getKey();
            LocalDate followUpDate = entry.getValue();

            if (followUpDate.isEqual(currentDate.plusDays(2))) {
                try {
                    // Creating a simple mail message
                    SimpleMailMessage mailMessage = new SimpleMailMessage();
                    User user = task.getSalesPerson().getUser();
                    Hibernate.initialize(user);
                    // Setting up necessary details
                    mailMessage.setFrom(sender);
                    mailMessage.setTo(user.getEmail());
                    mailMessage.setCc(task.getAssignedManager().getEmail());
                    mailMessage.setText(
                            "Hi " + task.getAssignedManager().getUserName() + ",\n"
                                    + "You have a meeting scheduled on " + followUpDate + ".\n"
                                    + "Thanks & Regards,\n"
                                    + "Ritesh Singh");
                    mailMessage.setSubject("Reminder");

                    // Sending the mail
                    javaMailSender.send(mailMessage);

//                    Email email = new Email();
//                    email.setEmailDate(LocalDate.now());
//                    emailRepository.save(email);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
