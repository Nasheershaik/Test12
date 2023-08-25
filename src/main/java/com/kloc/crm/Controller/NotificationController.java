package com.kloc.crm.Controller;

import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.kloc.crm.Entity.Notification;
import com.kloc.crm.Service.NotificationService;

@CrossOrigin("*")
@RestController
@RequestMapping("/app/notifications")
public class NotificationController {

    @Autowired
    private final NotificationService notificationService;

    private static final Logger Log = LogManager.getLogger(NotificationController.class);

    /**
     * Constructs a new NotificationController with the specified NotificationService.
     * 
     * @param notificationService The NotificationService used to interact with notifications.
     */
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/getnotifications")
    public ResponseEntity<List<Notification>> getAllNotifications() {
        Log.debug("Attempting to retrieve all notifications.");
        List<Notification> notifications = notificationService.getAllNotifications();
        if (!notifications.isEmpty()) {
            Log.info("Retrieved all Notifications successfully.");
            return new ResponseEntity<>(notifications, HttpStatus.OK);
        } else {
            Log.error("No notifications found in the database.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/createnotification")
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        Log.debug("Attempting to create a new Notification.");
        Notification savedNotification = notificationService.saveNotification(notification);
        if (savedNotification != null) {
            Log.info("New Notification created successfully.");
            return new ResponseEntity<>(savedNotification, HttpStatus.CREATED);
        } else {
            Log.error("Failed to create a new Notification.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable String notificationId) {
        Log.debug("Attempting to retrieve notification with ID: " + notificationId);
        Notification notification = notificationService.findByid(notificationId);
        if (notification != null) {
            Log.info("Notification details retrieved successfully for ID: " + notificationId);
            return new ResponseEntity<>(notification, HttpStatus.OK);
        } else {
            Log.error("No Notification details found for ID: " + notificationId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{notificationId}")
    public ResponseEntity<Notification> updateNotificationById(@PathVariable String notificationId,
                                                               @RequestBody Notification updatedNotification) {
        Log.debug("Attempting to update notification with ID: " + notificationId);
        Notification existingNotification = notificationService.findByid(notificationId);
        if (existingNotification != null) {
            existingNotification.setSubject(updatedNotification.getSubject());
   //         existingNotification.setNotificationType(updatedNotification.getNotificationType());
            existingNotification.setNotificationTemplate(updatedNotification.getNotificationTemplate());
            existingNotification.setRemindBefore(updatedNotification.getRemindBefore());
            existingNotification.setRole(updatedNotification.getRole());
            Notification updatedNotification1 = notificationService.saveNotification(existingNotification);
            Log.info("Notification values updated successfully for ID: " + notificationId);
            return new ResponseEntity<>(updatedNotification1, HttpStatus.OK);
        } else {
            Log.error("Unable to update notification. No Notification found for ID: " + notificationId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deletenotification/{notificationId}")
    public ResponseEntity<Notification> deleteNotificationById(@PathVariable String notificationId) {
        Log.debug("Attempting to delete notification with ID: " + notificationId);
        Notification deletedNotification = notificationService.DeleteByid(notificationId);
        return new ResponseEntity<>(deletedNotification, HttpStatus.OK);
    }

    @GetMapping("/remindbefore/{remindBefore}")
    public ResponseEntity<List<Notification>> getNotificationsByRemindBefore(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate remindBefore) {
        Log.debug("Attempting to retrieve notifications by RemindBefore: " + remindBefore);
        List<Notification> notifications = notificationService.getNotificationsByRemindBefore(remindBefore);
        if (notifications.isEmpty()) {
            Log.info("No notifications found for RemindBefore: " + remindBefore);
            return ResponseEntity.noContent().build();
        } else {
            Log.info("Retrieved notifications successfully for RemindBefore: " + remindBefore);
            return ResponseEntity.ok(notifications);
        }
    }

    @GetMapping("/template/{notificationtype}/{role}")
    public ResponseEntity<Notification> getNotificationTemplateByRoleAndType(@PathVariable("notificationtype") String notificationtype,
    		@PathVariable("role") String role) {

    	return new ResponseEntity<Notification>(notificationService.getNotificationTemplatesByRoleAndType(role, notificationtype),HttpStatus.FOUND);
    	
   }
    @GetMapping("/getalltemplates")
    public List<Notification> getallnotificationTemplates(String notificationTemplate) {
       	return  notificationService.getAllTemplates(notificationTemplate);
    
    	
    }
    @GetMapping("gettemplatebytype/{notificationType}")
    public ResponseEntity<String> getalltemplatesbyType(@PathVariable ("notificationType") String notificationtype) {
    	return new ResponseEntity<String>(notificationService.getNotificationTemplatesByType(notificationtype),HttpStatus.FOUND);
    	
    }
}
    

