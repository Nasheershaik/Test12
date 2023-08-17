package com.kloc.crm;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.kloc.crm.Entity.StaticData;
import com.kloc.crm.Entity.User;
import com.kloc.crm.Repository.StatusRepo;
import com.kloc.crm.Repository.UserRepository;


@SpringBootApplication
@EnableScheduling
public class KlocCrmApplication {
	public static void main(String[] args) {
		SpringApplication.run(KlocCrmApplication.class, args);
	}
}

@Component

class DataInsertionRunner implements CommandLineRunner {
	@Autowired
	private final StaticData statusService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private StatusRepo statusRepository;

	
	public DataInsertionRunner(StaticData statusService) {
		this.statusService = statusService;
	}

	@Override
	public void run(String... args) {
		String filePath ="./stat.txt";
		statusService.insertStatusFromFile(filePath);
		List<User> users	=userRepository.findAll();
		if(users.isEmpty()) {
			User user = new User();
			user.setUserId("user_0001");
			user.setUserName("Abhisarika Das");
			user.setEmail("abhisarikadas@kloctechnologies.com");
			user.setPassword(passwordEncoder.encode("abhisarika@kloctechnologies"));
			user.setRole(statusRepository.findByStatusValue("Administrator"));
			user.setMobileNo(9009076543l);
			user.setAltMobileNo(9992678420l);
			user.setStatus(statusRepository.findByStatusValue("Active"));
			User usern	=userRepository.save(user);
			user.setReportingTo(usern);
			userRepository.save(user);	
		}
	}
	
	
}
