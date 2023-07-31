package com.kloc.crm.Service;
import java.time.LocalDate;
/**
 * @Author :Nasheer
 *@Date :06-July-2023
 *@FileName:NotificationService.java
 */
import java.util.List;

import org.springframework.stereotype.Service;

import com.kloc.crm.Entity.*;

@Service
public interface NotificationService {
	List<Notification> getAllNotifications();
	
	Notification saveNotification(Notification notification);
	
	Notification findByid(String  NotificationId);
	
	Notification DeleteByid(String  NotificationId);
	
	List<Notification> getNotificationsByRemindBefore(LocalDate remindBefore);

	List<Notification> getNotificationTemplatesByRole(String role);
	
	
}