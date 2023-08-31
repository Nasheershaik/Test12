/**
 * @Author :RiteshSingh
 *@Date :06-July-2023
 *@FileName:TaskSubRepository.java 
 */
package com.kloc.crm.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kloc.crm.Entity.Task;
import com.kloc.crm.Entity.TaskSub;
@Repository
public interface TaskSubRepository extends JpaRepository<TaskSub, String>
{
	/**
     * Retrieves a list of tasks with the given status.
     *
     * @param status The task status
     * @return A list of tasks with the specified status
     */
	List<TaskSub> findByTask(Task task);

	List<TaskSub> findAllByTaskOrderByStatusDateDesc(Task task);
}
