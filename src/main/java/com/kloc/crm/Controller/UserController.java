/**
 * @Author :Avinash
 * @File_Name:UserController.java
 * @Date:05-Jul-2023
 */
package com.kloc.crm.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kloc.crm.Entity.SalesPerson;
import com.kloc.crm.Entity.User;
import com.kloc.crm.Exception.InvalidInput;
import com.kloc.crm.Repository.UserRepository;
import com.kloc.crm.Service.SalesPersonService;
import com.kloc.crm.Service.StatusService;
import com.kloc.crm.Service.UserService;


@RestController
@CrossOrigin("*")
@RequestMapping("/api")
/**
 * Controller class for managing User entities.
 */
public class UserController {
    
    // Service for handling User-related operations
    @Autowired
    private UserService userService;
    
    // Service for handling SalesPerson-related operations
    @Autowired
    private SalesPersonService salesPersonService;
    
    @Autowired
    private StatusService statusService;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @GetMapping("/getCurrentUser")
    public void currentUser(Principal p) {
    	if(p!=null) {
    		String email=p.getName();
    	User currentuser	=userRepository.findByEmail(email);
    		System.out.println(currentuser);
    	}
    }
    
    /**
     * Endpoint for creating a new User.
     *
     * @param user  The User object to be saved
     * @return      The created User object with HTTP status OK
     */
//    @PostMapping("/saveUser")
//    public ResponseEntity<User> saveUser(@RequestBody User user) {
//        return new ResponseEntity<User>(userService.createUser(user), HttpStatus.OK);
//    }
    
    /**
     * Endpoint for retrieving a list of all Users.
     *
     * @return  A list of all User objects
     */
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getListOfAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
    
    /**
     * Endpoint for retrieving a User by their ID.
     *
     * @param id  The ID of the User to retrieve
     * @return    The User object with the specified ID with HTTP status OK
     */
    @GetMapping("/getUserById/{id}")
    public ResponseEntity<User> getUserBasedOnId(@PathVariable("id") String id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }
    
    /**
     * Endpoint for retrieving a User by their username.
     *
     * @param name  The username of the User to retrieve
     * @return      The User object with the specified username with HTTP status OK
     */
    @GetMapping("/getUserByName/{name}")
    public ResponseEntity<List<User>> getUserByUserName(@PathVariable("name") String name) {
        return new ResponseEntity<>(userService.getUserByName(name), HttpStatus.OK);
    }
    
    /**
     * Endpoint for retrieving Users based on their roles.
     *
     * @param role  The role of the Users to retrieve
     * @return      A list of User objects with the specified role with HTTP status OK
     */
    @GetMapping("/getUserByRole/{role}")
    public ResponseEntity<List<User>> getUserBasedOnTheirRoles(@PathVariable("role") String role) {
        return new ResponseEntity<>(userService.getUserByRole(role), HttpStatus.OK);
    }
    
    /**
     * Endpoint for retrieving all Users who are reporting to a specific manager.
     *
     * @param reportingTo  The manager's name to filter Users by
     * @return             A list of User objects reporting to the specified manager with HTTP status OK
     */
    @GetMapping("/getUserByMgrName/{reportingTo}")
    public ResponseEntity<List<User>> getAllUsersWhoAreReportingTo(@PathVariable("reportingTo") String reportingTo) {
        return new ResponseEntity<>(userService.getUserByManagerName(reportingTo), HttpStatus.OK);
    }
    
    @PutMapping("/updateUser/{userId}/{statusValue}")
    public ResponseEntity<User> updateUserByTheirId(@RequestBody User user,@PathVariable("userId") String userId,@PathVariable("statusValue") String statusValue){
    	return new ResponseEntity<User>(userService.updateUser(user, userId,statusValue),HttpStatus.OK);
    }
    
    @PostMapping("/updatePassword")
    public User updatePassword(Principal p, @RequestParam("oldPassword") String oldPassword,@RequestParam("newPassword") String newPassword){
    	if(newPassword.isEmpty()) {throw new InvalidInput("password can not  be empty");}
    	String email =p.getName();
    User currentlyUser	=userRepository.findByEmail(email);
   
 boolean f =  passwordEncoder.matches(oldPassword, currentlyUser.getPassword());
    if(f) {
    	System.out.println("Matches with old password");
    	currentlyUser.setPassword(passwordEncoder.encode(newPassword) );
    	
    	return userRepository.save(currentlyUser);

    }
    else {
    	throw  new InvalidInput("old password does not match");
    }
    
    
    }
    
}
