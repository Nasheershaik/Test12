package com.kloc.crm.Entity;
import java.time.LocalDate;

/**
 * @Author :Nasheer
 *@Date :06-July-2023
 *@FileName:Notification.java
 */
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification{
	
	
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "notification-id-generator")
	@GenericGenerator(name = "notification-id-generator", type = com.kloc.crm.Entity.IdGenerator.class, 
	parameters = {
			@Parameter(name = IdGenerator.INCREMENT_PARAM, value = "1"),
	        @Parameter(name = "valuePrefix", value = "Notification__"),
	        @Parameter(name = "numberFormat", value = "%04d") 
	})
	private String notificationId;
	
	private String notificationType;
	
	private String notificationTemplate;
	
	private LocalDate remindBefore;
	
	private String subject;
	
	private String role;
	
	
	@OneToOne
	@JsonBackReference
	private Status status;
	
	
	 
	
}


