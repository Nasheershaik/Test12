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
    @Value("${spring.mail.username}")
    private String sender;

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
                    sendFollowUpMail(task);
                   
                }
            }
        });
	}
    public String sendFollowUpMail(Task task)
    {
    	try {
            // Creating a simple mail message
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(task.getAssignedManager().getEmail());
            mailMessage.setCc(task.getSalesPerson().getUser().getEmail());
            mailMessage.setText(
                    "Hi.." + task.getSalesPerson().getUser().getUserName() + "\n" + "You have meeting on this date."+LocalDate.now().plusDays(2)+"for this task id"+task.getTaskId() + "\n"
                            + "Thanks & Regards" + "\n" + "Ritesh Singh");
            mailMessage.setSubject("Reminder: FollowUp");

            // Sending the mail
            javaMailSender.send(mailMessage);
            Email email = new Email();
            email = new Email();
            email.setTask(task); // Assuming there's a constructor for Task that accepts taskId
            email.setEmailType("In Progress");
            email.setToAddress(task.getAssignedManager().getEmail());
            email.setEmailMsg("follow up");
        
        email.setEmailDate(LocalDate.now());
        emailRepository.save(email);

            return "Reminder Mail Sent Successfully...";
        } catch (Exception e) {
            return "Error while Sending Reminder Mail";
        }
    }
}
