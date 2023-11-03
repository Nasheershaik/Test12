package com.kloc.crm.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kloc.crm.Entity.User;
import com.kloc.crm.Exception.InvalidInput;

import com.kloc.crm.Repository.UserRepository;
import com.kloc.crm.Service.UserService;
import com.kloc.crm.dto.UserNDto;
import com.kloc.crm.models.JwtRequest;
import com.kloc.crm.models.JwtResponse;
import com.kloc.crm.security.JwtHelper;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sender;
	@Autowired
	UserRepository userRepository;

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private JwtHelper helper;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
		// Perform authentication

		this.doAuthenticate(request.getEmail(), request.getPassword());
		// System.out.println(request);
		// Retrieve updated user details from the database
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
		User user = userRepository.findByEmail(request.getEmail());
		// Generate JWT token
		String token = this.helper.generateToken(userDetails);

		JwtResponse response = JwtResponse.builder().jwtToken(token).username(userDetails.getUsername())
				.userId(user.getUserId()).build();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// @PostMapping("/login")
	// public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request){
	//
	// this.doAuthenticate(request.getEmail(), request.getPassword());
	//
	//
	// UserDetails userDetails =
	// userDetailsService.loadUserByUsername(request.getEmail());
	// String token = this.helper.generateToken(userDetails);
	//
	// JwtResponse response = JwtResponse.builder()
	// .jwtToken(token)
	// .username(userDetails.getUsername()).build();
	// return new ResponseEntity<>(response, HttpStatus.OK);
	// }

	private void doAuthenticate(String email, String password) {

		// Retrieve user from the database based on the email
		User user = userService.getUserByEmail(email);

		if (user.getStatus().getStatusValue().equalsIgnoreCase("deactive")) {
			throw new InvalidInput("User has been deactivated ,Can't login");
		}

		// if (user == null) {
		// throw new BadCredentialsException("Invalid Username or Password!");
		// }

		// Update the email in the authentication object if it has been changed
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),
				password);

		try {
			manager.authenticate(authentication);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Invalid Username or Password!");
		}

		// UsernamePasswordAuthenticationToken authentication = new
		// UsernamePasswordAuthenticationToken(email, password);
		// try {
		// manager.authenticate(authentication);
		//
		//
		// } catch (BadCredentialsException e) {
		// throw new BadCredentialsException(" Invalid Username or Password !!");
		// }

	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<String> exceptionHandler() {

		return new ResponseEntity<String>("Credentials Invalid !!", HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/saveUser/{reportingTo}") //// ,@PathVariable("statusV") String statusV)
	public UserNDto createUser(@RequestBody User user, @PathVariable String reportingTo) {

		String email = user.getEmail();
		// try {
		User existingUser = userRepository.findByEmail(email);

		if (existingUser != null) {

			throw new InvalidInput("User alredy exists with this mail id");

		} else {
			UserNDto createUser = userService.createUser(user, reportingTo);
			if (createUser != null)
				sendWelcomeMail(user);
			return createUser;
		}
	}
//
	public String sendWelcomeMail(User user) {
		try {
			// Creating a simple mail message
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			String userEmail = user.getEmail();
			mailMessage.setFrom(sender);
			mailMessage.setTo(userEmail);
			String welcomeMessage = "Hello " + user.getUsername() + ",\n\n"
					+ "Welcome to Kloc CRM! We're delighted that you've joined us.\n"
					+ "We're happy to see you as part of our community.\n\n"
					+ "To get started, please log in using the credentials you provided.\n\n"
					+ "Best regards,\nThe Kloc CRM Team";
			mailMessage.setText(welcomeMessage);
			javaMailSender.send(mailMessage);
			return "Welcome Email Sent Successfully";
		} catch (Exception e) {
			return "Failed to Send Welcome Email. Please provide valid details.";
		}
	}
}
