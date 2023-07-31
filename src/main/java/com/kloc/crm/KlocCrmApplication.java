package com.kloc.crm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.kloc.crm.Entity.StaticData;


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

	
	public DataInsertionRunner(StaticData statusService) {
		this.statusService = statusService;
	}

	@Override
	public void run(String... args) {
		String filePath ="./stat.txt";
		statusService.insertStatusFromFile(filePath);
	}
}
