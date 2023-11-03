package com.kloc.crm.Controller;

import java.util.HashMap;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kloc.crm.Entity.User;
import com.kloc.crm.Repository.UserRepository;
import com.kloc.crm.Service.EmailService;

import jakarta.servlet.http.HttpSession;
@RestController
@RequestMapping("/forget")
@CrossOrigin("*")
public class ForgetController
{
	
	
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sender;
	
	@Autowired
	private UserRepository userRepository;
	
	//private static Random random ;
	
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;
	//int sendedOtp;
	
	@PostMapping("/forgetPassword/{email}")
	public ResponseEntity<String> sendOTP(@PathVariable("email") String email,HttpSession session)
	{
		if (email == null || email.equals(""))
			return new ResponseEntity<String>("Please Enter Email.",HttpStatus.BAD_REQUEST);
		
		User user	= userRepository.findByEmail(email);
		if (user == null)
			return new ResponseEntity<String>("User with this mail id does not exists",HttpStatus.UNAUTHORIZED);
		else
		{
			
			System.out.println("User with this mail id exists");
		Random	random=new Random();
		int	sendedOtp = random.nextInt(999999);
			session.setAttribute("sendedOtp", sendedOtp);
			System.out.println(sendedOtp);
			// verify otp
			String subject = "OTP From Kloc CRM";
			String message =""+sendedOtp;
			boolean flag = emailService.sendEmail(email,subject,message);
			if(flag)
			{
				return new ResponseEntity<String>("OTP Sent succefully to your mail id your OTP is : " + message,HttpStatus.OK);
			}
			else
				return new ResponseEntity<String>("Mail not send.",HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@PostMapping("/verify/{enteredOtp}")
	public ResponseEntity<String> VerifyOtp(@PathVariable("enteredOtp") int enteredOtp,HttpSession session)
	{	
		Integer sendedOtp =(Integer) session.getAttribute("sendedOtp");
		if(enteredOtp == sendedOtp)
		{
			System.out.println("Otp verified");
			return new ResponseEntity<String>("Otp matches successfully",HttpStatus.OK);
		}
		else
			return new ResponseEntity<String>("Otp does not match",HttpStatus.UNAUTHORIZED);
	}
	@PutMapping("/changePassword/{email}/{newPassword}/{confirmPassword}")
	public ResponseEntity<String> updatePassword(@PathVariable String  email, @PathVariable("newPassword") String newPassword,@PathVariable("confirmPassword") String confirmPassword)
	{
		User currentlyUser	=userRepository.findByEmail(email);
		if(newPassword!=null && newPassword!="")
		{
			boolean f =  newPassword.equals(confirmPassword);
			if(f)
			{
				System.out.println("Matches with old password");
				currentlyUser.setPassword(passwordEncoder.encode(newPassword));
				userRepository.save(currentlyUser);
				sendMail(currentlyUser);
				return new ResponseEntity<String>("Password Changed Successfully",HttpStatus.OK);
			}
			else
				return new ResponseEntity<String>("Check both the Password they are not matching",HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<String>("Password can not be null or empty",HttpStatus.BAD_REQUEST);
	}
	
	
	
	
	
	public String sendMail(User user) {
		try {
			// Creating a simple mail message
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			String userEmail = user.getEmail();
			mailMessage.setFrom(sender);
			mailMessage.setTo(userEmail);
			mailMessage.setSubject(" Changed Succesfully ");
			String resetConfirmationMessage = "Hello " + user.getUsername() + ",\n\n" +
	                "Your password has been successfully reset.\n" +
	                "We wanted to confirm that the password for your account has been changed.\n" +
	                "If you did not make this change, please contact our support immediately.\n\n" +
	                "If you initiated this password reset, no further action is needed.\n\n" +
	                "Thank you,\nThe Kloc CRM Team";
			mailMessage.setText(resetConfirmationMessage);
			javaMailSender.send(mailMessage);
			return "Welcome Email Sent Successfully";
		}catch (Exception e) {
			return "Failed to Send Welcome Email. Please provide valid details.";
		}
		
		
		//
		
		
   
	}
}




















