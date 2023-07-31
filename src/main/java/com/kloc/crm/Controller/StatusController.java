package com.kloc.crm.Controller;

import org.apache.logging.log4j.LogManager;

/**
 * This class represents the StatusController responsible for handling status-related requests.
 *
 * @Author: Nasheer
 * @Date: 06-July-2023
 * @FileName: StatusController.java
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.Logger;

import com.kloc.crm.Entity.Status;
import com.kloc.crm.Exception.DataNotFoundException;
import com.kloc.crm.Service.StatusService;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/app/statuses")
public class StatusController {

    @Autowired
    private final StatusService statusService;

    private static final Logger Log = LogManager.getLogger(StatusController.class);

    /**
     * Constructs a new instance of the StatusController.
     *
     * @param statusService the service used for handling status-related operations
     */
    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    /**
     * Retrieves all statuses.
     *
     * @return ResponseEntity with a list of Status objects and an HTTP status code
     */
    @GetMapping("/getstatus")
    public ResponseEntity<List<Status>> getAllStatuses() {
    	Log.debug("Attempting to retrieve all statuses.");
        List<Status> statuses = statusService.getAllStatuses();
        if (!statuses.isEmpty()) {
            Log.info("Retrieved all Statuses successfully.");
            return new ResponseEntity<>(statuses, HttpStatus.OK);
        } else {
            Log.error("No statuses found in the database.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves a status by its ID.
     *
     * @param statusId the ID of the status to retrieve
     * @return ResponseEntity with a Status object and an HTTP status code
     */
    @GetMapping("/{statusId}")
    public ResponseEntity<Status> getStatusById(@PathVariable String statusId) {
    	 Log.debug("Attempting to retrieve status with ID: " + statusId);
        Status status = statusService.findByid(statusId);
        if (status != null) {
            Log.info("Status details retrieved successfully for statusId: " + statusId);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } else {
            Log.error("No Status details found for statusId: " + statusId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Updates a status by its ID.
     *
     * @param statusId      the ID of the status to update
     * @param updatedStatus the updated Status object
     * @return ResponseEntity with the updated Status object and an HTTP status code
     */
    @PutMapping("/{statusId}")
    public ResponseEntity<Status> updateStatusById(@PathVariable("statusId") String statusId,
                                                   @RequestBody Status updatedStatus) {
    	 Log.debug("Attempting to update status with ID: " + statusId);
        Status existingStatus = statusService.findByid(statusId);
        if (existingStatus != null) {
            // Update the properties of the existing status with the values from the updated status
            existingStatus.setStatusValue(updatedStatus.getStatusValue());
            existingStatus.setStatusType(updatedStatus.getStatusType());
            existingStatus.setTableName(updatedStatus.getTableName());
            existingStatus.setStatusDescription(updatedStatus.getStatusDescription());
            // Save the updated status
            Status updatedStatus1 = statusService.saveStatus(existingStatus);
            Log.info("Status values updated successfully for statusId: " + statusId);
            return new ResponseEntity<>(updatedStatus1, HttpStatus.OK);
        } else {
            Log.error("Unable to update status. No Status found for statusId: " + statusId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Creates a new status.
     *
     * @param status the Status object to be created
     * @return ResponseEntity with the created Status object and an HTTP status code
     */
    @PostMapping("/createstatus")
    public ResponseEntity<Status> saveStatus(@RequestBody Status status) {
    	Log.debug("Attempting to create a new Status.");
        Status savedStatus = statusService.saveStatus(status);
        if (savedStatus != null) {
            Log.info("New Status created successfully.");
            return new ResponseEntity<>(savedStatus, HttpStatus.CREATED);
        } else {
            Log.error("Failed to create a new Status.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Deletes a status by its ID.
     *
     * @param statusId the ID of the status to delete
     * @return ResponseEntity with the deleted Status object and an HTTP status code
     */
    @DeleteMapping("/deletestatus/{statusId}")
    public ResponseEntity<Status> deleteStatusById(@PathVariable String statusId) {
    	 Log.debug("Attempting to delete status with ID: " + statusId);
        Status deletedStatus = statusService.deleteByid(statusId);
        if (deletedStatus != null) {
            Log.info("Status with statusId: " + statusId + " deleted successfully.");
            return new ResponseEntity<>(deletedStatus, HttpStatus.OK);
        } else {
            Log.error("Failed to delete Status with statusId: " + statusId);
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
    }

    @GetMapping("/{statusType}/{tableName}")
    public List<String> getStatusValuesByTypeAndTableName(
            @PathVariable String statusType,
            @PathVariable String tableName
    ) {
        // Check if either statusType or tableName is missing
        if (statusType == null || tableName == null) {
            throw new DataNotFoundException("Either statusType or tableName is missing.");
        }
        List<String> statusValues = statusService.getStatusValuesByTypeAndTableName(statusType, tableName);
        if (statusValues.isEmpty()) {
            // You can also return a NOT_FOUND response here if no data is found for the given parameters.
            // return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            // For this example, we'll return an OK response with an empty list when no data is found.
            throw new DataNotFoundException("Status not found for statusType: " + statusType + ", tableName: " + tableName);
        } else {
            return statusValues;
        }

    }
}
