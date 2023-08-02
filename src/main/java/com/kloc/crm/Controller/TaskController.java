/**
 * @Author :RiteshSingh
 *@Date :06-July-2023
 *@FileName:TaskController.java
 */
package com.kloc.crm.Controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kloc.crm.Entity.Task;
import com.kloc.crm.Entity.TaskSub;
import com.kloc.crm.Service.TaskService;
import com.kloc.crm.Service.TaskSubService;

/**
 * The TaskController class handles HTTP requests related to Task operations.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/task")
public class TaskController 
{
	/*
	 * here the object reference of task service is injected 
	 * with the help of this object refernce we can use the method of serviceImpl
	 */
	@Autowired
	private TaskService taskService;
	@Autowired
	private TaskSubService taskSubService;
	public static final Logger logger=LogManager.getLogger(TaskController.class);
	/**
     * Creates a new task.
     *
     * @param task          The task to create
     * @param salesPersonId The ID of the salesperson
     * @param managerId     The ID of the manager
     * @param contactId     The ID of the contact
     * @return ResponseEntity containing the created task and HTTP status code
     */
	@PostMapping("/createTask/{salesPersonId}/{managerId}/{contactId}/{offeringId}")
	public ResponseEntity<Task> createTask(@RequestBody Task task,@PathVariable( "salesPersonId") String salesPersonId,@PathVariable("managerId") String managerId,@PathVariable("contactId") String contactId,@PathVariable("offeringId") String offeringId)
	{
		logger.trace("Received request to create a new task.");
        logger.info("Creating task for salesperson with ID: {}, assigned by manager with ID: {}," +
                " associated with contact with ID: {} and offering with ID: {}.", salesPersonId, managerId, contactId, offeringId);
        logger.debug("Received task details: {}", task);

        Task createdTask = taskService.createTask(task, salesPersonId, managerId, contactId, offeringId);

        logger.info("Task created successfully. Task ID: {}", createdTask.getTaskId());
        logger.debug("Created task details: {}", createdTask);

        return new ResponseEntity<Task>(createdTask, HttpStatus.OK);
	}
	/**
     * Retrieves all task statuses for a given task ID.
     *
     * @param taskId The ID of the task
     * @return ResponseEntity containing the list of task statuses and HTTP status code
     */
	@GetMapping("/getAllTaskStatusByTaskId/{taskId}")
    public ResponseEntity<List<TaskSub>> getAllTaskStatusByTaskId(@PathVariable("taskId") String taskId) {
        logger.trace("Received request to get all task statuses for taskId: {}.", taskId);

        List<TaskSub> taskStatusList = taskSubService.getAllTaskStatusByTaskId(taskId);

        if (taskStatusList.isEmpty()) {
            logger.info("No task statuses found for taskId: {}.", taskId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        logger.info("Retrieved all task statuses for taskId: {}. Number of statuses: {}.", taskId, taskStatusList.size());
        logger.debug("Retrieved task statuses: {}", taskStatusList);

        return new ResponseEntity<>(taskStatusList, HttpStatus.OK);
    }
	/**
     * Retrieves the latest task status for a given task ID.
     *
     * @param taskId The ID of the task
     * @return ResponseEntity containing the latest task status and HTTP status code
     */
	 @GetMapping("/getLatestTaskStatusByTaskId/{taskId}")
	    public ResponseEntity<TaskSub> getLatestTaskStatusByTaskId(@PathVariable("taskId") String taskId) {
	        logger.trace("Received request to get the latest task status for taskId: {}.", taskId);

	        TaskSub latestTaskStatus = taskSubService.getLatestStatusByTaskId(taskId);

	        if (latestTaskStatus == null) {
	            logger.info("No task status found for taskId: {}.", taskId);
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }

	        logger.info("Retrieved the latest task status for taskId: {}.", taskId);
	        logger.debug("Latest task status: {}", latestTaskStatus);

	        return new ResponseEntity<>(latestTaskStatus, HttpStatus.OK);
	    }
	/**
     * Retrieves all tasks.
     *
     * @return ResponseEntity containing the list of tasks and HTTP status code
     */
	 @GetMapping("/getAllTask")
	    public ResponseEntity<List<Task>> getAllTask() {
	        logger.trace("Received request to get all tasks.");

	        List<Task> allTasks = taskService.getAllTask();

	        if (allTasks.isEmpty()) {
	            logger.info("No tasks found.");
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }

	        logger.info("Retrieved all tasks. Number of tasks: {}.", allTasks.size());
	        logger.debug("Retrieved tasks: {}", allTasks);

	        return new ResponseEntity<>(allTasks, HttpStatus.OK);
	    }
	/**
     * Retrieves all tasks associated with a salesperson ID.
     *
     * @param salesPersonId The ID of the salesperson
     * @return ResponseEntity containing the list of tasks and HTTP status code
     */
	 @GetMapping("/getAllTaskBySalesPersonId/{salesPersonId}")
	    public ResponseEntity<List<Task>> getAllTaskBySalesPersonId(@PathVariable("salesPersonId") String salesPersonId) {
	        logger.trace("Received request to get all tasks for salesperson with ID: {}.", salesPersonId);

	        List<Task> tasksForSalesPerson = taskService.getAllTaskBySalesPersonId(salesPersonId);

	        if (tasksForSalesPerson.isEmpty()) {
	            logger.info("No tasks found for salesperson with ID: {}.", salesPersonId);
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }

	        logger.info("Retrieved all tasks for salesperson with ID: {}. Number of tasks: {}.", salesPersonId, tasksForSalesPerson.size());
	        logger.debug("Retrieved tasks: {}", tasksForSalesPerson);

	        return new ResponseEntity<>(tasksForSalesPerson, HttpStatus.OK);
	    }
	/**
     * Retrieves all tasks associated with a contact ID.
     *
     * @param contactId The ID of the contact
     * @return ResponseEntity containing the list of tasks and HTTP status code
     */
	 @GetMapping("/getAllTaskByContactId/{contactId}")
	    public ResponseEntity<List<Task>> getAllTaskByContactId(@PathVariable("contactId") String contactId) {
	        logger.trace("Received request to get all tasks for contact with ID: {}.", contactId);

	        List<Task> tasksForContact = taskService.getAllTaskByContactId(contactId);

	        if (tasksForContact.isEmpty()) {
	            logger.info("No tasks found for contact with ID: {}.", contactId);
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }

	        logger.info("Retrieved all tasks for contact with ID: {}. Number of tasks: {}.", contactId, tasksForContact.size());
	        logger.debug("Retrieved tasks: {}", tasksForContact);

	        return new ResponseEntity<>(tasksForContact, HttpStatus.OK);
	    }
	/**
     * Retrieves all tasks associated with a manager ID.
     *
     * @param managerId The ID of the manager
     * @return ResponseEntity containing the list of tasks and HTTP status code
     */
	 @GetMapping("/getAllTaskByManagerId/{managerId}")
	    public ResponseEntity<List<Task>> getAllTaskByManagerId(@PathVariable("managerId") String managerId) {
	        logger.trace("Received request to get all tasks for manager with ID: {}.", managerId);

	        List<Task> tasksForManager = taskService.getAllTaskByManagerId(managerId);

	        if (tasksForManager.isEmpty()) {
	            logger.info("No tasks found for manager with ID: {}.", managerId);
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }

	        logger.info("Retrieved all tasks for manager with ID: {}. Number of tasks: {}.", managerId, tasksForManager.size());
	        logger.debug("Retrieved tasks: {}", tasksForManager);

	        return new ResponseEntity<>(tasksForManager, HttpStatus.OK);
	    }
	/**
     * Updates the salesperson ID for a given task.
     *
     * @param taskId        The ID of the task
     * @param salesPersonId The ID of the salesperson
     * @return ResponseEntity containing the updated task and HTTP status code
     */
	 @PutMapping("/updateTaskBySalesPersonId/{taskId}/{salesPersonId}")
	    public ResponseEntity<Task> updateTaskBySalesPersonId(@PathVariable("taskId") String taskId, @PathVariable("salesPersonId") String salesPersonId) {
	        logger.trace("Received request to update task with ID: {} for salesperson with ID: {}.", taskId, salesPersonId);

	        Task updatedTask = taskService.updateTaskBySalesPersonId(taskId, salesPersonId);

	        if (updatedTask == null) {
	            logger.info("No task found for taskId: {} or salesperson with ID: {}.", taskId, salesPersonId);
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }

	        logger.info("Task with ID: {} updated successfully for salesperson with ID: {}.", taskId, salesPersonId);
	        logger.debug("Updated task details: {}", updatedTask);

	        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
	    }
	/**
     * Updates the contact ID for a given task.
     *
     * @param taskId    The ID of the task
     * @param contactId The ID of the contact
     * @return ResponseEntity containing the updated task and HTTP status code
     */
	 @PutMapping("/updateTaskByContactId/{taskId}/{contactId}")
	    public ResponseEntity<Task> updateTaskByContactId(@PathVariable("taskId") String taskId, @PathVariable("contactId") String contactId) {
	        logger.trace("Received request to update task with ID: {} for contact with ID: {}.", taskId, contactId);

	        Task updatedTask = taskService.updateTaskByContactId(taskId, contactId);

	        if (updatedTask == null) {
	            logger.info("No task found for taskId: {} or contact with ID: {}.", taskId, contactId);
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }

	        logger.info("Task with ID: {} updated successfully for contact with ID: {}.", taskId, contactId);
	        logger.debug("Updated task details: {}", updatedTask);

	        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
	    }
	/**
     * Updates the salesperson ID and contact ID for a given task.
     *
     * @param taskId        The ID of the task
     * @param salesPersonId The ID of the salesperson
     * @param contactId     The ID of the contact
     * @return ResponseEntity containing the updated task and HTTP status code
     */
	 @PutMapping("/updateTaskBySalesPersonAndContactId/{taskId}/{salesPersonId}/{contactId}")
	    public ResponseEntity<Task> updateTaskBySalesPersonAndContactId(@PathVariable("taskId") String taskId,
	                                                                    @PathVariable("salesPersonId") String salesPersonId,
	                                                                    @PathVariable("contactId") String contactId) {
	        logger.trace("Received request to update task with ID: {} for salesperson with ID: {} and contact with ID: {}.",
	                taskId, salesPersonId, contactId);

	        Task updatedTask = taskService.updateTaskByContactAndSalesPersonId(taskId, salesPersonId, contactId);

	        if (updatedTask == null) {
	            logger.info("No task found for taskId: {}, salesperson with ID: {}, or contact with ID: {}.",
	                    taskId, salesPersonId, contactId);
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }

	        logger.info("Task with ID: {} updated successfully for salesperson with ID: {} and contact with ID: {}.",
	                taskId, salesPersonId, contactId);
	        logger.debug("Updated task details: {}", updatedTask);

	        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
	    }
	
	/**
     * Retrieves all task sub-entries with a specific status.
     *
     * @param taskStatus The status of the task sub-entries
     * @return ResponseEntity containing the list of task sub-entries and HTTP status code
     */
	 @GetMapping("/getAllTaskByStatus/{taskStatus}")
	    public ResponseEntity<List<Task>> getAllTaskSubByStatus(@PathVariable("taskStatus") String taskStatus) {
	        logger.trace("Received request to get all tasks with status: {}.", taskStatus);

	        List<Task> tasksWithStatus = taskSubService.getAllTaskByStatus(taskStatus);

	        if (tasksWithStatus.isEmpty()) {
	            logger.info("No tasks found with status: {}.", taskStatus);
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }

	        logger.info("Retrieved all tasks with status: {}. Number of tasks: {}.", taskStatus, tasksWithStatus.size());
	        logger.debug("Retrieved tasks: {}", tasksWithStatus);

	        return new ResponseEntity<>(tasksWithStatus, HttpStatus.OK);
	    }
	
	/**
     * Retrieves all task sub-entries with a specific task outcome.
     *
     * @param taskOutcome The outcome of the task sub-entries
     * @return ResponseEntity containing the list of task sub-entries and HTTP status code
     */
	 @GetMapping("/getAllTaskByTaskOutcome/{taskOutcome}")
	    public ResponseEntity<List<Task>> getAllTaskSubByTaskOutcome(@PathVariable("taskOutcome") String taskOutcome) {
	        logger.trace("Received request to get all tasks with task outcome: {}.", taskOutcome);

	        List<Task> tasksWithOutcome = taskSubService.getAllTaskByTaskOutcome(taskOutcome);

	        if (tasksWithOutcome.isEmpty()) {
	            logger.info("No tasks found with task outcome: {}.", taskOutcome);
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }

	        logger.info("Retrieved all tasks with task outcome: {}. Number of tasks: {}.", taskOutcome, tasksWithOutcome.size());
	        logger.debug("Retrieved tasks: {}", tasksWithOutcome);

	        return new ResponseEntity<>(tasksWithOutcome, HttpStatus.OK);
	    }
	
	 /**
     * Updates a task sub-entry by task ID.
     *
     * @param task    The updated task sub-entry
     * @param taskId  The ID of the task
     * @return ResponseEntity containing the updated task sub-entry and HTTP status code
     */
	 @PutMapping("/updateTaskSub/{taskId}")
	    public ResponseEntity<TaskSub> updateTask(@RequestBody TaskSub task, @PathVariable("taskId") String taskId) {
	        logger.trace("Received request to update task with ID: {}.", taskId);

	        TaskSub updatedTaskSub = taskSubService.updateTaskSubByTaskId(task, taskId);

	        if (updatedTaskSub == null) {
	            logger.info("No task found for taskId: {}.", taskId);
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }

	        logger.info("Task with ID: {} updated successfully.", taskId);
	        logger.debug("Updated task details: {}", updatedTaskSub);

	        return new ResponseEntity<>(updatedTaskSub, HttpStatus.OK);
	    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
