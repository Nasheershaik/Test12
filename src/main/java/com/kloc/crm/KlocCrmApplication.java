package com.kloc.crm;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.kloc.crm.Entity.Notification;
import com.kloc.crm.Entity.StaticData;
import com.kloc.crm.Entity.User;
import com.kloc.crm.Repository.NotificationRepo;
import com.kloc.crm.Repository.StatusRepo;
import com.kloc.crm.Repository.UserRepository;


@SpringBootApplication
@EnableScheduling
public class KlocCrmApplication {
	public static void main(String[] args) {
		SpringApplication.run(KlocCrmApplication.class, args);
	}
}

@Component
class DataInsertionRunner implements CommandLineRunner {
	@Autowired
	private final StaticData statusService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private StatusRepo statusRepository;

	public DataInsertionRunner(StaticData statusService, NotificationRepo notificationRepo) {
		super();
		this.statusService = statusService;
		this.notificationRepo = notificationRepo;
	}

	@Autowired
	private NotificationRepo notificationRepo;

	@Override
	public void run(String... args) {
		String filePath ="./stat.txt";
		statusService.insertStatusFromFile(filePath);
		List<User> users	=userRepository.findAll();
		if(users.isEmpty()) {
			User user = new User();
			user.setUserId("user_0001");
			user.setUserName("Abhisarika Das");
			user.setEmail("abhisarikadas@kloctechnologies.com");
			user.setPassword(passwordEncoder.encode("abhisarika@kloctechnologies"));
			user.setRole(statusRepository.findByStatusValue("Administrator"));
			user.setMobileNo(9009076543l);
			user.setAltMobileNo(9992678420l);
			user.setStatus(statusRepository.findByStatusValue("Active"));
			User usern	=userRepository.save(user);
			user.setReportingTo(usern);
			userRepository.save(user);	
		}
		//List<Notification> notifications = notificationRepo.findAll();
		if (!notificationRepo.existsByNotificationTemplateAndRoleAndSubject(
		        "Sample Template",
		        "Sample Role",
		        "Sample Subject")&& notificationRepo.count()<3) {
		    Notification notification = new Notification();
		    notification.setNotificationId("notification_0001");
		    notification.setNotificationTemplate("Hello %s,\r\n"
		    		+ "I trust you're doing well. We noticed that your task %s for the Contact %s is not updated for this followUp date:%s. To ensure our workflow remains smooth, kindly update the task status at your earliest convenience.\r\n"
		    		+ "Thank You !");
		    notification.setRemindBefore(LocalDate.now().plusDays(1));
		    notification.setSubject("Sample Subject");
		    notification.setRole("Sample Role");
		    notification.setNotificationType(statusRepository.findByStatusValue("statuschangeTemplate"));
		    notificationRepo.save(notification);

		    Notification notification1 = new Notification(); 
		    notification1.setNotificationId("notification_0002"); 
		    notification1.setNotificationTemplate("Hello %s,\r\n"
		    		+ "I trust you're doing well. We noticed that your task %s for the Contact %s is currently overdue. To ensure our workflow remains smooth, kindly update the task status at your earliest convenience.\r\n"
		    		+ "Thank You !");
		    notification1.setRemindBefore(LocalDate.now().plusDays(2));
		    notification1.setSubject("Sample Subject1");
		    notification1.setRole("Sample Role1");
		    notification1.setNotificationType(statusRepository.findByStatusValue("OverDueTemplate"));
		    notificationRepo.save(notification1);

		    Notification notification2 = new Notification(); 
		    notification2.setNotificationId("notification_0003");
		    notification2.setNotificationTemplate("Dear %s,\r\n"
		    		+ "I hope this message finds you well. This is a gentle reminder regarding the task assigned to you for the Contact %s. The Follow-Up date for %s is %s.\r\n"
		    		+ "Thank You !");
		    notification2.setRemindBefore(LocalDate.now().plusDays(3));
		    notification2.setSubject("Sample Subject2");
		    notification2.setRole("Sample Role2");
		    notification2.setNotificationType(statusRepository.findByStatusValue("followupTemplate"));
		    notificationRepo.save(notification2);
		}


	}
}
