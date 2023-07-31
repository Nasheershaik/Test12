package com.kloc.crm.Repository;
/**
 * @Author :Nasheer
 *@Date :06-July-2023
 *@FileName:StatusRepo.java
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;
import com.kloc.crm.Entity.*;
public interface StatusRepo extends JpaRepository<Status,String>
{
	/**
	 * Finds a Status object based on the provided tableName, statusType, and statusValue.
	 *
	 * @param tableName  The name of the table associated with the Status.
	 * @param statusType The type of the Status (e.g., "Active" or "Inactive").
	 * @param statusValue The specific value representing the Status (e.g., "Approved" or "Pending").
	 * @return The found Status object, or null if no matching Status is found.
	 */
	 Status findByTableNameAndStatusTypeAndStatusValue(String tableName,String statusType,String statusValue);
	 /**
	  * Finds a Status object based on the provided statusValue.
	  *
	  * @param statusValue The specific value representing the Status (e.g., "Approved" or "Pending").
	  * @return The found Status object, or null if no matching Status is found.
	  */
	 Status findByStatusValue(String statusValue);
	 /**
	  * Finds a Status object based on the provided statusType and statusValue.
	  *
	  * @param statusType The type of the Status (e.g., "Active" or "Inactive").
	  * @param statusValue The specific value representing the Status (e.g., "Approved" or "Pending").
	  * @return The found Status object, or null if no matching Status is found.
	  */
	 Status findByStatusTypeAndStatusValue(String statusType,String statusValue);
	 /**
	  * Checks if a Status object exists with the provided tableName, statusType, statusValue, and statusDescription.
	  *
	  * @param tableName        The name of the table associated with the Status.
	  * @param statusType       The type of the Status (e.g., "Active" or "Inactive").
	  * @param statusValue      The specific value representing the Status (e.g., "Approved" or "Pending").
	  * @param statusDescription The description or additional information about the Status.
	  * @return True if a matching Status object exists, otherwise false.
	  */
	 boolean existsByTableNameAndStatusTypeAndStatusValueAndStatusDescription(String tableName, String statusType,String statusValue, String statusDescription);
//	 @Query(value = "select * from status where table_name = ?1 && status_type = ?2 && status_value = ?3", nativeQuery = true)
//	 Status getStatusId(@RequestParam String tableName, @RequestParam String statusType, @RequestParam String statusValue);	
//	 @Query(value="select * from Status where status_value=?",nativeQuery = true)
//	 Status findByStatusValue(String status_value);
//	 @Query(value = "select * from status where status_type = ?1 && status_value = ?2", nativeQuery = true)
//	 Status findByStatusTypeAndStatusValue( @RequestParam String statusType, @RequestParam String statusValue);
}