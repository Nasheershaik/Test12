/**
 * @Author :Avinash
 * @File_Name:UserServiceImpl.java
 * @Date:05-Jul-2023
 */
package com.kloc.crm.Service.ServiceImpl;

import java.util.ArrayList;
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
	public User updateUser(User user, String userId) 
	{
		User existingUser = userRepository.findById(userId)
				.orElseThrow(() -> new DataNotFoundException("User", "id", userId));
		existingUser.setUserName(user.getUserName());
		if(!user.getEmail().equals("")) {
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
				.filter(e -> e.getReportingTo() != null && e.getReportingTo().equals(reportingTo)).toList();
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
		if(!email.equals("") && !email.equals(null)) 
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

	@Override
	public User updateStatus(String userId, String statusValue) {
		
		if(!userId.equals("") && userId != null || statusValue.equals("") && statusValue !=null ) {
		User existingUser	=userRepository.findById(userId).orElseThrow(()-> new DataNotFoundException("User","id",userId));
		Status newStatus	=statusRepository.findByStatusValue(statusValue);
		existingUser.setStatus(newStatus);
		return userRepository.save(existingUser);
		}
		else {
			throw new DataNotFoundException("please enter valid id");
		}
		
		
	}

	@Override
	public User getReportingToByUserId(String userId) {
		if(userId!=null && !userId.equals("")) {
		User user= userRepository.findById(userId).orElseThrow(()->new DataNotFoundException("User Id is not available")).getReportingTo();
		return userRepository.findById(user.getReportingTo().getUserId()).get();
	}
		else {
			throw new InvalidInput("User with this id not found");
		}
	}
	
	@Override
	public UserNDto getUserRoleAndStatusValueById(String userId) {
		if(userId!=null && !userId.equals("")) {
	User existingUser	=userRepository.findById(userId).orElseThrow(()-> new DataNotFoundException("User","id",userId));
		String uname =existingUser.getUserName();
		String uemail=existingUser.getEmail();
		long umobileNo =existingUser.getMobileNo();
		long ualtMobileNo = existingUser.getAltMobileNo();
		String role  = existingUser.getRole().getStatusValue();
		String value =existingUser.getStatus().getStatusValue();
		String reportingToUserId = "";
		String reportingToUserName ="";
		if (!userId.equals("user_0001"))
		{
			reportingToUserId = existingUser.getReportingTo().getUserId();
			reportingToUserName =existingUser.getReportingTo().getUserName();	
		}
		return new UserNDto(uname,uemail,umobileNo,ualtMobileNo,role,value,reportingToUserId,reportingToUserName);
		}
		else {
			throw new InvalidInput("User with this id not found");
		}
	}

	
	@Override
	public UserDto getUserRoleAndStatusvalueAndreportingToByEmailId(String email) {
		if(email==null && email.equals("")) {
			throw new InvalidInput("Please enter valid email");
		}
	User existingUser	=userRepository.findByEmail(email);
	if(existingUser!=null) {
	String role = existingUser.getRole().getStatusValue();
	String value = existingUser.getStatus().getStatusValue();
	String reportingToUserId = existingUser.getReportingTo().getUserId();
	 User managerUser     =userRepository.findById(reportingToUserId).get();
	 String managerName =managerUser.getUserName();
	String mEmail =managerUser.getEmail();
	
	UserDto demoUser =new UserDto(role,value,reportingToUserId,managerName,mEmail);
	return demoUser;
	//return "User's role is "+role+","+"Value is"+" "+value+" "+"And user is reporting to this userId"+" "+reportingToUserId;
	}	
	else {
		throw new InvalidInput("There is no user found ");
	}
		
		
	}

	@Override
	public String updateUserByAdmin(String userId,String srole,String reportingUserId) {
		if((!userId.equals("") && !srole.equals("")) && !reportingUserId.equals("")) {
	User existingUser	=userRepository.findById(userId).orElseThrow(()-> new DataNotFoundException("User not found With this email id"));
	Status nstatus=statusRepository.findByStatusValue(srole);
	System.out.println(nstatus.getStatusValue());
	if(nstatus!=null) {
	
	
	User reportingUser =userRepository.findById(reportingUserId).orElseThrow(()-> new DataNotFoundException("There is no User with this user id whom other can report"));
		existingUser.setReportingTo(reportingUser);
		System.out.println(reportingUser.getUserId());
		existingUser.setRole(nstatus);
		userRepository.save(existingUser);
		
		return "User Details Updated Successfully";
	}
	else {
			throw new InvalidInput("Role Does Not Matches or Found");
	}
		}
	
	else {
		
		throw new InvalidInput("Check the fields properly");
	
	}
 }
	@Override
	public List<UserNDto> getAllUserNDto(){
		List<User> users =userRepository.findAll().stream().filter(e->!e.getReportingTo().equals(null)).toList();
		List<UserNDto> usersNdtos = new ArrayList<>();
		for(User user:users) {
		String uname =	user.getUserName();
		String email =user.getEmail();
		long phoneNo =user.getMobileNo();
		long altphoneNo =user.getAltMobileNo();
		String role =user.getRole().getStatusValue();
		String sValue =user.getStatus().getStatusValue();
		String reportingToId;
		
			 reportingToId =user.getReportingTo().getUserId();
	
		
		String reportingUserName =user.getReportingTo().getUserName();
		
		usersNdtos.add(new UserNDto(uname,email,phoneNo,altphoneNo,role,sValue,reportingToId,reportingUserName));
		
		}
		return usersNdtos;
		
	}

	@Override
	public List<UserNDto> getUsersByTheirRoles(String role) {
		if (role.equals("") || role ==null) {
			throw new InvalidInput("please enter role");
		}
		else {
	List<User> mUsers	=userRepository.findAllByRole(statusRepository.findByStatusValue(role));
	if (mUsers.isEmpty())
		throw new InvalidInput("No users Found with this Role");
	List<UserNDto> nUsers = new ArrayList<>();
	for(User user:mUsers) {
		String name = user.getUserName();
		String email =user.getEmail();
		long mobileNo = user.getMobileNo();
		long altMobileNo =user.getAltMobileNo();
		String nrole = user.getRole().getStatusValue();
		String nvalue = user.getStatus().getStatusValue();
		String reportingUserId;
		if(user.getReportingTo().getUserId()!=null) {
			 reportingUserId =user.getReportingTo().getUserId();
		}else {
			reportingUserId ="";
		}
		String reportingUserName =user.getReportingTo().getUserName();
		
		nUsers.add(new UserNDto(name,email,mobileNo,altMobileNo,nrole,nvalue,reportingUserId,reportingUserName));
		
	}
		
		return nUsers;
	}
	}

	@Override
	public List<UserNDto> getUsersBasedOnTheirName(String name) {
		System.out.println("-------------");
		if (name.equals("") || name.equals(null)) {
			throw new DataNotFoundException("please enter user name");
		}
		List<User> users =userRepository.findAll().stream().filter(e -> e.getUserName().equalsIgnoreCase(name)).toList();
		List<UserNDto> nUsersNDto =new ArrayList<UserNDto>();
		if (users.isEmpty()) {
			throw new DataNotFoundException("User", "name", name);
		}
		System.out.println("----users is not empty----");
		
		for(User user : users) {
			String uname = user.getUserName();
			String email =user.getEmail();
			long mobileNo = user.getMobileNo();
			long altMobileNo =user.getAltMobileNo();
			String nrole = user.getRole().getStatusValue();
			String nvalue = user.getStatus().getStatusValue();
			String reportingUserId;
			if(user.getReportingTo().getUserId()!=null) {
				 reportingUserId =user.getReportingTo().getUserId();
			}else {
				reportingUserId ="null";
			}
			String reportingUserName =user.getReportingTo().getUserName();
			
			nUsersNDto.add(new UserNDto(uname,email,mobileNo,altMobileNo,nrole,nvalue,reportingUserId,reportingUserName));
			
		}
		System.out.println("--------- aaaaaabbbbbbbcccccc");
			
		return nUsersNDto;
	
	
	}

	@Override
	public UserNDto getUserBasedOnTheirid(String userId) {
	User user =	userRepository.findById(userId).orElseThrow(()-> new DataNotFoundException("User Not Found With this id"));
		
	String uname =user.getUserName();
	String uemail =user.getEmail();
	long mobileNo = user.getMobileNo();
	long altMobileNo = user.getAltMobileNo();
	String urole = user.getRole().getStatusValue();
	String uvalue =user.getStatus().getStatusValue();
	String reportingUserId;
	if(user.getReportingTo().getUserId()!=null) {
		 reportingUserId =user.getReportingTo().getUserId();
	}else {
		reportingUserId ="null";
	}
	String reportingUserName =user.getReportingTo().getUserName();
	
		
		return new UserNDto(uname, uemail, mobileNo, altMobileNo, urole, uvalue, reportingUserId, reportingUserName);
	}
}
