package com.kloc.crm.Scheduler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LogFileHandler {

//    public void deleteLogFile() 
//    {
//    	File f=new File("./");
//		
//		String[] s=f.list();
//		for(String st:s)
//		{
//			System.out.println(st);
//			
//			File f1=new File(f,st);
//			if(f1.isFile()&&f1.getName().equals("myapp.log"))
//			{
//				f1.delete();
//				try {
//					f1.createNewFile();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//    }
	 @Scheduled(cron="0 0 9 1 1/3 ?") // 120000 milliseconds = 2 minutes
	    public void clearLogFile() {
	        File logFile = new File("./myapp.log");
			System.out.println("xyz");
	        if (logFile.exists()) {
	            try  {
	            	FileWriter writer = new FileWriter(logFile, false);
	                writer.write(""); // Clear the log file by writing an empty string
	                writer.flush();
	                System.out.println("Log file cleared successfully.");
	            } catch (IOException e) {
	                System.out.println("Error clearing log file: " + e.getMessage());
	            }
	        } else {
	            System.out.println("Log file does not exist.");
	        }
	    }
}