/**
 * @Author :Avinash
 * @File_Name:UserRepository.java
 * @Date:05-Jul-2023
 */

package com.kloc.crm.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kloc.crm.Entity.Status;
import com.kloc.crm.Entity.User;


@Repository
/**
 * Repository interface for performing database operations on User entities.
 */
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, String> {
    
    /**
     * Retrieves a list of Users with the specified role.
     *
     * @param role  The role of the Users to retrieve
     * @return      A list of User objects with the specified role
     */
    List<User> findAllByRole(Status role);
    
    /**
     * Retrieves a User by their username.
     *
     * @param userName  The username of the User to retrieve
     * @return          The User object with the specified username
     */
    List<User> findAllByUserName(String userName);
    
    
   
    
     User findByEmail(String email);
    
    
}

