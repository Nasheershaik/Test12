/**
 * @Author :RiteshSingh
 *@Date :06-July-2023
 *@FileName:TaskServiceImpl.java 
 */
package com.kloc.crm.Service.ServiceImpl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.kloc.crm.Entity.Contact;
import com.kloc.crm.Entity.Email;
import com.kloc.crm.Entity.Offering;
import com.kloc.crm.Entity.SalesPerson;
import com.kloc.crm.Entity.Status;
import com.kloc.crm.Entity.Task;
import com.kloc.crm.Entity.TaskSub;
import com.kloc.crm.Entity.User;
import com.kloc.crm.Exception.DataNotFoundException;
import com.kloc.crm.Exception.InvalidInput;
import com.kloc.crm.Exception.NullDataException;
import com.kloc.crm.Repository.ContactRepository;
import com.kloc.crm.Repository.EmailRepo;
import com.kloc.crm.Repository.OfferingRepository;
import com.kloc.crm.Repository.SalesPersonRepository;
import com.kloc.crm.Repository.StatusRepo;
import com.kloc.crm.Repository.TaskRepository;
import com.kloc.crm.Repository.TaskSubRepository;
import com.kloc.crm.Repository.UserRepository;
import com.kloc.crm.Scheduler.ScheduledMailWithDueDates;
import com.kloc.crm.Scheduler.ScheduledMailWithFollowUpDates;
import com.kloc.crm.Service.TaskService;
import com.kloc.crm.Service.TaskSubService;

/**
 * this class is used to provide implementation of all the abstract method persent in its super interface that is service interface
 */
@Service
public class TaskServiceImpl implements TaskService,TaskSubService {
	/**
	 * @Autowired is an annotation used in Spring Boot to enable automatic
	 * dependency injection. It allows the Spring container to provide an instance
	 * of a required dependency when a bean is created. This annotation can be used
	 * on fields, constructors, and methods to have Spring provide the dependencies
	 * automatically
	 */
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private SalesPersonRepository salesPersonRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	private StatusRepo statusRepo;
	@Autowired
	private ScheduledMailWithFollowUpDates scheduleMAilWithFollowUpDate;
	@Autowired
	private ScheduledMailWithDueDates scheduledMailWithDueDates;
	@Autowired
	private TaskSubRepository taskSubRepository;
	@Autowired
	private OfferingRepository offeringRepository;
	@Autowired
	private EmailRepo emailRepository;
	
	@Override
	public List<TaskSub> getAllTaskStatusByTaskId(String taskId) 
	{
		if(taskId.isEmpty()||taskId.equals(null)) {throw new NullDataException("Enter The Valid taskId");}
		taskRepository.findById(taskId).orElseThrow(()->new DataNotFoundException("The Given TaskId Is Not Found:Enter the Existing TaskId"));
		return taskSubRepository.findAll().stream().filter(e->e.getTask().getTaskId().equals(taskId)).toList();	
	}
	@Override
	public TaskSub getLatestStatusByTaskId(String taskId) {
		if(taskId.isEmpty()||taskId.equals(null)) {throw new NullDataException("Enter The Valid taskId");}
		taskRepository.findById(taskId).orElseThrow(()->new DataNotFoundException("The Given TaskId Is Not Found:Enter the Existing TaskId"));
		List<TaskSub> list=taskSubRepository.findAll().stream().filter(e->e.getTask().getTaskId().equals(taskId)).toList();	
		return list.get(list.size()-1);
	}
//	@Override
//	public List<TaskSub> getAllTaskByStatus(String taskStatus) {
//	    return taskRepository.findAll().stream()
//	        .filter(e -> e.getSalesPerson().getUser().getStatus().getStatusValue().equalsIgnoreCase("active"))
//	        .flatMap(e -> {
//	            List<TaskSub> list = taskSubRepository.findAll().stream()
//	                .filter(a -> a.getTask().equals(e))
//	                .toList();
//	            if (!list.isEmpty()) {
//	                if (list.get(list.size() - 1).getTaskStatus().getStatusValue().equalsIgnoreCase(taskStatus)) {
//	                    return list.stream();
//	                }
//	            }
//	            return Stream.empty();
//	        })
//	        .toList();    
//	}
	@Override
	public List<Task> getAllTaskByStatus(String taskStatus) {
//	    try {
//	        return taskRepository.findAll().stream()
//	            .filter(e -> "active".equalsIgnoreCase(
//	                Optional.ofNullable(e.getSalesPerson())
//	                        .map(SalesPerson::getUser)
//	                        .map(User::getStatus)
//	                        .map(Status::getStatusValue)
//	                        .orElse("")
//	            ))
//	            .flatMap(e -> {
//	                List<TaskSub> list = taskSubRepository.findAll().stream()
//	                    .filter(a -> a.getTask().equals(e))
//	                    .collect(Collectors.toList());
//
//	                if (!list.isEmpty()) {
//	                    TaskSub lastTaskSub = list.get(list.size() - 1);
//	                    if (lastTaskSub.getTaskStatus().getStatusValue().equalsIgnoreCase(taskStatus)) {
//	                        return Stream.of(lastTaskSub.getTask());
//	                    }
//	                }
//	                return Stream.empty();
//	            })
//	            .collect(Collectors.toList());
//	    } catch (Exception e) {
//	        // Handle the exception here (e.g., logging or rethrowing)
//	        return Collections.emptyList(); // Return an empty list in case of an exception
//	    }
		try {
			return taskRepository.findAll().stream()
			        .filter(e -> e.getSalesPerson().getUser().getStatus().getStatusValue().toLowerCase().equals("active"))
			        .flatMap(e -> {
			            List<TaskSub> list = taskSubRepository.findAll().stream()
			                .filter(a -> a.getTask().equals(e))
			                .toList();
			            if (!list.isEmpty()) {
			                if (list.get(list.size() - 1).getTaskStatus().getStatusValue().toLowerCase().equals(taskStatus.toLowerCase())) {
			                    return Stream.of(list.get(list.size()-1).getTask());
			                }
			            }
			            return Stream.empty();
			        })
			        .toList(); 
			}
			catch (Exception e) {
		        // Handle the exception here (e.g., logging or rethrowing)
		        return Collections.emptyList(); // Return an empty list in case of an exception
		    }
	}

	@Override
	public List<Task> getAllTaskByTaskOutcome(String taskOutcome) {
		try {
		return taskRepository.findAll().stream()
		        .filter(e -> e.getSalesPerson().getUser().getStatus().getStatusValue().toLowerCase().equals("active"))
		        .flatMap(e -> {
		            List<TaskSub> list = taskSubRepository.findAll().stream()
		                .filter(a -> a.getTask().equals(e))
		                .toList();
		            if (!list.isEmpty()) {
		                if (list.get(list.size() - 1).getTaskOutcome().getStatusValue().toLowerCase().equals(taskOutcome.toLowerCase())) {
		                    return Stream.of(list.get(list.size()-1).getTask());
		                }
		            }
		            return Stream.empty();
		        })
		        .toList(); 
		}
		catch (Exception e) {
	        // Handle the exception here (e.g., logging or rethrowing)
	        return Collections.emptyList(); // Return an empty list in case of an exception
	    }
	}
	@Override
	public List<Task> getAllTaskBySalesActivity(String salesActivity) 
	{
		return null;
	}
	@Override
	public List<Task> getAllTaskByOfferingId(String OfferingId) 
	{
		if(OfferingId.isEmpty()||OfferingId.equals(null)) {throw new NullDataException("Enter the valid OfferingId");}
		return taskSubRepository.findAll().stream().filter(e->e.getOfferingId().getOfferingId().toLowerCase().equals(OfferingId.toLowerCase())).distinct().map(e->e.getTask()).toList();
	}
	@Override
	public List<Task> getAllTaskByDateRange(LocalDate intialdate, LocalDate finalDate) 
	{
		if(intialdate.equals(null)||finalDate.equals(null)) {throw new NullDataException("compulsory to fill intialDate and finalDate");}
		return taskRepository.findAll().stream().filter(e->e.getSalesPerson().getUser().getStatus().getStatusValue().toLowerCase().equals("active")).map(e->{
			List<TaskSub> list=taskSubRepository.findAll().stream().filter(a->a.getTask().equals(e)).toList();
			LocalDate statusdate=list.get(list.size()-1).getStatusDate();
			if((statusdate.isBefore(finalDate)||statusdate.isEqual(finalDate))&&(statusdate.isAfter(intialdate)||statusdate.isEqual(intialdate))&& (!list.get(list.size()-1).getTaskStatus().getStatusValue().toLowerCase().equals("completed"))) 
			{
				return list.get(list.size()-1).getTask();
			}
			else {return null;}
		}).toList();
	}
	@Override
	public Task createTask(Task task,String salesPersonId, String managerId, String contactId,String offeringId) 
	{
		if(task.equals(null)) {throw new InvalidInput("Write all the necessary fiels while creating task");}
		if(salesPersonId.isEmpty()||salesPersonId.equals(null)) {throw new NullDataException("Enter the Valid SalesPersonId");}
		if(managerId.isEmpty()||managerId.equals(null)) {throw new NullDataException("Enter the Valid ManagerId");}
		if(contactId.isEmpty()||contactId.equals(null)) {throw new NullDataException("Enter the Valid ContactId");}
		if(offeringId.isEmpty()||offeringId.equals(null)) {throw new NullDataException("Enter the valid OfferingId");}
		SalesPerson salesPerson=salesPersonRepository.findById(salesPersonId).orElseThrow(()->new DataNotFoundException("SalesPerson is not found:Enter the existing salesPersonId"));
		task.setSalesPerson(salesPerson);
		User user=userRepository.findById(managerId).orElseThrow(()->new DataNotFoundException("manager is not found:Enter the existing managerId"));
		task.setAssignedManager(user);
		Contact contact=contactRepository.findById(contactId).orElseThrow(()->new DataNotFoundException("conatct is not found:Enter the existing contactId"));
		task.setContactId(contact);
		Offering offering=offeringRepository.findById(offeringId).orElseThrow(()->new DataNotFoundException("Offering is not found:Enter the existing offeringId"));
		Status status=statusRepo.findAll().stream().filter(e->(e.getTableName().toLowerCase().equals("task"))&&(e.getStatusValue().toLowerCase().equals("open".toLowerCase()))).findFirst().get();
		TaskSub taskSub=new TaskSub();
		Task task1=taskRepository.save(task);
		taskSub.setTaskStatus(status);
		taskSub.setTask(task1);
		taskSub.setStatusDate(LocalDate.now());
		taskSub.setOfferingId(offering);
		taskSubRepository.save(taskSub);
//		User user1=task.getSalesPerson().getUser();
//		if(!taskSub.getFollowUpDate().equals(null)) {
//		scheduleMAil.sendSimpleMail(user1,taskSub.getFollowUpDate());
//		}
		return task1;
	}
	@Override
	public List<Task> getAllTask() {
		return taskRepository.findAll();
	}
	@Override
	public List<Task> getAllTaskBySalesPersonId(String salesPersonId) 
	{
		if(salesPersonId.isEmpty()||salesPersonId.equals(null)) {throw new NullDataException("Enter the valid salespersonId");}
		salesPersonRepository.findById(salesPersonId).orElseThrow(()->new DataNotFoundException("salesperson is not found:enter the existing salespersonId"));
		return taskRepository.findAll().stream().filter(e->e.getSalesPerson().getSalespersonId().toLowerCase().equals(salesPersonId.toLowerCase())).toList();
	}
	@Override
	public List<Task> getAllTaskByManagerId(String managerId) {
		if(managerId.isEmpty()||managerId.equals(null)) {throw new NullDataException("Enter the valid contactId");}
		userRepository.findById(managerId).orElseThrow(()->new DataNotFoundException("Manager is not found:Enter the existing ManagerId"));
		return taskRepository.findAll().parallelStream().filter(e->e.getAssignedManager().getUserId().toLowerCase().equals(managerId.toLowerCase())).toList();
	}
	@Override
	public List<Task> getAllTaskByContactId(String contactId) {
		if(contactId.isEmpty()||contactId.equals(null)) {throw new NullDataException("Enter the Valid contactId");}
		contactRepository.findById(contactId).orElseThrow(()->new DataNotFoundException("Contact is not persent:Enter the existing ContactId"));
		return taskRepository.findAll().stream().filter(e->e.getContactId().getContactId().toLowerCase().equals(contactId.toLowerCase())).toList();
	}
	@Override
	public Task updateTaskBySalesPersonId(String taskId, String salesPersonId) 
	{	
		if(taskId.isEmpty()||taskId.equals(null)) {throw new NullDataException("Enter The Valid taskId");}
		if(salesPersonId.isEmpty()||salesPersonId.equals(null)) {throw new NullDataException("SalesPerson is not found:Enter the existing salesPersonId");}
		Task task=taskRepository.findById(taskId).orElseThrow(()->new DataNotFoundException("task is not found:Enter the vaild taskId"));
		SalesPerson salesPerson=salesPersonRepository.findById(salesPersonId).orElseThrow(()->new DataNotFoundException("salesperson is not found:enter the valid salespersonId"));
		Task task1=new Task();
		task1.setAssignedManager(task.getAssignedManager());
		task1.setContactId(task.getContactId());
		task1.setDueDate(task.getDueDate());
		task1.setStartDate(task.getStartDate());
		task1.setTaskDescription(task.getTaskDescription());
		task1.setSalesPerson(salesPerson);
		task1.setTaskDescription("continuation of "+task.getTaskId());
		Task task2=taskRepository.save(task1);
		task.setTaskDescription("Transferred as "+task2.getTaskId());
		List<TaskSub> list=taskSubRepository.findAll().stream().filter(e->e.getTask().getTaskId().toLowerCase().equals(taskId.toLowerCase())).toList();
		TaskSub a=list.get(list.size()-1);
			TaskSub taskSub=new TaskSub();
			taskSub.setFeedbackDate(a.getFeedbackDate());
			taskSub.setFollowUpDate(a.getFollowUpDate());
			taskSub.setLeadFeedback(a.getLeadFeedback());
			taskSub.setOfferingId(a.getOfferingId());
			taskSub.setSalesActivity(a.getSalesActivity());
			taskSub.setStatusDate(a.getStatusDate());
			taskSub.setTask(task2);
			taskSub.setTaskFeedback(a.getTaskFeedback());
			taskSub.setTaskOutcome(a.getTaskOutcome());
			taskSub.setTaskStatus(a.getTaskStatus());	
			taskSubRepository.save(taskSub);
	
		return task2;
	}
	@Override
	public Task updateTaskByContactId(String taskId, String contactId) 
	{
		if(taskId.isEmpty()||taskId.equals(null)) {throw new NullDataException("Enter The Valid taskId");}
		if(contactId.isEmpty()||contactId.equals(null)) {throw new NullDataException("Enter the valid contactId");}
		Task task=taskRepository.findById(taskId).orElseThrow(()->new DataNotFoundException("Task not found:Enter the existing TaskId"));
		Contact contact=contactRepository.findById(contactId).orElseThrow(()->new DataNotFoundException("contact is not found:Enter the existing contactId"));
		Task task1=new Task();
		task1.setAssignedManager(task.getAssignedManager());
		task1.setContactId(contact);
		task1.setDueDate(task.getDueDate());
		task1.setStartDate(task.getStartDate());
		task1.setTaskDescription(task.getTaskDescription());
		task1.setSalesPerson(task.getSalesPerson());
		task1.setTaskDescription("continuation of "+task.getTaskId());
		Task task2=taskRepository.save(task1);
		task.setTaskDescription("Transferred as "+task2.getTaskId());
		List<TaskSub> list=taskSubRepository.findAll().stream().filter(e->e.getTask().getTaskId().toLowerCase().equals(taskId.toLowerCase())).toList();
		TaskSub a=list.get(list.size()-1);
			TaskSub taskSub=new TaskSub();
			taskSub.setFeedbackDate(a.getFeedbackDate());
			taskSub.setFollowUpDate(a.getFollowUpDate());
			taskSub.setLeadFeedback(a.getLeadFeedback());
			taskSub.setOfferingId(a.getOfferingId());
			taskSub.setSalesActivity(a.getSalesActivity());
			taskSub.setStatusDate(a.getStatusDate());
			taskSub.setTask(task2);
			taskSub.setTaskFeedback(a.getTaskFeedback());
			taskSub.setTaskOutcome(a.getTaskOutcome());
			taskSub.setTaskStatus(a.getTaskStatus());	
			taskSubRepository.save(taskSub);
		
		return task2;
	}
	@Override
	public Task updateTaskByContactAndSalesPersonId(String taskId, String salesPersonId, String contactId) 
	{	
		if(salesPersonId.isEmpty()||salesPersonId.equals(null)) {throw new NullDataException("Enter the valid salesPersonId");}
		if(taskId.isEmpty()||taskId.equals(null)) {throw new NullDataException("Enter The Valid taskId");}
		if(contactId.isEmpty()||contactId.equals(null)) {throw new NullDataException("Enter the Valid ContactId");}
		Task task=taskRepository.findById(taskId).orElseThrow(()->new DataNotFoundException("Task Is Not Found:Enter the Existing TaskId"));
		Contact contact=contactRepository.findById(contactId).orElseThrow(()->new DataNotFoundException("Contact is not persent:Enter the existing ContactId"));
		SalesPerson salesPerson=salesPersonRepository.findById(salesPersonId).orElseThrow(()->new DataNotFoundException("salesPerson is not found:Enter the existing salesPersonId"));
		Task task1=new Task();
		task1.setAssignedManager(task.getAssignedManager());
		task1.setContactId(contact);
		task1.setDueDate(task.getDueDate());
		task1.setStartDate(task.getStartDate());
		task1.setTaskDescription(task.getTaskDescription());
		task1.setSalesPerson(salesPerson);
		task1.setTaskDescription("continuation of "+task.getTaskId());
		Task task2=taskRepository.save(task1);
		task.setTaskDescription("Transferred as "+task2.getTaskId());
		List<TaskSub> list=taskSubRepository.findAll().stream().filter(e->e.getTask().getTaskId().toLowerCase().equals(taskId.toLowerCase())).toList();
		TaskSub a=list.get(list.size()-1);
			TaskSub taskSub=new TaskSub();
			taskSub.setFeedbackDate(a.getFeedbackDate());
			taskSub.setFollowUpDate(a.getFollowUpDate());
			taskSub.setLeadFeedback(a.getLeadFeedback());
			taskSub.setOfferingId(a.getOfferingId());
			taskSub.setSalesActivity(a.getSalesActivity());
			taskSub.setStatusDate(a.getStatusDate());
			taskSub.setTask(task2);
			taskSub.setTaskFeedback(a.getTaskFeedback());
			taskSub.setTaskOutcome(a.getTaskOutcome());
			taskSub.setTaskStatus(a.getTaskStatus());	
			 taskSubRepository.save(taskSub);
		
		return task2;
	}
//	@Override
//	public TaskSub TaskUpdate(TaskSub taskSub, String taskSubId) 
//	{
//		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&7");
//		System.out.println(taskSub.toString());
//		return null;
//	}
	
	@Override
	public TaskSub updateTaskSubByTaskId(TaskSub taskSub, String taskId) 
	{
		if(taskSub.equals(null)) {throw new InvalidInput("TaskSub can't be null");}
		if(taskId.isEmpty()||taskId.equals(null)) {throw new NullDataException("Enter The Valid taskId");}
		Task task=taskRepository.findById(taskId).orElseThrow(()->new DataNotFoundException("Task is not found:Enter the existing the taskId"));
		List<TaskSub> list=taskSubRepository.findAll().stream().filter(e->e.getTask().getTaskId().equals(taskId)).toList();	
		TaskSub existingTaskSub=list.get(list.size()-1);
		if(!taskSub.getTaskStatus().equals(null))
		{
		  existingTaskSub.setTaskStatus(statusRepo.findByStatusTypeAndStatusValue("Task_Status", taskSub.getTaskStatus().getStatusValue()));
		  existingTaskSub.setStatusDate(LocalDate.now());
		  LocalDate date=task.getDueDate();
//		  if(LocalDate.now().isAfter(date)&&(!taskSub.getTaskStatus().getStatusValue().toLowerCase().equals("close".toLowerCase()))) {
//			  scheduledMailWithDueDates.sendSimpleMail(task.getSalesPerson().getUser(), task);
//		  }
		}
		if(!taskSub.getTaskOutcome().equals(null))
		{
			existingTaskSub.setTaskOutcome(statusRepo.findByStatusTypeAndStatusValue("Task_Outcome", taskSub.getTaskOutcome().getStatusValue()));
		}
		if(!taskSub.getFollowUpDate().equals(null)) 
		{
		 existingTaskSub.setFollowUpDate(taskSub.getFollowUpDate());
		}
		existingTaskSub.setTaskFeedback(taskSub.getTaskFeedback());
		existingTaskSub.setLeadFeedback(taskSub.getLeadFeedback());
		if(!(taskSub.getLeadFeedback().equals(null))||!(taskSub.getLeadFeedback().equals(null)))
				{
					existingTaskSub.setFeedbackDate(LocalDate.now());
				}
		if(!taskSub.getSalesActivity().equals(null))
		{
			existingTaskSub.setSalesActivity(statusRepo.findByStatusTypeAndStatusValue("Sales_ActivityStatus", taskSub.getSalesActivity().getStatusValue()));
		}
//		if(!taskSub.getOfferingId().equals(null)) {
//			existingTaskSub.setOfferingId(offeringRepository.findByOfferingTypeAndOfferingCategory(taskSub.getOfferingId().getOfferingType(),taskSub.getOfferingId().getOfferingCategory()));
//		}
		TaskSub newTaskSub=new TaskSub();
		newTaskSub.setFeedbackDate(existingTaskSub.getFeedbackDate());
		newTaskSub.setFollowUpDate(existingTaskSub.getFollowUpDate());
		newTaskSub.setLeadFeedback(existingTaskSub.getLeadFeedback());
		newTaskSub.setTaskFeedback(existingTaskSub.getTaskFeedback());
		newTaskSub.setTask(existingTaskSub.getTask());
		newTaskSub.setTaskStatus(existingTaskSub.getTaskStatus());
		newTaskSub.setTaskOutcome(existingTaskSub.getTaskOutcome());
		newTaskSub.setStatusDate(existingTaskSub.getStatusDate());
		newTaskSub.setOfferingId(existingTaskSub.getOfferingId());
		newTaskSub.setSalesActivity(existingTaskSub.getSalesActivity());
		User user=task.getSalesPerson().getUser();
		// Schedule an email for the follow-up date
		if(!taskSub.getFollowUpDate().equals(null)) {
		scheduleMAilWithFollowUpDate.scheduleEmailForTask(newTaskSub.getTask(),newTaskSub.getFollowUpDate());
		}
//		else if(LocalDate.now().isBefore(existingTaskSub.getFollowUpDate())){
//			scheduleMAilWithFollowUpDate.sendSimpleMail(user, task, existingTaskSub.getFollowUpDate());
//		}
		return taskSubRepository.save(newTaskSub);
	}
	

	@Scheduled(cron = "0 0 7 * * ?")
	public void scheduledMailBasedOnDueDates() {
	    taskRepository.findAll().stream().forEach(e -> {
	        List<TaskSub> tasksub = taskSubRepository.findByTask(e);
	        if (!tasksub.isEmpty()) {
	            TaskSub lastTaskSub = tasksub.get(tasksub.size() - 1);
	            if (!lastTaskSub.getTaskStatus().getStatusValue().toLowerCase().equals("completed")) {
	                // Check if the due date has passed
	                if (e.getDueDate().isBefore(LocalDate.now())) {
	                    String emailType = "OverDue"; // Set the emailType here

	                    if (isTimeForSendingEmail(e.getTaskId(), emailType)) {
	                        // Get the task and send the email
	                        scheduledMailWithDueDates.sendSimpleMail(e);
	                    }
	                }
	            }
	        }
	    });
	}

	private boolean isTimeForSendingEmail(String taskId, String emailType) {
	    Email email = emailRepository.findFirstByTaskTaskIdAndEmailTypeOrderByEmailDateDesc(taskId, emailType);
	    if (email == null || email.getEmailDate().plusDays(7).isBefore(LocalDate.now())) {
	        if (email == null) {
	            email = new Email();
	            email.setTask(taskRepository.findById(taskId).get()); // Assuming there's a constructor for Task that accepts taskId
	            email.setEmailType(emailType);
	            email.setToAddress(taskRepository.findById(taskId).get().getAssignedManager().getEmail());
	            email.setEmailMsg("The Task is Overdue for the salesperson "+taskRepository.findById(taskId).get().getSalesPerson().getUser().getUserName()+".Please take further action on it.");
	        }
	        email.setEmailDate(LocalDate.now());
	        emailRepository.save(email);
	        return true;
	    }
	    return false;
	}
		
}
