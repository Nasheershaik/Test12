package com.kloc.crm.models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
//This class will contain user email and password when  we will do login
public class JwtRequest {
	private String email;
	
	private String password;
}