package com.kloc.crm.Scheduler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
public class ScheduledMailWithDueDates {
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
     * Scheduled method to send reminder emails based on overdue task due dates.
     * This method is triggered by a cron expression that runs daily at 7:00 AM.
     * It retrieves all tasks from the task repository and iterates through each task's associated task submissions.
     * For each task submission, it checks if the task's status is not "completed" or "Transferred".
     * If the task's due date has passed, an "OverDue" email reminder is sent if the conditions for email sending are met.
     * The task's ID and "OverDue" email type are used to determine if it's time to send the email.
     * If conditions are satisfied, a simple email is sent using the sendSimpleMail() method.
     */
    @Scheduled(cron = "0 0 7 * * ?")
	public void scheduledMailBasedOnDueDates() {
	    taskRepository.findAll().stream().forEach(e -> {
	        List<TaskSub> tasksub = taskSubRepository.findByTask(e);
	        if (!tasksub.isEmpty()) {
	            TaskSub lastTaskSub = tasksub.get(tasksub.size() - 1);
	            if (!lastTaskSub.getTaskStatus().getStatusValue().toLowerCase().equals("completed")&&!lastTaskSub.getTaskStatus().getStatusValue().toLowerCase().equals("Transferred")) {
	                // Check if the due date has passed
	                if (e.getDueDate().isBefore(LocalDate.now())) {
	                    String emailType = "OverDue"; // Set the emailType here

	                    if (isTimeForSendingEmail(e.getTaskId(), emailType)) {
	                        // Get the task and send the email
	                        sendSimpleMail(e);
	                    }
	                }
	            }
	        }
	    });
	}

	private boolean isTimeForSendingEmail(String taskId, String emailType) {
	    Email email = emailRepository.findFirstByTaskTaskIdAndEmailTypeOrderByEmailDateDesc(taskId, emailType);
	    if (email == null || email.getEmailDate().plusDays(7).isBefore(LocalDate.now())) {
	        if (email == null) {
	            email = new Email();
	            email.setTask(taskRepository.findById(taskId).get()); // Assuming there's a constructor for Task that accepts taskId
	            email.setEmailType(emailType);
	            email.setToAddress(taskRepository.findById(taskId).get().getAssignedManager().getEmail());
	            email.setEmailMsg("The Task is Overdue for the salesperson "+taskRepository.findById(taskId).get().getSalesPerson().getUser().getUserName()+".Please take further action on it.");
	        }
	        email.setEmailDate(LocalDate.now());
	        emailRepository.save(email);
	        return true;
	    }
	    return false;
	}
    public String sendSimpleMail(Task task)
    {
    	try {
            // Creating a simple mail message
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(task.getAssignedManager().getEmail());
            mailMessage.setCc(task.getSalesPerson().getUser().getEmail());
            String st= notificationRepository.findByNotificationType(statusRepository.findByStatusValue("OverDueTemplate")).getNotificationTemplate();
            String text=String.format(st,task.getSalesPerson().getUser().getUserName(),task.getTaskId(),task.getContactSub().getContactId().getFirstName() );
            mailMessage.setText(text);
            String st1= notificationRepository.findByNotificationType(statusRepository.findByStatusValue("OverDueTemplate")).getSubject();
            String subject=String.format(st1,task.getTaskId(),task.getContactSub().getContactId().getFirstName());
            mailMessage.setSubject(subject);

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

