package com.kloc.crm.Scheduler;

import java.time.LocalDate;
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
import com.kloc.crm.Repository.EmailRepo;
import com.kloc.crm.Repository.NotificationRepo;
import com.kloc.crm.Repository.StatusRepo;
import com.kloc.crm.Repository.TaskRepository;
import com.kloc.crm.Repository.TaskSubRepository;

@Component
public class ScheduledMailBasedOnStatusChange 
{
		@Autowired
		private TaskRepository taskRepository;
		@Autowired
	    private JavaMailSender javaMailSender;
	    @Autowired
	    private EmailRepo emailRepository;
	    @Autowired
		private TaskSubRepository taskSubRepository;
	    @Autowired
	    private NotificationRepo notificationRepository;
	    @Autowired
	    private StatusRepo statusRepository;
	    @Value("${spring.mail.username}")
	    private String sender;

	    /**
	     * Scheduled method to send reminder emails based on status changes and follow-up dates of tasks.
	     * This method is triggered by a cron expression that runs daily at 7:00 AM.
	     * It retrieves all tasks from the task repository and iterates through each task's associated task submissions.
	     * For each task submission, it checks if the task's status is not "completed" or "Transferred",
	     * and if the follow-up date is not null and is before the current date.
	     * If these conditions are met, and the number of reminder emails sent for the specific follow-up date is less than 3,
	     * a pending email reminder is sent.
	     * The task, along with its corresponding last task submission, is used to compose the email.
	     */
	@Scheduled(cron="0 0 7 * * ?")
	public void schMailBasedOnStatusChange() {
        taskRepository.findAll().forEach(task -> {
            List<TaskSub> taskSubs = taskSubRepository.findByTask(task);
            if (!taskSubs.isEmpty()) {
                TaskSub lastTaskSub = taskSubs.get(taskSubs.size() - 1);
                if (!lastTaskSub.getTaskStatus().getStatusValue().equalsIgnoreCase("completed")&&!lastTaskSub.getTaskStatus().getStatusValue().toLowerCase().equals("Transferred")
                        && lastTaskSub.getFollowUpDate() != null
                        && lastTaskSub.getFollowUpDate().isBefore(LocalDate.now())) {
                    int sentReminders = emailRepository.countByTaskAndEmailType(task, "Pending "+lastTaskSub.getFollowUpDate());
                    if (sentReminders < 3) {
                        sendPendingMail(task,lastTaskSub);
                    }
                }
            }
        });
    }
	 public String sendPendingMail(Task task,TaskSub taskSub)
	    {
	    	try {
	            // Creating a simple mail message
	            SimpleMailMessage mailMessage = new SimpleMailMessage();

	            // Setting up necessary details
	            mailMessage.setFrom(sender);
	            mailMessage.setTo(task.getSalesPerson().getUser().getEmail());
	            mailMessage.setCc(task.getAssignedManager().getEmail());
	           String st= notificationRepository.findByNotificationType(statusRepository.findByStatusValue("statuschangeTemplate")).getNotificationTemplate();
	          String text= String.format(st,task.getSalesPerson().getUser().getUserName(),task.getTaskId(),task.getContactSub().getContactId().getFirstName(),taskSub.getFollowUpDate());
	            mailMessage.setText(text);
	            mailMessage.setSubject("Task Update: No Change in "+task.getTaskId()+" Status");

	            // Sending the mail
	            javaMailSender.send(mailMessage);
	            Email email = new Email();
	            email = new Email();
	            email.setTask(task); // Assuming there's a constructor for Task that accepts taskId
	            email.setEmailType("Pending "+taskSub.getFollowUpDate());
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
