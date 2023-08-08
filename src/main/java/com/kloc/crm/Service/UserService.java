/**
 * @Author :Avinash
 * @File_Name:UserService.java
 * @Date:05-Jul-2023
 */
package com.kloc.crm.Service;

import java.util.List;

import com.kloc.crm.Entity.User;
import com.kloc.crm.dto.UserDto;
import com.kloc.crm.dto.UserNDto;


/**
 * Interface for managing User entities.
 */
public interface UserService {
    
    /**
     * Creates a new User.
     *
     * @param user  The User object to be created
     * @return      The created User object
     */
    User createUser(User user,String reportingTo );//String value);
    
    /**
     * Retrieves a list of all Users.
     *
     * @return  A list of all User objects
     */
    List<User> getAllUsers();
    
    /**
     * Retrieves a User by their ID.
     *
     * @param id  The ID of the User to retrieve
     * @return    The User object with the specified ID
     */
    User getUserById(String id);
    
    /**
     * Updates an existing User.
     *
     * @param user  The updated User object
     * @param id    The ID of the User to update
     * @return      The updated User object
     */
    User updateUser(User user, String id);
    
    /**
     * Retrieves a User by their username.
     *
     * @param name  The username of the User to retrieve
     * @return      The User object with the specified username
     */
    List<User> getUserByName(String name);
    
    /**
     * Retrieves Users based on their roles.
     *
     * @param role  The role of the Users to retrieve
     * @return      A list of User objects with the specified role
     */
    List<User> getUserByRole(String role);
    
    /**
     * Retrieves all Users who are reporting to a specific manager.
     *
     * @param reportingTo  The manager's name to filter Users by
     * @return             A list of User objects reporting to the specified manager
     */
    List<User> getUserByManagerName(String reportingTo);
    /**
     * Retrieves  User based on email
     *
     * @return             A  User object based on email
     */
	User getUserByEmail(String email);
	
	User getReportingToByUserId(String userId);
	
	User updateStatus(String userId,String statusValue);
	
	UserNDto getUserRoleAndStatusValueById(String userId);
	
	UserDto getUserRoleAndStatusvalueAndreportingToByEmailId(String email);
	
	List<UserNDto> getAllUserNDto();
	
	 String updateUserByAdmin(String userId,String srole ,String reportingUserId);
	 
	 List<UserNDto> getUsersByTheirRoles(String role);
	 
	 List<UserNDto> getUsersBasedOnTheirName(String name);
	 
	 UserNDto getUserBasedOnTheirid(String userId);
	
}

