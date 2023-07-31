package com.kloc.crm.Service;

/**
 * @Author :Nasheer
 *@Date :06-July-2023
 *@FileName:.java
 */
import java.util.List;

import org.springframework.stereotype.Service;

import com.kloc.crm.Entity.*;

@Service
public interface StatusService {
	List<Status>getAllStatuses();
	
	Status saveStatus(Status status);
	
	Status findByid(String  StatusId);
	
	Status deleteByid(String StatusId);

	List<String> getStatusValuesByTypeAndTableName(String statusType, String tableName);
	
	
	
}