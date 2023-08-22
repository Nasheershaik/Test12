/**
 * @Author :Avinash
 * @File_Name:UserServiceImpl.java
 * @Date:05-Jul-2023
 */
package com.kloc.crm.Service.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kloc.crm.Entity.Status;
import com.kloc.crm.Entity.User;
import com.kloc.crm.Exception.DataNotFoundException;
import com.kloc.crm.Exception.InvalidInput;
import com.kloc.crm.Repository.StatusRepo;
import com.kloc.crm.Repository.UserRepository;
import com.kloc.crm.Service.UserService;
import com.kloc.crm.dto.UserDto;
import com.kloc.crm.dto.UserNDto;

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

	@Autowired
	private UserDto userdto;

	@Autowired
	private UserNDto userNdto;

	// Create a logger using Log4j 2.x
	private static final Logger logger = LogManager.getLogger(UserService.class);

	/**
	 * Creates a new User.
	 *
	 * @param user The User object to be created
	 * @return The created User object
	 */
	@Override
	public UserNDto createUser(User user, String reportingTo)// ,String value)
	{
		try {
			logger.info("Creating user");
			if (user.getEmail() == null || user.getEmail().equals("")) {
				throw new InvalidInput("Please enter valid mail id");
			}

			// System.out.println(user.getRole().getStatusValue());
			if (reportingTo.equals("") || reportingTo == null)
				throw new InvalidInput("Please enter manager Id.");
			else {
				if (user == null)
					throw new InvalidInput("please enter user details.");
				else {
					// List<User> users = userRepository.findAll();
					// if (users.isEmpty())
					// {
					// User user1 = new User();
					// user1.setUserName("Abhisarika Das");
					// user1.setEmail("abhisarika.das459@kloctechnologies.com");
					// user1.setPassword(passwordEncoder.encode( "abhisarikadas3469"));
					// user1.setMobileNo(9999999999L);
					// user1.setAltMobileNo(9009056789L);
					// user1.setRole(statusRepository.findByStatusValue("Administrator"));
					// user1.setReportingTo(null);
					// user1.setStatus(statusRepository.findByStatusValue("Active"));
					//
					// // user1.setReportingTo(reportingTo);
					// User usernew=userRepository.save(user1);
					// user1.setReportingTo(usernew);
					// userRepository.save(user1);
					// }
					User existingUser = userRepository.findById(reportingTo)
							.orElseThrow(() -> new DataNotFoundException("User", "userId", reportingTo));
					user.setReportingTo(existingUser);
					if (user.getPassword() == null || user.getPassword().equals(""))
						throw new InvalidInput("Please enter password");
					else
						user.setPassword(passwordEncoder.encode(user.getPassword()));
					if (user.getRole() == null || user.getRole().getStatusValue() == null
							|| user.getRole().getStatusValue().equals(""))
						throw new InvalidInput("please enter role and value.");
					else
						user.setRole(statusRepository.findByStatusValue(user.getRole().getStatusValue()));
					System.out.println("-------------------------------");
					Status newStatus = statusRepository.findByStatusValue("Active");// , value);
					if (newStatus == null)
						throw new DataNotFoundException("Initial status not found.");
					else
						user.setStatus(newStatus);

					// Status
					// status=statusRepository.findAll().stream().filter(e->e.getTableName().toLowerCase().equals("User".toLowerCase())&&e.getStatusValue().toLowerCase().equals("active".toLowerCase())&&e.getStatusType().toLowerCase().equals(user.getRole().toLowerCase())).findFirst().get();
					// user.setStatus(status);
					User savedUser = userRepository.save(user);
					String userId = savedUser.getUserId();
					String uname = savedUser.getUserName();
					String email = savedUser.getEmail();
					long mobileNo = savedUser.getMobileNo();
					long altMobileNo = savedUser.getAltMobileNo();
					String role = savedUser.getRole().getStatusValue();
					String svalue = savedUser.getStatus().getStatusValue();
					String reportingUserId = savedUser.getReportingTo().getUserId();
					String reportingUserName = savedUser.getReportingTo().getUserName();
					logger.info("User created successfully");
					return new UserNDto(userId, uname, email, mobileNo, altMobileNo, role, svalue, reportingUserId,
							reportingUserName);
				}
			}
		} catch (Exception e) {
			// Log the exception if any error occurs
			logger.error("Error creating user: {}", e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * Retrieves a list of all Users.
	 *
	 * @return A list of all User objects
	 */
	@Override
	public List<User> getAllUsers() {
		try {
			logger.info("Retrieving a list of all users");

			List<User> users = userRepository.findAll();

			// Log a success message
			logger.info("Retrieved {} users", users.size());

			return users;
		} catch (Exception e) {
			// Log the exception if any error occurs
			logger.error("Error retrieving all users: {}", e.getMessage(), e);
			throw e;
		}

	}

	/**
	 * Retrieves a User by their ID.
	 *
	 * @param id The ID of the User to retrieve
	 * @return The User object with the specified ID
	 * @throws DataNotFoundException if the User with the specified ID is not found
	 */
	@Override
	public User getUserById(String id) {
		try {
			logger.info("Retrieving user by ID: {}", id);

			if (id == null || id.equals("")) {
				logger.error("Invalid input: Please insert id");
				throw new InvalidInput("Please insert id");
			}

			User user = userRepository.findById(id).orElseThrow(() -> {
				logger.error("User not found with id: {}", id);
				return new DataNotFoundException("User", "id", id);
			});

			// Log a success message
			logger.info("Retrieved user by ID: {}", id);

			return user;
		} catch (Exception e) {
			// Log the exception if any error occurs
			logger.error("Error retrieving user by ID: {}", e.getMessage(), e);
			throw e;
		}
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
	public UserNDto updateUser(User user, String userId) {
		try {
			logger.info("Updating user with ID: {}", userId);

			User existingUser = userRepository.findById(userId)
					.orElseThrow(() -> new DataNotFoundException("User", "id", userId));
			existingUser.setUserName(user.getUserName());
			if (!user.getEmail().equals("")) {
				existingUser.setEmail(user.getEmail());
			}
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
//		if (user.getReportingTo() != null && !user.getReportingTo().equals("")) 
//			existingUser.setReportingTo(user.getReportingTo());
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
//		if(statusValue.equals("") ||statusValue == null)
//  		{
//  			throw new NullDataException("Enter the valid status value");
//  		}
//		Status status = statusRepository.findAll().stream().filter(e -> e.getStatusValue().toLowerCase().equals(statusValue.toLowerCase())).findFirst().get();
//		Status status = statusRepository.findByStatusTypeAndStatusValue("User_Type", statusValue);
//		existingUser.setStatus(status);
			userRepository.save(existingUser);
			String userid = existingUser.getUserId();
			String uname = existingUser.getUserName();
			String email = existingUser.getEmail();
			long mobileNo = existingUser.getMobileNo();
			long altMobileNo = existingUser.getAltMobileNo();
			String role = existingUser.getRole().getStatusValue();
			String svalue = existingUser.getStatus().getStatusValue();
			String reportingUserId = existingUser.getReportingTo().getUserId();
			String reportingUserName = existingUser.getReportingTo().getUserName();
			// Log a success message
			logger.info("User updated with ID: {}", userId);
			return new UserNDto(userid, uname, email, mobileNo, altMobileNo, role, svalue, reportingUserId,
					reportingUserName);
		} catch (Exception e) {
			// Log the exception if any error occurs
			logger.error("Error updating user with ID {}: {}", userId, e.getMessage(), e);
			throw e;
		}

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
	public List<User> getUserByName(String name) {
		try {
			logger.info("Retrieving user(s) by name: {}", name);

			if (name.equals("") || name == null) {
				logger.error("Invalid input: Please enter a user name");
				throw new DataNotFoundException("Please enter a user name");
			}

			List<User> users = userRepository.findAll().stream().filter(e -> e.getUserName().toLowerCase().equals(name.toLowerCase()))
					.collect(Collectors.toList());

			if (users.isEmpty()) {
				logger.info("No user found with name: {}", name);
				throw new DataNotFoundException("User", "name", name);
			} else {
				// Log a success message
				logger.info("Retrieved {} user(s) by name: {}", users.size(), name);
				return users;
			}
		} catch (Exception e) {
			// Log the exception if any error occurs
			logger.error("Error retrieving user(s) by name {}: {}", name, e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * Retrieves Users based on their roles.
	 *
	 * @param role The role of the Users to retrieve
	 * @return A list of User objects with the specified role
	 * @throws DataNotFoundException if no Users with the specified role are found
	 */
	@Override
	public List<User> getUserByRole(String role) {
//        List<User> users = userRepository.findAll().stream().filter(e -> e.getRole().equals(role)).collect(Collectors.toList());
//        if (users.isEmpty()) {
//            throw new DataNotFoundException("User", "role", role);
//        }

		try {
			logger.info("Retrieving user(s) by role: {}", role);

			if (role.equals("") || role ==null) {
				logger.error("Invalid input: Please enter a role");
				throw new InvalidInput("please enter role");
			}

			List<User> manyUsers = userRepository.findAllByRole(statusRepository.findByStatusValue(role.toLowerCase()));
			if (manyUsers.isEmpty()) {
				logger.info("No user found with role: {}", role);
				throw new InvalidInput("role not found.");
			} else {
				// Log a success message
				logger.info("Retrieved {} user(s) by role: {}", manyUsers.size(), role);
				return manyUsers;
			}
		} catch (Exception e) {
			// Log the exception if any error occurs
			logger.error("Error retrieving user(s) by role {}: {}", role, e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * Retrieves all Users who are reporting to a specific manager.
	 *
	 * @param reportingTo The manager's name to filter Users by
	 * @return A list of User objects reporting to the specified manager
	 */
	@Override
	public List<User> getUserByManagerName(String reportingTo) {
		try {
			logger.info("Retrieving user(s) by manager ID: {}", reportingTo);

			if(reportingTo.equals("") || reportingTo ==null) {
				logger.error("Invalid input: Please enter a manager ID");
				throw new InvalidInput("please enter manager id");
			}

			List<User> list = userRepository.findAll().stream()
					.filter(e -> e.getReportingTo() != null && e.getReportingTo().getUserId().equals(reportingTo)).toList();

			if (list.isEmpty()) {
				logger.info("No user found with manager ID: {}", reportingTo);
				throw new DataNotFoundException("No user available");
			} else {
				// Log a success message
				logger.info("Retrieved {} user(s) by manager ID: {}", list.size(), reportingTo);
				return list;
			}
		} catch (Exception e) {
			// Log the exception if any error occurs
			logger.error("Error retrieving user(s) by manager ID {}: {}", reportingTo, e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * Retrieves User by their email
	 *
	 * 
	 * @return A User object based on his email id
	 */

	@Override
	public User getUserByEmail(String email) {

		try {
			logger.info("Retrieving user by email: {}", email);

			if (email == null || email.equals("")) {
				logger.error("Invalid input: Please enter an email");
				throw new DataNotFoundException("Please enter a valid email");
			}
			User user = userRepository.findByEmail(email);
			if (user == null) {
				logger.info("No user found with email: {}", email);
				throw new DataNotFoundException("No user available with this email");
			} else {
				// Log a success message
				logger.info("Retrieved user by email: {}", email);
				return user;
			}
		} catch (Exception e) {
			// Log the exception if any error occurs
			logger.error("Error retrieving user by email {}: {}", email, e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public UserNDto updateStatus(String userId, String statusValue) {

		try {
			logger.info("Updating status for user ID: {}, new status: {}", userId, statusValue);

			if (userId == null || userId.equals("") || statusValue == null || statusValue.equals("")) {
				logger.error("Invalid input: Please provide valid user ID and status value");
				throw new DataNotFoundException("Please provide valid user ID and status value");
			}

			User existingUser = userRepository.findById(userId)
					.orElseThrow(() -> new DataNotFoundException("User", "id", userId));
			Status newStatus = statusRepository.findByStatusValue(statusValue);

			if (newStatus == null) {
				logger.error("Invalid status value: {}", statusValue);
				throw new DataNotFoundException("Invalid status value");
			}

			existingUser.setStatus(newStatus);
			userRepository.save(existingUser);

			String userid = existingUser.getUserId();
			String uname = existingUser.getUserName();
			String email = existingUser.getEmail();
			long mobileNo = existingUser.getMobileNo();
			long altMobileNo = existingUser.getAltMobileNo();
			String role = existingUser.getRole().getStatusValue();
			String svalue = existingUser.getStatus().getStatusValue();
			String reportingUserId = existingUser.getReportingTo().getUserId();
			String reportingUserName = existingUser.getReportingTo().getUserName();
			logger.info("Updated status for user ID: {} to new status: {}", userId, statusValue);
			return new UserNDto(userid, uname, email, mobileNo, altMobileNo, role, svalue, reportingUserId,
					reportingUserName);
		} catch (Exception e) {
			// Log the exception if any error occurs
			logger.error("Error updating status for user ID {}: {}", userId, e.getMessage(), e);
			throw e;
		}
	}

	//check
	@Override
	public User getReportingToByUserId(String userId) {
		try {
			logger.info("Retrieving reporting user for user ID: {}", userId);

			if (userId != null && !userId.equals("")) {
				User user = userRepository.findById(userId)
						.orElseThrow(() -> new DataNotFoundException("User Id is not available"));

				User reportingUser = userRepository.findById(user.getReportingTo().getUserId()).orElse(null);

				if (reportingUser != null) {
					// Log a success message
					logger.info("Retrieved reporting user for user ID: {}", userId);
					return reportingUser;
				} else {
					logger.error("Reporting user not found for user ID: {}", userId);
					throw new DataNotFoundException("Reporting user not found for user ID: " + userId);
				}
			} else {
				logger.error("Invalid input: User ID is null or empty");
				throw new InvalidInput("User with this id not found");
			}
		} catch (Exception e) {
			// Log the exception if any error occurs
			logger.error("Error retrieving reporting user for user ID {}: {}", userId, e.getMessage(), e);
			throw e;
		}
//		if(userId!=null && !userId.equals("")) {
//		User user= userRepository.findById(userId).orElseThrow(()->new DataNotFoundException("User Id is not available")).getReportingTo();
//		return userRepository.findById(user.getReportingTo().getUserId()).get();
//	}
//		else {
//			throw new InvalidInput("User with this id not found");
//		}
	}

	@Override
	public UserNDto getUserRoleAndStatusValueById(String userId) {
		try {
			logger.info("Retrieving user role and status value for user ID: {}", userId);

			if (userId != null && !userId.equals("")) {
				User existingUser = userRepository.findById(userId)
						.orElseThrow(() -> new DataNotFoundException("User", "id", userId));
				String userid = existingUser.getUserId();
				String uname = existingUser.getUserName();
				String uemail = existingUser.getEmail();
				long umobileNo = existingUser.getMobileNo();
				long ualtMobileNo = existingUser.getAltMobileNo();
				String role = existingUser.getRole().getStatusValue();
				String value = existingUser.getStatus().getStatusValue();
				String reportingToUserId;
				String reportingToUserName;
				if (userId.equals("user_0001") && existingUser.getReportingTo().equals(null)) {
					reportingToUserId = "";
					reportingToUserName = "";
				} else {
					reportingToUserId = existingUser.getReportingTo().getUserId();
					reportingToUserName = existingUser.getReportingTo().getUserName();
				}
				// Log a success message
				logger.info("Retrieved user role and status value for user ID: {}", userId);

				return new UserNDto(userid, uname, uemail, umobileNo, ualtMobileNo, role, value, reportingToUserId,
						reportingToUserName);
			} else {
				logger.error("Invalid input: User ID is null or empty");
				throw new InvalidInput("User with this id not found");
			}
		} catch (Exception e) {
			// Log the exception if any error occurs
			logger.error("Error retrieving user role and status value for user ID {}: {}", userId, e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public UserDto getUserRoleAndStatusvalueAndreportingToByEmailId(String email) {
		try {
			logger.info("Retrieving user role, status value, and reporting user for email: {}", email);

			if (email == null || email.equals("")) {
				throw new InvalidInput("Please enter valid email");
			}
			User existingUser = userRepository.findByEmail(email);
			if (existingUser != null) {
				String userId = existingUser.getUserId();
				String uname = existingUser.getUserName();
				String urole = existingUser.getRole().getStatusValue();
				String svalue = existingUser.getStatus().getStatusValue();
				String managerId = existingUser.getReportingTo().getUserId();
				String managerName = existingUser.getReportingTo().getUserName();
				String mEmail = existingUser.getReportingTo().getEmail();
				UserDto demoUser = new UserDto(userId, uname, urole, svalue, managerId, managerName, mEmail);
				// Log a success message
				logger.info("Retrieved user role, status value, and reporting user for email: {}", email);
				return demoUser;
			} else {
				logger.error("No user found for email: {}", email);
				throw new InvalidInput("There is no user found");
			}
		} catch (Exception e) {
			// Log the exception if any error occurs
			logger.error("Error retrieving user role, status value, and reporting user for email {}: {}", email,
					e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public String updateUserByAdmin(String userId, String srole, String reportingUserId) {
		try {
			logger.info("Updating user by admin with userID: {}, new role: {}, reportingUserID: {}", userId, srole,
					reportingUserId);

			if (userId == null || userId.equals("") || srole == null || srole.equals("") || reportingUserId == null
					|| reportingUserId.equals("")) {
				logger.error("Invalid input: Please provide valid userID, role, and reportingUserID");
				throw new InvalidInput("Please provide valid userID, role, and reportingUserID");
			}

			User existingUser	=userRepository.findById(userId).orElseThrow(()-> new DataNotFoundException("User not found With this email id"));
			Status nstatus=statusRepository.findByStatusValue(srole);

			if(nstatus!=null) {
				User reportingUser =userRepository.findById(reportingUserId).orElseThrow(()-> new DataNotFoundException("There is no User with this user id whom other can report"));
				existingUser.setReportingTo(reportingUser);
//				System.out.println(reportingUser.getUserId());
				existingUser.setRole(nstatus);
				userRepository.save(existingUser);
				// Log a success message
				logger.info("Updated user by admin with userID: {}, new role: {}, reportingUserID: {}", userId, srole,
						reportingUserId);

				return "User Details Updated Successfully";
			} else {
				logger.error("Role Does Not Match or Found");
				throw new InvalidInput("Role Does Not Match or Found");
			}
		} catch (Exception e) {
			// Log the exception if any error occurs
			logger.error("Error updating user by admin with userID: {}, new role: {}, reportingUserID: {}: {}", userId,
					srole, reportingUserId, e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public List<UserNDto> getAllUserNDto() {
		try {
			logger.info("Getting all UserNDtos");
			List<User> users = userRepository.findAll();
			List<UserNDto> usersNdtos = new ArrayList<>();
			for (User user : users) {
				String userId = user.getUserId();
				String uname = user.getUserName();
				String email = user.getEmail();
				long phoneNo = user.getMobileNo();
				long altphoneNo = user.getAltMobileNo();
				String role = user.getRole().getStatusValue();
				String sValue = user.getStatus().getStatusValue();
				String reportingToId;
				String reportingUserName;
				if (userId.equals("user_0001") && user.getReportingTo().equals(null)) {
					reportingToId = "";
					reportingUserName = "";
				}
				reportingToId = user.getReportingTo().getUserId();
				reportingUserName = user.getReportingTo().getUserName();
				usersNdtos.add(new UserNDto(userId, uname, email, phoneNo, altphoneNo, role, sValue, reportingToId,
						reportingUserName));
			}
			// Log a success message
			logger.info("Retrieved all UserNDtos");
			return usersNdtos;
		} catch (Exception e) {
			// Log the exception if any error occurs
			logger.error("Error getting all UserNDtos: {}", e.getMessage(), e);
			throw e;
		}

	}

	@Override
	public List<UserNDto> getUsersByTheirRoles(String role) {
		try {
			logger.info("Getting users by role: {}", role);
			if (role.equals("") || role == null) {
				throw new InvalidInput("please enter role");
			} else {
				List<User> mUsers = userRepository.findAllByRole(statusRepository.findByStatusValue(role));
				if (mUsers.isEmpty())
					throw new InvalidInput("No users Found with this Role");
				List<UserNDto> nUsers = new ArrayList<>();
				for (User user : mUsers) {
					String userId = user.getUserId();
					String name = user.getUserName();
					String email = user.getEmail();
					long mobileNo = user.getMobileNo();
					long altMobileNo = user.getAltMobileNo();
					String nrole = user.getRole().getStatusValue();
					String nvalue = user.getStatus().getStatusValue();
					String reportingUserId;
					if (user.getReportingTo().getUserId() != null) {
						reportingUserId = user.getReportingTo().getUserId();
					} else {
						reportingUserId = "";
					}
					String reportingUserName = user.getReportingTo().getUserName();
					nUsers.add(new UserNDto(userId, name, email, mobileNo, altMobileNo, nrole, nvalue, reportingUserId,
							reportingUserName));
				}
				// Log a success message
				logger.info("Retrieved users by role: {}", role);
				return nUsers;
			}
		} catch (Exception e) {
			// Log the exception if any error occurs
			logger.error("Error getting users by role {}: {}", role, e.getMessage(), e);
			throw e;
		}

	}

	@Override
	public List<UserNDto> getUsersBasedOnTheirName(String name) {
		try {
			logger.info("Getting users by name: {}", name);

			if (name.equals("") || name.equals(null)) {
				throw new DataNotFoundException("please enter user name");
			}
			List<User> users = userRepository.findAll().stream().filter(e -> e.getUserName().equalsIgnoreCase(name))
					.toList();
			List<UserNDto> nUsersNDto = new ArrayList<UserNDto>();
			if (users.isEmpty()) {
				throw new DataNotFoundException("User", "name", name);
			}
			for (User user : users) {
				String userId = user.getUserId();
				String uname = user.getUserName();
				String email = user.getEmail();
				long mobileNo = user.getMobileNo();
				long altMobileNo = user.getAltMobileNo();
				String nrole = user.getRole().getStatusValue();
				String nvalue = user.getStatus().getStatusValue();
				String reportingUserId;
				if (!user.getReportingTo().equals(null)) {
					reportingUserId = user.getReportingTo().getUserId();
				} else {
					reportingUserId = "null";
				}
				String reportingUserName = user.getReportingTo().getUserName();

				nUsersNDto.add(new UserNDto(userId, uname, email, mobileNo, altMobileNo, nrole, nvalue, reportingUserId,
						reportingUserName));
			}
			// Log a success message
			logger.info("Retrieved users by name: {}", name);
			return nUsersNDto;

		} catch (Exception e) {
			// Log the exception if any error occurs
			logger.error("Error getting users by name {}: {}", name, e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public UserNDto getUserBasedOnTheirid(String userId) {
		try {
			logger.info("Getting user by ID: {}", userId);
			if (userId == null || userId.isEmpty()) {
				throw new InvalidInput("Please enter a valid user ID");
			}
			User user = userRepository.findById(userId)
					.orElseThrow(() -> new DataNotFoundException("User Not Found With this id"));
			String userid = user.getUserId();
			String uname = user.getUserName();
			String uemail = user.getEmail();
			long mobileNo = user.getMobileNo();
			long altMobileNo = user.getAltMobileNo();
			String urole = user.getRole().getStatusValue();
			String uvalue = user.getStatus().getStatusValue();
			String reportingToId = "";
			String reportingUserName = "";
			if (!user.getReportingTo().equals(null)) {
				reportingToId = user.getReportingTo().getUserId();
				reportingUserName = user.getReportingTo().getUserName();
			}
			// Log a success message
			logger.info("Retrieved user by ID: {}", userId);
			return new UserNDto(userid, uname, uemail, mobileNo, altMobileNo, urole, uvalue, reportingToId,
					reportingUserName);
		} catch (Exception e) {
			// Log the exception if any error occurs
			logger.error("Error getting user by ID {}: {}", userId, e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public List<UserNDto> getAllUsersWhoAreReportingToThisId(String reportingToId) {
		try 
		{
			logger.info("Getting all users reporting to user ID: {}", reportingToId);
			if (reportingToId.equals("") || reportingToId == null) {
				throw new InvalidInput("Please enter correctId ");
			}
			List<User> users = userRepository.findAll().stream().filter(
					e -> (!e.getReportingTo().equals(null) && e.getReportingTo().getUserId().equals(reportingToId)))
					.collect(Collectors.toList());
			if (users.isEmpty())
			{
				throw new DataNotFoundException("No user available with this reporting to id.");
			}
			List<UserNDto> nusers = new ArrayList<>();
			for (User user : users) {
				String userId = user.getUserId();
				String uname = user.getUserName();
				String uemail = user.getEmail();
				long mobileNo = user.getMobileNo();
				long altMobileNo = user.getAltMobileNo();
				String urole = user.getRole().getStatusValue();
				String uvalue = user.getStatus().getStatusValue();
				String reportingUserId = user.getReportingTo().getUserId();
				String reportingUserName = user.getReportingTo().getUserName();
				nusers.add(new UserNDto(userId, uname, uemail, mobileNo, altMobileNo, urole, uvalue, reportingUserId,
						reportingUserName));
			}
			// Log a success message
			logger.info("Retrieved all users reporting to user ID: {}", reportingToId);

			return nusers;
		} catch (Exception e) {
			// Log the exception if any error occurs
			logger.error("Error getting all users reporting to user ID {}: {}", reportingToId, e.getMessage(), e);
			throw e;
		}
	}
}
