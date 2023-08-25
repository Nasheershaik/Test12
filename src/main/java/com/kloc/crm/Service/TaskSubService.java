 /**
 * @Author :RiteshSingh
 *@Date :06-July-2023
 *@FileName:TaskSubService.java 
 */
package com.kloc.crm.Service;

import java.time.LocalDate;
import java.util.List;

import com.kloc.crm.Entity.Task;
import com.kloc.crm.Entity.TaskSub;

public interface TaskSubService 
{
	/**
	 * This method retrieves all task statuses by task ID.
	 *
	 * @param taskId The ID of the task.
	 * @return A list of TaskSub objects representing the task statuses.
	 */
	List<TaskSub> getAllTaskStatusByTaskId(String taskId);

	/**
	 * This method retrieves the latest task status by task ID.
	 *
	 * @param taskId The ID of the task.
	 * @return The latest TaskSub object representing the task status.
	 */
	TaskSub getLatestStatusByTaskId(String taskId);

	/**
	 * This method retrieves all tasks by a given status.
	 *
	 * @param taskStatus The status of the tasks.
	 * @return A list of TaskSub objects representing the tasks.
	 */
	List<Task> getAllTaskByStatus(String taskStatus);

	/**
	 * This method retrieves all tasks by a given task outcome.
	 *
	 * @param taskOutcome The outcome of the tasks.
	 * @return A list of TaskSub objects representing the tasks.
	 */
	List<Task> getAllTaskByTaskOutcome(String taskOutcome);

	/**
	 * This method retrieves all tasks by a sales activity.
	 *
	 * @param salesActivity The sales activity associated with the tasks.
	 * @return A list of Task objects representing the tasks.
	 */
	List<Task> getAllTaskBySalesActivity(String salesActivity);

	/**
	 * This method retrieves all tasks by an offering ID.
	 *
	 * @param offeringId The ID of the offering associated with the tasks.
	 * @return A list of Task objects representing the tasks.
	 */
	List<Task> getAllTaskByOfferingId(String offeringId);

	/**
	 * This method retrieves all tasks within a specified date range.
	 *
	 * @param initialDate The start date of the range.
	 * @param finalDate   The end date of the range.
	 * @return A list of TaskSub objects representing the tasks.
	 */
	List<Task> getAllTaskByDateRange(LocalDate initialDate, LocalDate finalDate);

	/**
	 * This method updates a task sub by task ID.
	 *
	 * @param taskSub The updated TaskSub object.
	 * @param taskId  The ID of the task to update.
	 * @return The updated TaskSub object.
	 */
	TaskSub updateTaskSubByTaskId(TaskSub taskSub, String taskId);
	/**
	 * Retrieves a list of tasks based on the given salesperson ID and task status.
	 *
	 * @param salespersonId The ID of the salesperson.
	 * @param status The status of the tasks to filter by.
	 * @return A list of tasks matching the criteria.
	 */
	List<Task> getAllTaskBySalespersonIdAndStatus(String salespersonId, String status);

	/**
	 * Retrieves a list of tasks based on the given salesperson ID and task outcome.
	 *
	 * @param salespersonId The ID of the salesperson.
	 * @param outcome The outcome of the tasks to filter by.
	 * @return A list of tasks matching the criteria.
	 */
	List<Task> getAllTaskBySalespersonIdAndOutcome(String salespersonId, String outcome);

	/**
	 * Retrieves a list of tasks based on the specified date range, salesperson ID, task status, and life cycle stage.
	 *
	 * @param initialDate The start date of the date range.
	 * @param finalDate The end date of the date range.
	 * @param salespersonId The ID of the salesperson.
	 * @return A list of tasks matching the criteria.
	 */
	List<Task> getAllTaskByDateRangeBySalesPersonIdByTaskStatusByLifeCycleStage(LocalDate initialDate, LocalDate finalDate, String salespersonId);

}
