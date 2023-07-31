package com.kloc.crm.Service.ServiceImpl;
/**
 * @Author :Nasheer
 *@Date :06-July-2023
 *@FileName:StatusServiceImpl.java
 */

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kloc.crm.Entity.Status;
import com.kloc.crm.Exception.DataNotFoundException;
import com.kloc.crm.Repository.StatusRepo;
import com.kloc.crm.Service.StatusService;

/**
 * The StatusServiceImpl class implements the StatusService interface and provides
 * methods to interact with statuses in the CRM system.
 */

/**
 * @Author :Nasheer
 *@Date :06-July-2023
 *@FileName:StatusServiceImpl.java
 */

@Service
public class StatusServiceImpl implements StatusService {
    @Autowired
    private StatusRepo statusRepo;

    /**
     * Constructs a new StatusServiceImpl with the specified StatusRepo.
     * @param statusRepo The StatusRepo used to interact with statuses.
     */
    public StatusServiceImpl(StatusRepo statusRepo) {
        this.statusRepo = statusRepo;
    }

    /**
     * Retrieves all statuses.
     * @return A list of all statuses.
     */
    @Override
    public List<Status> getAllStatuses() {
        return statusRepo.findAll();
    }

    /**
     * Retrieves a status by its ID.
     * @param statusId The ID of the status to retrieve.
     * @return The retrieved status.
     */
    @Override
    public Status findByid(String statusId) 
    {
    	return statusRepo.findById(statusId).orElseThrow(()-> new DataNotFoundException("statusID is not present"));
     }


    /**
     * Saves a status.
     * @param status The status to save.
     * @return The saved status.
     */
    @Override
    public Status saveStatus(Status status) {
    	
    		return statusRepo.save(status);    		
    	}

    /**
     * Deletes a status by its ID.
     * @param statusId The ID of the status to delete.
     * @return The deleted status, or null if the status was not found.
     */
    @Override
    public Status deleteByid(String statusId) {
        Status status = statusRepo.findById(statusId).orElseThrow(()-> new DataNotFoundException("StatusID is  not present"));
        if (status != null) {
            statusRepo.delete(status);
        }
        return status;
    }
    @Override
    public List<String> getStatusValuesByTypeAndTableName(String statusType, String tableName) {
        List<Status> allStatuses = statusRepo.findAll();

        // Use Java Streams to filter the statuses based on statusType and tableName
        List<String> statusValues = allStatuses.stream()
                .filter(status -> status.getStatusType().equals(statusType) && status.getTableName().equals(tableName))
                .map(Status::getStatusValue) // Extract the statusValue from each Status object
                .collect(Collectors.toList());
        
        if(statusValues.isEmpty()) {
        	System.out.println("value is empty");
        } else {
        	 return statusValues;
        }

        return statusValues;
    }
    }
   

