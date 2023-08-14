/**
 * @Author :Avinash
 * @File_Name:SalesPersonRepository.java
 * @Date:05-Jul-2023
 */
package com.kloc.crm.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.kloc.crm.Entity.SalesPerson;


/**
 * Repository interface for performing database operations on SalesPerson entities.
 */
@Repository
@EnableJpaRepositories
public interface SalesPersonRepository extends JpaRepository<SalesPerson, String> {
	
	List<SalesPerson> findAllByTarget(int target);
	
	
	
	
	}

