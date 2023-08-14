package com.kloc.crm.Repository;
/**
 * @Author :Nasheer
 *@Date :06-July-2023
 *@FileName:EmailRepo.java
 */
import org.springframework.data.jpa.repository.JpaRepository;

import com.kloc.crm.Entity.*;


public interface EmailRepo extends JpaRepository<Email,String>
{

    // Custom query method to find the first email with a specific taskId and emailType,
    // ordered by emailDate in descending order
    Email findFirstByTaskTaskIdAndEmailTypeOrderByEmailDateDesc(String taskId, String emailType);
    int countByTaskAndEmailType(Task task, String emailType);
}