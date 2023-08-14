package com.kloc.crm.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class UserNDto {
	
	private String userId;
	private String userName;
	private String email;
	private long mobileNo;
	private long altMobileNo;
	private String role;
	private String statusValue;
	private String reportingUsrId;
	private String reportingUsrName;
}
