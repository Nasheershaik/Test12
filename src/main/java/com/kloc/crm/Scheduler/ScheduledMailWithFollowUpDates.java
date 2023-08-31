package com.kloc.crm.Scheduler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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
import com.kloc.crm.Entity.TaskSub;
import com.kloc.crm.Entity.User;
import com.kloc.crm.Repository.EmailRepo;
import com.kloc.crm.Repository.NotificationRepo;
import com.kloc.crm.Repository.StatusRepo;
import com.kloc.crm.Repository.TaskRepository;
import com.kloc.crm.Repository.TaskSubRepository;
@Component
public class ScheduledMailWithFollowUpDates {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private EmailRepo emailRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskSubRepository taskSubRepository;
    @Autowired
    private NotificationRepo notificationRepository;
    @Autowired
    private StatusRepo statusRepository;
    @Value("${spring.mail.username}")
    private String sender;
    /**
     * Scheduled method to send follow-up reminder emails for tasks that are due for follow-up within 2 days.
     * This method is triggered by a cron expression that runs daily at 7:00 AM.
     * It retrieves all tasks from the task repository and iterates through each task's associated task submissions.
     * For each task submission, it checks if the task's status is not "completed" or "Transferred".
     * If the task has a follow-up date that is exactly 2 days from the current date, a follow-up email is sent using the sendFollowUpMail() method.
     * The method ensures that timely reminders are sent for tasks requiring follow-up in the next 2 days.
     */
    @Scheduled(cron="0 0 7 * * ?")
	public void schMailBasedOnFollowUp()
	{
		taskRepository.findAll().forEach(task -> {
            List<TaskSub> taskSubs = taskSubRepository.findByTask(task);
            if (!taskSubs.isEmpty()) {
                TaskSub lastTaskSub = taskSubs.get(taskSubs.size() - 1);
                if (!lastTaskSub.getTaskStatus().getStatusValue().equalsIgnoreCase("completed")&&!lastTaskSub.getTaskStatus().getStatusValue().toLowerCase().equals("Transferred")
                        && lastTaskSub.getFollowUpDate() != null
                        && LocalDate.now().plusDays(2).isEqual(lastTaskSub.getFollowUpDate())) {
                    sendFollowUpMail(task,lastTaskSub);
                   
                }
            }
        });
	}
    public String sendFollowUpMail(Task task,TaskSub taskSub)
    {
    	try {
            // Creating a simple mail message
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(task.getSalesPerson().getUser().getEmail());
            mailMessage.setCc(task.getAssignedManager().getEmail());
            String st= notificationRepository.findByNotificationType(statusRepository.findByStatusValue("followupTemplate")).getNotificationTemplate();
            String text=String.format(st,task.getSalesPerson().getUser().getUserName(),task.getContactSub().getContactId().getFirstName(),task.getTaskId(),taskSub.getFollowUpDate());
            mailMessage.setText(text);
            mailMessage.setSubject("Friendly Reminder: Task Update Needed for "+task.getTaskId()+" Assigned to "+task.getContactSub().getContactId().getFirstName());

            // Sending the mail
            javaMailSender.send(mailMessage);
            Email email = new Email();
            email = new Email();
            email.setTask(task); // Assuming there's a constructor for Task that accepts taskId
            email.setEmailType("In Progress");
            email.setToAddress(task.getAssignedManager().getEmail());
            email.setEmailMsg(text);
        
        email.setEmailDate(LocalDate.now());
        emailRepository.save(email);

            return "Reminder Mail Sent Successfully...";
        } catch (Exception e) {
            return "Error while Sending Reminder Mail";
        }
    }
}
