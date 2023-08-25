package com.kloc.crm.Scheduler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LogFileHandler {

	/**
	 * Scheduled method to clear the contents of the log file on a recurring basis.
	 * This method is triggered by a cron expression that runs on the 1st day of every 3rd month at 9:00 AM.
	 * The log file "./myapp.log" is located in the application directory.
	 * If the file exists, its contents are cleared by overwriting it with an empty string.
	 * If the file does not exist, a message is printed indicating its absence.
	 */
	@Scheduled(cron = "0 0 9 1 1/3 ?")
	public void clearLogFile() {
	    // Define the path to the log file
	    File logFile = new File("./myapp.log");
	    
	    // Check if the log file exists
	    if (logFile.exists()) {
	        try {
	            // Create a FileWriter to write to the log file
	            FileWriter writer = new FileWriter(logFile, false);
	            
	            // Clear the log file by writing an empty string and flushing the writer
	            writer.write("");
	            writer.flush();
	            
	            // Print success message
	            System.out.println("Log file cleared successfully.");
	        } catch (IOException e) {
	            // Handle any exceptions that occur during file clearing
	            System.out.println("Error clearing log file: " + e.getMessage());
	        }
	    } else {
	        // Print a message if the log file does not exist
	        System.out.println("Log file does not exist.");
	    }
	}

}