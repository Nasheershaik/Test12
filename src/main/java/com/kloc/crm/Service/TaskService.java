/**
 * @Author :RiteshSingh
 *@Date :06-July-2023
 *@FileName:TaskService.java 
 */
package com.kloc.crm.Service;

import java.time.LocalDate;
import java.util.List;

import com.kloc.crm.Entity.Task;
import com.kloc.crm.Entity.TaskSub;
/**
 * The TaskService interface defines the contract for managing Task entities.
 * It provides methods to perform various operations on Task objects.
 *
 */
public interface TaskService 
{
	
	/**
	 * This method creates a new task.
	 *
	 * @param task          The Task object representing the task to be created.
	 * @param salesPersonId The ID of the salesperson associated with the task.
	 * @param managerId     The ID of the manager associated with the task.
	 * @param contactId     The ID of the contact associated with the task.
	 * @return The created Task object.
	 */
	Task createTask(Task task, String salesPersonId, String managerId, String contactId,String offeringId);

	/**
	 * This method retrieves all tasks.
	 *
	 * @return A list of Task objects representing all tasks.
	 */
	List<Task> getAllTask();

	/**
	 * This method retrieves all tasks associated with a salesperson ID.
	 *
	 * @param salesPersonId The ID of the salesperson.
	 * @return A list of Task objects associated with the salesperson.
	 */
	List<Task> getAllTaskBySalesPersonId(String salesPersonId);

	/**
	 * This method retrieves all tasks associated with a manager ID.
	 *
	 * @param managerId The ID of the manager.
	 * @return A list of Task objects associated with the manager.
	 */
	List<Task> getAllTaskByManagerId(String managerId);

	/**
	 * This method retrieves all tasks associated with a contact ID.
	 *
	 * @param id The ID of the contact.
	 * @return A list of Task objects associated with the contact.
	 */
	List<Task> getAllTaskByContactId(String id);

	/**
	 * This method updates a task by its salesperson ID.
	 *
	 * @param taskId        The ID of the task to update.
	 * @param salesPersonId The new salesperson ID to associate with the task.
	 * @return The updated Task object.
	 */
	Task updateTaskBySalesPersonId(String taskId, String salesPersonId);

	/**
	 * This method updates a task by its contact ID.
	 *
	 * @param taskId    The ID of the task to update.
	 * @param contactId The new contact ID to associate with the task.
	 * @return The updated Task object.
	 */
	Task updateTaskByContactId(String taskId, String contactId);

	/**
	 * This method updates a task by its contact and salesperson ID.
	 *
	 * @param taskId        The ID of the task to update.
	 * @param salesPersonId The new salesperson ID to associate with the task.
	 * @param contactId     The new contact ID to associate with the task.
	 * @return The updated Task object.
	 */
	Task updateTaskByContactAndSalesPersonId(String taskId, String salesPersonId, String contactId);

}
