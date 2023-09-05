package com.kloc.crm.Repository;

/**
 * @Author :Nasheer
 *@Date :06-July-2023
 *@FileName:NotificationRepo.java
 */
import org.springframework.data.jpa.repository.JpaRepository;

import com.kloc.crm.Entity.*;




public interface NotificationRepo extends JpaRepository<Notification,String >{


	boolean existsByNotificationTemplateAndSubject
	(String notificationTemplate,String subject);

	Notification findByNotificationType(Status notificationType);


}

