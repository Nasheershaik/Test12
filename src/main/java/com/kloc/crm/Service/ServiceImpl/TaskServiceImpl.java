/**
 * @Author :RiteshSingh
 *@Date :06-July-2023
 *@FileName:TaskServiceImpl.java 
 */
package com.kloc.crm.Service.ServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kloc.crm.Entity.Contact;
import com.kloc.crm.Entity.ContactSub;
import com.kloc.crm.Entity.Customer;
import com.kloc.crm.Entity.Offering;
import com.kloc.crm.Entity.Opportunity;
import com.kloc.crm.Entity.OpportunitySub;
import com.kloc.crm.Entity.SalesPerson;
import com.kloc.crm.Entity.Status;
import com.kloc.crm.Entity.Task;
import com.kloc.crm.Entity.TaskSub;
import com.kloc.crm.Entity.User;
import com.kloc.crm.Exception.DataNotFoundException;
import com.kloc.crm.Exception.InvalidInput;
import com.kloc.crm.Exception.NullDataException;
import com.kloc.crm.Repository.ContactRepository;
import com.kloc.crm.Repository.ContactSubRepository;
import com.kloc.crm.Repository.CustomerRepository;
import com.kloc.crm.Repository.OfferingRepository;
import com.kloc.crm.Repository.OpportunityRepository;
import com.kloc.crm.Repository.OpportunitySubRepository;
import com.kloc.crm.Repository.SalesPersonRepository;
import com.kloc.crm.Repository.StatusRepo;
import com.kloc.crm.Repository.TaskRepository;
import com.kloc.crm.Repository.TaskSubRepository;
import com.kloc.crm.Repository.UserRepository;
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
	private ContactSubRepository contactSubRepository;
	@Autowired
	private StatusRepo statusRepo;
	@Autowired
	private TaskSubRepository taskSubRepository;
	@Autowired
	private OfferingRepository offeringRepository;
	@Autowired
	private OpportunityRepository opportunityRepository;
	@Autowired
	private OpportunitySubRepository opportunitySubRepository;
	@Autowired
	private CustomerRepository customerRepository;
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

	public List<Task> getAllTaskByStatus(String taskStatus) {
	    try {
	        List<Task> tasksWithStatus = new ArrayList<>();

	        List<Task> allTasks = taskRepository.findAll();
	        for (Task task : allTasks) {
	            SalesPerson salesPerson = task.getSalesPerson();
	            if (salesPerson.getUser().getStatus().getStatusValue().equalsIgnoreCase("active")) {
	                List<TaskSub> taskSubs = taskSubRepository.findAllByTaskOrderByStatusDateDesc(task);
	                if (!taskSubs.isEmpty()) {
	                    TaskSub lastTaskSub = taskSubs.get(0);
	                    if (lastTaskSub.getTaskStatus().getStatusValue().equalsIgnoreCase(taskStatus)) {
	                        tasksWithStatus.add(task);
	                    }
	                }
	            }
	        }

	        return tasksWithStatus;
	    } catch (Exception e) {
	        // Handle the exception here (e.g., logging or rethrowing)
	        return Collections.emptyList(); // Return an empty list in case of an exception
	    }
	}
	@Override
	public List<Task> getAllTaskByTaskOutcome(String taskOutcome) {
	    List<Task> taskWithOutcome = new ArrayList<>();
	    List<Task> allTasks = taskRepository.findAll();

	    for (Task task : allTasks) {
	        SalesPerson salesPerson = task.getSalesPerson();
	        
	        if (salesPerson != null && salesPerson.getUser() != null && 
	            salesPerson.getUser().getStatus() != null &&
	            salesPerson.getUser().getStatus().getStatusValue().equalsIgnoreCase("Active")) {
	            
	            List<TaskSub> taskSubs = taskSubRepository.findByTask(task);
	            
	            if (taskSubs != null && !taskSubs.isEmpty()) {
	                TaskSub taskSub = taskSubs.get(taskSubs.size() - 1);
	                
	                if (taskSub.getTaskOutcome() != null && 
	                    taskSub.getTaskOutcome().getStatusValue() != null &&
	                    taskSub.getTaskOutcome().getStatusValue().equalsIgnoreCase(taskOutcome)) {
	                    
	                    taskWithOutcome.add(task);
	                }
	            }
	        }
	    }
	    
	    return taskWithOutcome;
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
		if(userRepository.findById(managerId).get().getRole().getStatusValue().equalsIgnoreCase("SalesPerson")) {throw new InvalidInput("task cannot assigned by salesperson");}
		SalesPerson salesPerson=salesPersonRepository.findById(salesPersonId).orElseThrow(()->new DataNotFoundException("SalesPerson is not found:Enter the existing salesPersonId"));
		task.setSalesPerson(salesPerson);
		User user=userRepository.findById(managerId).orElseThrow(()->new DataNotFoundException("manager is not found:Enter the existing managerId"));
		task.setAssignedManager(user);
		Contact contact=contactRepository.findById(contactId).orElseThrow(()->new DataNotFoundException("conatct is not found:Enter the existing contactId"));
		Offering offering=offeringRepository.findById(offeringId).orElseThrow(()->new DataNotFoundException("Offering is not found:Enter the existing offeringId"));
		;
		if(!customerRepository.existsByContact(contact))
		{
			ContactSub contactSub=new ContactSub();
			contactSub.setContactId(contact);
			Status lifecycleStage=statusRepo.findByStatusTypeAndStatusValue("Lead", "New");
			contactSub.setLifeCycleStage(lifecycleStage);
			contactSub.setStageDate(LocalDate.now());
			ContactSub contactSub1=contactSubRepository.save(contactSub);
			task.setContactSub(contactSub1);
			Task task1=taskRepository.save(task);
//			contactSub1.setTaskId(task1);
			contactSubRepository.save(contactSub1);
			TaskSub taskSub=new TaskSub();
			Status status=statusRepo.findByStatusTypeAndStatusValue("Task_Status","Open");
			taskSub.setTaskStatus(status);
			taskSub.setTask(task1);
			taskSub.setStatusDate(LocalDate.now());
			taskSub.setOfferingId(offering);
			taskSubRepository.save(taskSub);
			return task1;
		}
		else
		{
			ContactSub contactSub=new ContactSub();
			contactSub.setContactId(contact);
			Status lifecycleStage=statusRepo.findByStatusTypeAndStatusValue("Sales Qualified", "Qualified");
			contactSub.setLifeCycleStage(lifecycleStage);
			contactSub.setStageDate(LocalDate.now());
			ContactSub contactSub1=contactSubRepository.save(contactSub);
			task.setContactSub(contactSub1);
			Task task1=taskRepository.save(task);
//			contactSub1.setTaskId(task1);
			contactSubRepository.save(contactSub1);
			TaskSub taskSub=new TaskSub();
			taskSub.setTask(task1);
			taskSub.setStatusDate(LocalDate.now());
			taskSub.setOfferingId(offering);
			taskSub.setTaskStatus(statusRepo.findByStatusTypeAndStatusValue("Task_status","In Progress"));
			taskSub.setTaskOutcome(statusRepo.findByStatusTypeAndStatusValue("Task_Outcome", "Qualified"));
			taskSubRepository.save(taskSub);
			Opportunity opportunity=new Opportunity();
			opportunity.setContactSub(contactSub1);
			opportunity.setOffering(offering);
			opportunity.setOpportunityCreatedDate(LocalDate.now());
			Opportunity opportunity1=opportunityRepository.save(opportunity);
			OpportunitySub opportunitySub=new OpportunitySub();
			opportunitySub.setOpportunityStatusDate(LocalDate.now());
			opportunitySub.setOpportunityId(opportunity1);
			opportunitySub.setStatus(statusRepo.findByStatusTypeAndStatusValue("opportunity/deal","Opportunity"));
			opportunitySubRepository.save(opportunitySub);
			return task1;
		}
		
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
		if(managerId.isEmpty()||managerId.equals(null)) {throw new NullDataException("Enter the valid managerID");}
		userRepository.findById(managerId).orElseThrow(()->new DataNotFoundException("Manager is not found:Enter the existing ManagerId"));
		return taskRepository.findAll().parallelStream().filter(e->e.getAssignedManager().getUserId().toLowerCase().equals(managerId.toLowerCase())).toList();
	}
	@Override
	public List<Task> getAllTaskByContactId(String contactId) {
		if(contactId.isEmpty()||contactId.equals(null)) {throw new NullDataException("Enter the Valid contactId");}
		contactRepository.findById(contactId).orElseThrow(()->new DataNotFoundException("Contact is not persent:Enter the existing ContactId"));
		return taskRepository.findAll().stream().filter(e->e.getContactSub().getContactId().getContactId().toLowerCase().equals(contactId.toLowerCase())).toList();
	}
	@Override
	public Task updateTaskBySalesPersonId(String taskId, String salesPersonId) 
	{	
		if(taskId.isEmpty()||taskId.equals(null)) {throw new NullDataException("Enter The Valid taskId");}
		if(salesPersonId.isEmpty()||salesPersonId.equals(null)) {throw new NullDataException("SalesPerson is not found:Enter the existing salesPersonId");}
		Task task=taskRepository.findById(taskId).orElseThrow(()->new DataNotFoundException("task is not found:Enter the vaild taskId"));
		SalesPerson salesPerson=salesPersonRepository.findById(salesPersonId).orElseThrow(()->new DataNotFoundException("salesperson is not found:enter the valid salespersonId"));
		List<TaskSub> list=taskSubRepository.findAll().stream().filter(e->e.getTask().getTaskId().toLowerCase().equals(taskId.toLowerCase())).toList();
		TaskSub a=list.get(list.size()-1);
		if(a.getTaskStatus().getStatusValue().equalsIgnoreCase("Transferred")) {throw new InvalidInput("this task status is transferred you can't update this task");}
		Task task1=new Task();
		task1.setAssignedManager(task.getAssignedManager());
		task1.setContactSub(task.getContactSub());
		task1.setDueDate(task.getDueDate());
		task1.setStartDate(task.getStartDate());
		task1.setTaskDescription(task.getTaskDescription());
		task1.setSalesPerson(salesPerson);
		task1.setTaskDescription("continuation of "+task.getTaskId());
		Task task2=taskRepository.save(task1);
		task.setTaskDescription("Transferred as "+task2.getTaskId());
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
			a.setTaskStatus(statusRepo.findByStatusValue("Transferred"));
			 taskSubRepository.save(a);
	
		return task2;
	}
	@Override
	public Task updateTaskByContactId(String taskId, String contactId) 
	{
		if(taskId.isEmpty()||taskId.equals(null)) {throw new NullDataException("Enter The Valid taskId");}
		if(contactId.isEmpty()||contactId.equals(null)) {throw new NullDataException("Enter the valid contactId");}
		Task task=taskRepository.findById(taskId).orElseThrow(()->new DataNotFoundException("Task not found:Enter the existing TaskId"));
		Contact contact=contactRepository.findById(contactId).orElseThrow(()->new DataNotFoundException("contact is not found:Enter the existing contactId"));
		List<TaskSub> list=taskSubRepository.findAll().stream().filter(e->e.getTask().getTaskId().toLowerCase().equals(taskId.toLowerCase())).toList();
		TaskSub a=list.get(list.size()-1);
		if(a.getTaskStatus().getStatusValue().equalsIgnoreCase("Transferred")) {throw new InvalidInput("this task status is transferred you can't update this task");}
		Task task1=new Task();
		task1.setAssignedManager(task.getAssignedManager());
		ContactSub contactSub=new ContactSub();
		contactSub.setContactId(contact);
		contactSub.setLifeCycleStage(task.getContactSub().getLifeCycleStage());
		contactSub.setStageDate(LocalDate.now());
		ContactSub contactSub1=contactSubRepository.save(contactSub);
		task1.setContactSub(contactSub1);
		task1.setDueDate(task.getDueDate());
		task1.setStartDate(task.getStartDate());
		task1.setTaskDescription(task.getTaskDescription());
		task1.setSalesPerson(task.getSalesPerson());
		task1.setTaskDescription("continuation of "+task.getTaskId());
		Task task2=taskRepository.save(task1);
//		contactSub1.setTaskId(task2);
		ContactSub contactSub2=contactSubRepository.save(contactSub1);
		List<Opportunity> opportunity1=opportunityRepository.findAll().stream().filter(e->e.getContactSub().getContactSubId().equals(task.getContactSub().getContactSubId())).toList();
		if(!opportunity1.isEmpty()&&!opportunity1.equals(null)) {
			Opportunity oppor=opportunity1.get(0);
			oppor.setContactSub(contactSub2);
			opportunityRepository.save(oppor);
		}
		task.setTaskDescription("Transferred as "+task2.getTaskId());
		
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
			a.setTaskStatus(statusRepo.findByStatusValue("Transferred"));
			 taskSubRepository.save(a);
			
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
		List<TaskSub> list=taskSubRepository.findAll().stream().filter(e->e.getTask().getTaskId().toLowerCase().equals(taskId.toLowerCase())).toList();
		TaskSub a=list.get(list.size()-1);
		if(a.getTaskStatus().getStatusValue().equalsIgnoreCase("Transferred")) {throw new InvalidInput("this task status is transferred you can't update this task");}
		Task task1=new Task();
		task1.setAssignedManager(task.getAssignedManager());
		ContactSub contactSub=new ContactSub();
		contactSub.setContactId(contact);
		contactSub.setLifeCycleStage(task.getContactSub().getLifeCycleStage());
		contactSub.setStageDate(LocalDate.now());
		ContactSub contactSub1=contactSubRepository.save(contactSub);
		task1.setContactSub(contactSub1);
		task1.setDueDate(task.getDueDate());
		task1.setStartDate(task.getStartDate());
		task1.setTaskDescription(task.getTaskDescription());
		task1.setSalesPerson(salesPerson);
		task1.setTaskDescription("continuation of "+task.getTaskId());
		Task task2=taskRepository.save(task1);
	//	contactSub1.setTaskId(task2);
		ContactSub contactSub2=contactSubRepository.save(contactSub1);
		List<Opportunity> opportunity1=opportunityRepository.findAll().stream().filter(e->e.getContactSub().getContactSubId().equals(task.getContactSub().getContactSubId())).toList();
		if(!opportunity1.isEmpty()&&!opportunity1.equals(null)) {
			Opportunity oppor=opportunity1.get(0);
			oppor.setContactSub(contactSub2);
			opportunityRepository.save(oppor);
		}
		task.setTaskDescription("Transferred as "+task2.getTaskId());
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
			 a.setTaskStatus(statusRepo.findByStatusValue("Transferred"));
			 taskSubRepository.save(a);
		return task2;
	}
	@Override
	public TaskSub updateTaskSubByTaskId(TaskSub taskSub, String taskId) 
	{
		if(taskSub.equals(null)) {throw new InvalidInput("TaskSub can't be null");}
		if(taskId.isEmpty()||taskId.equals(null)) {throw new NullDataException("Enter The Valid taskId");}
		Task task=taskRepository.findById(taskId).orElseThrow(()->new DataNotFoundException("Task is not found:Enter the existing the taskId"));
		List<TaskSub> list=taskSubRepository.findAll().stream().filter(e->e.getTask().getTaskId().equals(taskId)).toList();	
		TaskSub existingTaskSub=list.get(list.size()-1);
		if(existingTaskSub.getTaskStatus().getStatusValue().equalsIgnoreCase("Transferred")) {throw new InvalidInput("this task status is transferred you can't update this task");}
		if(existingTaskSub.getTaskStatus().getStatusValue().equalsIgnoreCase("Completed")) 
		{
			throw new InvalidInput(taskId+" is already completed.So, we can't update the task status");
		}
		TaskSub newTaskSub=new TaskSub();
		if(!taskSub.getTaskStatus().equals(null))
		{
			newTaskSub.setTaskStatus(statusRepo.findByStatusTypeAndStatusValue("Task_Status", taskSub.getTaskStatus().getStatusValue()));
			newTaskSub.setStatusDate(LocalDate.now());
		}
		else 
		{
			newTaskSub.setTaskStatus(existingTaskSub.getTaskStatus());
		}
		if(!taskSub.getTaskOutcome().equals(null))
		{
			newTaskSub.setTaskOutcome(statusRepo.findByStatusTypeAndStatusValue("Task_Outcome", taskSub.getTaskOutcome().getStatusValue()));
		}
		else
		{
			newTaskSub.setTaskOutcome(existingTaskSub.getTaskOutcome());
		}
		if(!taskSub.getFollowUpDate().equals(null)) 
		{
		 newTaskSub.setFollowUpDate(taskSub.getFollowUpDate());
		}
		else
		{
			newTaskSub.setFollowUpDate(existingTaskSub.getFollowUpDate());
		}
		if(!taskSub.getTaskFeedback().equals(null)||!taskSub.getTaskFeedback().equals(""))
		{
			newTaskSub.setTaskFeedback(taskSub.getTaskFeedback());
		}
		else
		{
			newTaskSub.setTaskFeedback(existingTaskSub.getTaskFeedback());
		}
		if(!(taskSub.getLeadFeedback().equals(null))||!(taskSub.getLeadFeedback().equals("")))
				{
					newTaskSub.setLeadFeedback(taskSub.getLeadFeedback());
					
				}
		else
		{
			newTaskSub.setLeadFeedback(existingTaskSub.getLeadFeedback());
		}
		if(!taskSub.getLeadFeedback().equals(null)||!taskSub.getTaskFeedback().equals(null)||!(taskSub.getLeadFeedback().equals(""))||!taskSub.getTaskFeedback().equals(""))
		{
			newTaskSub.setFeedbackDate(LocalDate.now());
		}
		else
		{
			newTaskSub.setFeedbackDate(existingTaskSub.getFeedbackDate());
		}
		if(!taskSub.getSalesActivity().equals(null))
		{
			newTaskSub.setSalesActivity(statusRepo.findByStatusTypeAndStatusValue("Sales_ActivityStatus", taskSub.getSalesActivity().getStatusValue()));
		}
		else {
			newTaskSub.setSalesActivity(existingTaskSub.getSalesActivity());
		}
		newTaskSub.setOfferingId(existingTaskSub.getOfferingId());
		newTaskSub.setTask(task);
		ContactSub contactSub=contactSubRepository.findById(task.getContactSub().getContactSubId()).get();
		if(newTaskSub.getTaskOutcome().getStatusValue().equalsIgnoreCase("Interested"))
		{
			contactSub.setLifeCycleStage(statusRepo.findByStatusTypeAndStatusValue("Lead", "Interested"));
			contactSub.setStageDate(LocalDate.now());
			contactSubRepository.save(contactSub);
		}
		else if(newTaskSub.getTaskOutcome().getStatusValue().equalsIgnoreCase("Not Interested"))
		{
			
			contactSub.setLifeCycleStage(statusRepo.findByStatusTypeAndStatusValue("Lead", "Not Interested"));
			contactSub.setStageDate(LocalDate.now());
			contactSubRepository.save(contactSub);
		}
		else if(newTaskSub.getTaskOutcome().getStatusValue().equalsIgnoreCase("Qualified"))
		{
			contactSub.setLifeCycleStage(statusRepo.findByStatusTypeAndStatusValue("Sales Qualified","Qualified"));
			contactSub.setStageDate(LocalDate.now());
			contactSubRepository.save(contactSub);
			Opportunity opportunity=new Opportunity();
			opportunity.setContactSub(contactSub);
			opportunity.setOffering(newTaskSub.getOfferingId());
			opportunity.setOpportunityCreatedDate(LocalDate.now());
			Opportunity opportunity1=opportunityRepository.save(opportunity);
			OpportunitySub opportunitySub=new OpportunitySub();
			opportunitySub.setOpportunityStatusDate(LocalDate.now());
			opportunitySub.setOpportunityId(opportunity1);
			opportunitySub.setStatus(statusRepo.findByStatusTypeAndStatusValue("opportunity/deal","Opportunity"));
			opportunitySubRepository.save(opportunitySub);
		}
		else if(newTaskSub.getTaskStatus().getStatusValue().equalsIgnoreCase("Completed")&&newTaskSub.getTaskOutcome().getStatusValue().equalsIgnoreCase("Done"))
		{
			contactSub.setLifeCycleStage(statusRepo.findByStatusTypeAndStatusValue("Customer","Won"));
			contactSub.setStageDate(LocalDate.now());
			contactSubRepository.save(contactSub);
			Opportunity opportunity3=opportunityRepository.findAll().stream().filter(e->e.getContactSub().equals(task.getContactSub())).findFirst().get();
			List<OpportunitySub> opportunitySubList=opportunitySubRepository.findAll().stream().filter(e->e.getOpportunityId().getOpportunityId().equalsIgnoreCase(opportunity3.getOpportunityId())).toList();
			OpportunitySub opportunitySub=opportunitySubList.get(opportunitySubList.size()-1);
			OpportunitySub newOpportunitySub=new OpportunitySub();
			newOpportunitySub.setCurrency(opportunitySub.getCurrency());
			newOpportunitySub.setDuration(opportunitySub.getDuration());
			newOpportunitySub.setNoOfInstallements(opportunitySub.getNoOfInstallements());
			newOpportunitySub.setOpportunityStatusDate(LocalDate.now());
			newOpportunitySub.setOpportunityId(opportunity3);
			newOpportunitySub.setPrice(opportunitySub.getPrice());
			newOpportunitySub.setCurrency("₹ Indian Rupees");
			newOpportunitySub.setStatus(statusRepo.findByStatusTypeAndStatusValue("opportunity/deal","Deal"));
			opportunitySubRepository.save(newOpportunitySub);
			if(!customerRepository.existsByContact(contactSub.getContactId()))
			{
				Customer customer=new Customer();
				customer.setContact(contactSub.getContactId());
				customer.setCustomerCreatedDate(LocalDate.now());
				customerRepository.save(customer);
			}
			
		}
		else if(newTaskSub.getTaskOutcome().getStatusValue().equalsIgnoreCase("Lost"))
		{
			contactSub.setLifeCycleStage(statusRepo.findByStatusTypeAndStatusValue("Sales Qualified","Lost"));
			contactSub.setStageDate(LocalDate.now());
			contactSubRepository.save(contactSub);
		}
		else if(newTaskSub.getTaskOutcome().getStatusValue().equalsIgnoreCase("Negotiation"))
		{
			contactSub.setLifeCycleStage(statusRepo.findByStatusTypeAndStatusValue("Sales Qualified","Negotiation"));
			contactSub.setStageDate(LocalDate.now());
			contactSubRepository.save(contactSub);
		}
		return taskSubRepository.save(newTaskSub);
	}
	@Override
	public List<Task> getAllTaskBySalespersonIdAndStatus(String salespersonId, String taskstatus) {
		SalesPerson salesPerson=salesPersonRepository.findById(salespersonId).orElseThrow(()->new DataNotFoundException("salesperson is not found with this id in database"));
		List<Task> task=taskRepository.findBySalesPerson(salesPerson);
		if(task.isEmpty())
		{
			throw new DataNotFoundException("No task is available for this salesperson");
		}
		try {
			return task.stream()
			        .filter(e -> e.getSalesPerson().getUser().getStatus().getStatusValue().toLowerCase().equals("active"))
			        .flatMap(e -> {
			            List<TaskSub> list = taskSubRepository.findAll().stream()
			                .filter(a -> a.getTask().equals(e))
			                .toList();
			            if (!list.isEmpty()) {
			                if (list.get(list.size() - 1).getTaskStatus().getStatusValue().toLowerCase().equals(taskstatus.toLowerCase())) {
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
	public List<Task> getAllTaskBySalespersonIdAndOutcome(String salespersonId, String taskOutcome) {
		SalesPerson salesPerson=salesPersonRepository.findById(salespersonId).orElseThrow(()->new DataNotFoundException("salesperson is not found with this id in database"));
		List<Task> task=taskRepository.findBySalesPerson(salesPerson);
		if(task.isEmpty())
		{
			throw new DataNotFoundException("No task is available for this salesperson");
		}
		try {
			return task.stream()
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
	public List<Task> getAllTaskByDateRangeBySalesPersonIdByTaskStatusByLifeCycleStage(LocalDate intialdate,
			LocalDate finalDate, String salespersonId) {
		
		if(intialdate.equals(null)||finalDate.equals(null)||salespersonId.equals(null)||salespersonId.equals("")) {throw new NullDataException("compulsory to fill intialDate and finalDate and salespersonid and status type");}
		salesPersonRepository.findById(salespersonId).orElseThrow(()->new DataNotFoundException("salesperson is not persent in database"));
		return taskRepository.findAll().stream().filter(e->e.getSalesPerson().getUser().getStatus().getStatusValue().toLowerCase().equalsIgnoreCase("active")&&e.getSalesPerson().getSalespersonId().equalsIgnoreCase(salespersonId)).map(e->{
			List<TaskSub> list=taskSubRepository.findAll().stream().filter(a->a.getTask().equals(e)).toList();
			LocalDate statusdate=list.get(list.size()-1).getStatusDate();
			if((statusdate.isBefore(finalDate)||statusdate.isEqual(finalDate))&&(statusdate.isAfter(intialdate)||statusdate.isEqual(intialdate))) 
			{
				return list.get(list.size()-1).getTask();
			}
			else {return null;}
		}).toList().stream().filter(a->!a.equals(null)).toList();
	}
		
}
