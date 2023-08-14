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
public class UserDto {
	
	private String userId;
	private String uname;
	private String role;
	private String value;
	private String reportingToUserId;
	private String reportingName;
	private String reportingEmail;

}
