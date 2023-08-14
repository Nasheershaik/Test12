package com.kloc.crm.Service.ServiceImpl;
/**
 * @Author :Nasheer
 *@Date:06-July-2023
 *@FileName:NotificationServiceImpl.java
 */


import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.kloc.crm.Entity.Notification;
import com.kloc.crm.Entity.Status;
import com.kloc.crm.Exception.DataNotFoundException;
import com.kloc.crm.Exception.InvalidInput;
import com.kloc.crm.Repository.NotificationRepo;
import com.kloc.crm.Repository.StatusRepo;
import com.kloc.crm.Service.NotificationService;

/**
 * The NotificationServiceImpl class implements the NotificationService interface and provides
 * methods to interact with notifications in the CRM system.
 */

@Service 
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepo notificationRepo;
    
    @Autowired
    private StatusRepo statusRepo;
    
    private final List<Notification> allNotifications;
    
    /**
     * Constructs a new NotificationServiceImpl with the specified NotificationRepo.
     * @param notificationRepo The NotificationRepo used to interact with notifications.
     */
    public NotificationServiceImpl(NotificationRepo notificationRepo) {
        this.allNotifications = notificationRepo.findAll();
		this.notificationRepo = notificationRepo;
    }
    
    /**
     * Retrieves all notifications.
     * @return A list of all notifications.
     */
    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepo.findAll();
    }
    
    /**
     * Saves a notification.
     * @param notification The notification to save.
     * @return 
     * @return The saved notification.
     */
    @Override
    public Notification saveNotification(Notification notification) {
        if (notification.getSubject() == null || notification.getSubject().isEmpty()) {
            throw new InvalidInput("Subject cannot be empty.");
        }

        if (notification.getRemindBefore() == null) {
            throw new InvalidInput("RemindBefore date cannot be empty.");
        }

        if (notification.getRole() == null || notification.getRole().isEmpty()) {
            throw new InvalidInput("Role cannot be empty.");
        }
        if(notification.getNotificationType().equals(null)||notification.getNotificationType().getStatusValue().isEmpty())
        
        {
        	throw new InvalidInput("specify the notification type");
        }
        Status status=statusRepo.findByStatusValue(notification.getNotificationType().getStatusValue());
        	notification.setNotificationType(status);
        
        return  notificationRepo.save(notification);
    }



    /**
     * Retrieves a notification by its ID.
     * @param notificationId The ID of the notification to retrieve.
     * @return The retrieved notification.
     */
    @Override
    public Notification findByid(String notificationId) {
     return notificationRepo.findById(notificationId).orElseThrow(()-> new DataNotFoundException("notificationID is wrong"));
    }
    
    /**
     * Deletes a notification by its ID.
     * @param notificationId The ID of the notification to delete.
     * @return The deleted notification, or null if the notification was not found.
     */
    @Override
    public Notification DeleteByid(String notificationId) {
        Notification notification =  notificationRepo.findById(notificationId).orElseThrow(()-> new DataNotFoundException("notificationID is wrong"));
        if (notification != null) {
            notificationRepo.delete(notification);
        }
        return notification;
    }
    @Override
    public List<Notification> getNotificationsByRemindBefore(LocalDate remindBefore) {
        List<Notification> allNotifications = notificationRepo.findAll();
        // Use Java Streams to filter the notifications based on remindBefore
        List<Notification> filteredNotifications = allNotifications.stream()
                .filter(notification -> notification.getRemindBefore().isEqual(remindBefore))
                .collect(Collectors.toList());
        return filteredNotifications;
    }
    @Override
    public Notification getNotificationTemplatesByRoleAndType(String role, String notificationtype) {
        return notificationRepo.findAll().stream()
                .filter(notification -> notification.getRole().equals(role) && notification.getNotificationType().getStatusValue().equals(notificationtype))
                .findFirst().orElseThrow(()->new DataNotFoundException("Wrong Value for Type or Role"));
    }


}