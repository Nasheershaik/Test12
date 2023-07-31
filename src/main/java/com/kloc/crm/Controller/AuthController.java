package com.kloc.crm.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kloc.crm.Entity.User;
import com.kloc.crm.Service.StatusService;
import com.kloc.crm.Service.UserService;
import com.kloc.crm.models.JwtRequest;
import com.kloc.crm.models.JwtResponse;
import com.kloc.crm.security.JwtHelper;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private StatusService statusService;
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private JwtHelper helper;
	
	private Logger logger =LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private UserService userService;
	
	
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
		 // Perform authentication
	    this.doAuthenticate(request.getEmail(), request.getPassword());

	    // Retrieve updated user details from the database
	    UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

	    // Generate JWT token
	    String token = this.helper.generateToken(userDetails);

	    JwtResponse response = JwtResponse.builder()
	            .jwtToken(token)
	            .username(userDetails.getUsername()).build();
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
//	@PostMapping("/login")
//	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request){
//		
//		 this.doAuthenticate(request.getEmail(), request.getPassword());
//
//
//	        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
//	        String token = this.helper.generateToken(userDetails);
//
//	        JwtResponse response = JwtResponse.builder()
//	                .jwtToken(token)
//	                .username(userDetails.getUsername()).build();
//	        return new ResponseEntity<>(response, HttpStatus.OK);
//	}
	
	 private void doAuthenticate(String email, String password) {
		 
		
			    // Retrieve user from the database based on the email
			    User user = userService.getUserByEmail(email);

			    if (user == null) {
			        throw new BadCredentialsException("Invalid Username or Password!");
			    }

			    // Update the email in the authentication object if it has been changed
			    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), password);

			    try {
			        manager.authenticate(authentication);
			    } catch (BadCredentialsException e) {
			        throw new BadCredentialsException("Invalid Username or Password!");
			    }
			


//	        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
//	        try {
//	            manager.authenticate(authentication);
//
//
//	        } catch (BadCredentialsException e) {
//	            throw new BadCredentialsException(" Invalid Username or Password  !!");
//	        }

	    }

	    @ExceptionHandler(BadCredentialsException.class)
	    public ResponseEntity<String> exceptionHandler() {
	        return new ResponseEntity<String>("Credentials Invalid !!",HttpStatus.UNAUTHORIZED);
	    }
	    
	    @PostMapping("/saveUser/{reportingTo}")									////,@PathVariable("statusV") String statusV)
	    public User createUser(@RequestBody User user, @PathVariable String reportingTo){ 
	    	return userService.createUser(user,reportingTo);
	    }

	
}
