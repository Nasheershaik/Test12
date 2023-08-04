/**
 * @Author :RiteshSingh
 *@Date :06-July-2023
 *@FileName:Task.java 
 */
package com.kloc.crm.Entity;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * The Task entity represents a task in the CRM system.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task 
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "taskId")
    @GenericGenerator(name = "taskId",type = com.kloc.crm.Entity.IdGenerator.class,
            parameters = {
                    @Parameter(name = IdGenerator.INCREMENT_PARAM, value = "000001"),
                    @Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "task_"),
                    @Parameter(name = IdGenerator.NUMBER_FORMAT_PARAMETER, value = "%06d")
            })
	@Column(name="taskId")
	private String taskId;
	@ManyToOne(fetch=FetchType.EAGER)
//	@JsonBackReference("salesPerson")
	private SalesPerson salesPerson;  // The salesperson associated with the task
	private String taskDescription;  // Description of the task
	private LocalDate startDate;  // Start date of the task
	private LocalDate dueDate;  // Due date of the task
	@ManyToOne(fetch=FetchType.EAGER)
//	@JsonBackReference("assignedManager")
	private User assignedManager;  // The manager assigned to the task
	@ManyToOne
	private Contact contactId;  // The contact associated with the task
}
