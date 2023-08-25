/**
 * @Author :RiteshSingh
 *@Date :06-July-2023
 *@FileName:TaskRepository.java 
 */
package com.kloc.crm.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kloc.crm.Entity.SalesPerson;
import com.kloc.crm.Entity.Task;
/**
 * The TaskRepository interface is a repository for managing Task entities.
 * It provides methods to perform CRUD operations on Task objects.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, String>
{
	
	/**
     * Retrieves a list of tasks associated with the given sales person ID.
     *
     * @param salesPersonId The ID of the sales person
     * @return A list of tasks associated with the sales person
     */
	List<Task> findBySalesPerson(SalesPerson salesPerson);
	
	
}
