package com.kloc.crm.Repository;
import java.time.LocalDate;

/**
 * @Author :Nasheer
 *@Date :06-July-2023
 *@FileName:NotificationRepo.java
 */
import org.springframework.data.jpa.repository.JpaRepository;

import com.kloc.crm.Entity.*;




public interface NotificationRepo extends JpaRepository<Notification,String >{
	
	
boolean existsByNotificationTemplateAndRoleAndSubject
	(String notificationTemplate,String role,String subject);
	
	Notification findByNotificationType(Status notificationType);
	
}

