/**
 * @Author :Avinash
 * @File_Name:UserServiceImpl.java
 * @Date:05-Jul-2023
 */
package com.kloc.crm.Service.ServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kloc.crm.Entity.Status;
import com.kloc.crm.Entity.User;
import com.kloc.crm.Exception.DataNotFoundException;
import com.kloc.crm.Exception.InvalidInput;
import com.kloc.crm.Exception.NullDataException;
import com.kloc.crm.Repository.StatusRepo;
import com.kloc.crm.Repository.UserRepository;
import com.kloc.crm.Service.UserService;

@Service
/**
 * Implementation of the UserService interface for managing User entities.
 */
public class UserServiceImpl implements UserService {

	// Repository for performing database operations on User entities
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StatusRepo statusRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Creates a new User.
	 *
	 * @param user The User object to be created
	 * @return The created User object
	 */
	@Override
	public User createUser(User user, String reportingTo)// ,String value)
	{	
		if(user.getEmail()==null || user.getEmail().equals("")) {
			throw new InvalidInput("Please enter valid mail id");
		}
		
		System.out.println(user.getRole().getStatusValue());
		if (reportingTo.equals("") || reportingTo == null)
			throw new InvalidInput("Please enter manager Id.");
		else
		{
			if (user == null)
				throw new InvalidInput("please enter user details.");
			else
			{
				List<User> users = userRepository.findAll();
				if (users.isEmpty()) 
				{
					User user1 = new User();
					user1.setUserName("Abhisarika Das");
					user1.setEmail("abhisarika.das459@kloctechnologies.com");
					user1.setPassword("abhisarikadas3469");
					user1.setMobileNo(9999999999L);
					user1.setRole(statusRepository.findByStatusValue("Administrator"));
					user1.setReportingTo(null);
					user1.setStatus(statusRepository.findByStatusValue("Active"));
					
					// user1.setReportingTo(reportingTo);
					userRepository.save(user1);
				}
				User existingUser = userRepository.findById(reportingTo)
						.orElseThrow(() -> new DataNotFoundException("User", "userId", reportingTo));
				user.setReportingTo(existingUser);
				if (user.getPassword() == null || user.getPassword().equals(""))
					throw new InvalidInput("Please enter password");
				else
					user.setPassword(passwordEncoder.encode(user.getPassword()));
				if (user.getRole() == null || user.getRole().getStatusValue()==null ||  user.getRole().getStatusValue().equals(""))
					throw new InvalidInput("please enter role and value.");
				else
					user.setRole(statusRepository.findByStatusValue(user.getRole().getStatusValue()));
				System.out.println("-------------------------------");
				Status newStatus = statusRepository.findByStatusValue("Active");// , value);
				if(newStatus == null)
					throw new DataNotFoundException("Initial status not found.");
				else
					user.setStatus(newStatus);
				
				// Status
				// status=statusRepository.findAll().stream().filter(e->e.getTableName().toLowerCase().equals("User".toLowerCase())&&e.getStatusValue().toLowerCase().equals("active".toLowerCase())&&e.getStatusType().toLowerCase().equals(user.getRole().toLowerCase())).findFirst().get();
				// user.setStatus(status);
				User save = userRepository.save(user);
				return save;
			}
		}
	}

	/**
	 * Retrieves a list of all Users.
	 *
	 * @return A list of all User objects
	 */
	@Override
	public List<User> getAllUsers() 
	{
		return userRepository.findAll();
	}

	/**
	 * Retrieves a User by their ID.
	 *
	 * @param id The ID of the User to retrieve
	 * @return The User object with the specified ID
	 * @throws DataNotFoundException if the User with the specified ID is not found
	 */
	@Override
	public User getUserById(String id)
	{
		if(id == null || id.equals(""))
			throw new InvalidInput("Please insert id");
		else
			return userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("User", "id", id));
	}

	/**
	 * Updates an existing User.
	 *
	 * @param user The updated User object
	 * @param id   The ID of the User to update
	 * @return The updated User object
	 * @throws DataNotFoundException if the User with the specified ID is not found
	 */
	@Override
	public User updateUser(User user, String userId, String statusValue) 
	{
		User existingUser = userRepository.findById(userId)
				.orElseThrow(() -> new DataNotFoundException("User", "id", userId));
		existingUser.setUserName(user.getUserName());
		// existingUser.setEmail(user.getEmail());
		// user.setPassword(passwordEncoder.encode(user.getPassword()));
//        existingUser.setReportingTo(user.getReportingTo());
//		if (user.getRole() != null && !user.getRole().equals(""))
//		{
//			Status role=statusRepository.findByStatusTypeAndStatusValue("Role", user.getRole().getStatusValue());
//			existingUser.setRole(role);
//		}
			
		
		if (user.getMobileNo() > 0)
			existingUser.setMobileNo(user.getMobileNo());
		if (user.getMobileNo() > 0)
			existingUser.setAltMobileNo(user.getAltMobileNo());
		if (user.getReportingTo() != null && !user.getReportingTo().equals("")) 
			existingUser.setReportingTo(user.getReportingTo());
		// existingUser.setStatus(statusRepository.findById(user.getStatus()).get());

//        String newStatusType  = user.getRole();
//        System.out.println(newStatusType);
//		Status newStatus = statusRepository.findByStatusValue(statusValue);
//		if (!newStatus.equals(null))
//		{
//			System.out.println(newStatus);
//			existingUser.setStatus(newStatus);
//			
//		}
		if(statusValue.equals("") ||statusValue == null)
  		{
  			throw new NullDataException("Enter the valid status value");
  		}
//		Status status = statusRepository.findAll().stream().filter(e -> e.getStatusValue().toLowerCase().equals(statusValue.toLowerCase())).findFirst().get();
		Status status = statusRepository.findByStatusTypeAndStatusValue("User_Type", statusValue);
		existingUser.setStatus(status);
		User save = userRepository.save(existingUser);
		return save;
	}

	/**
	 * Retrieves a User by their username.
	 *
	 * @param name The username of the User to retrieve
	 * @return The User object with the specified username
	 * @throws DataNotFoundException if the User with the specified username is not
	 *                               found
	 */
	@Override
	public List<User> getUserByName(String name)
	{
		if (name.equals("") || name == null)
			throw new DataNotFoundException("please enter user name");
		List<User> users = userRepository.findAll().stream().filter(e -> e.getUserName().toLowerCase().equals(name.toLowerCase()))
				.collect(Collectors.toList());
		if (users.isEmpty())
			throw new DataNotFoundException("User", "name", name);
		else
			return userRepository.findAllByUserName(name);
	}

	/**
	 * Retrieves Users based on their roles.
	 *
	 * @param role The role of the Users to retrieve
	 * @return A list of User objects with the specified role
	 * @throws DataNotFoundException if no Users with the specified role are found
	 */
	@Override
	public List<User> getUserByRole(String role) 
	{
//        List<User> users = userRepository.findAll().stream().filter(e -> e.getRole().equals(role)).collect(Collectors.toList());
//        if (users.isEmpty()) {
//            throw new DataNotFoundException("User", "role", role);
//        }

		if (role.equals("") || role ==null)
			throw new InvalidInput("please enter role");
		else
		{
			List<User> manyUsers = userRepository.findAllByRole(statusRepository.findByStatusValue(role.toLowerCase()));
			if (manyUsers.isEmpty())
				throw new InvalidInput("role not found.");
			else
				return manyUsers;
		}
	}

	/**
	 * Retrieves all Users who are reporting to a specific manager.
	 *
	 * @param reportingTo The manager's name to filter Users by
	 * @return A list of User objects reporting to the specified manager
	 */
	@Override
	public List<User> getUserByManagerName(String reportingTo) 
	{
		if(reportingTo.equals("") || reportingTo ==null)
			throw new InvalidInput("please enter manager id");
		List<User> list = userRepository.findAll().stream()
				.filter(e -> e.getReportingTo() != null && e.getReportingTo().getUserId().equals(reportingTo)).toList();
		if (list.isEmpty())
			throw new DataNotFoundException("no user available");
		else
			return list;
	}

	/**
	 * Retrieves User by their email
	 *
	 * 
	 * @return A User object based on his email id
	 */

	@Override
	public User getUserByEmail(String email) 
	{
		if(!email.equals("") && email != null) 
		{
			User user = userRepository.findByEmail(email);
			if (user == null)
				throw new DataNotFoundException("No user available with this email.");
			else
				return user;
		} 
		else
			throw new DataNotFoundException("please enter valid email.");
	}
}
