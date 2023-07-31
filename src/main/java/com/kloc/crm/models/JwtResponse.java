/**
 * @Author :Avinash
 * @File_Name:JwtResponse.java
 * @Date:14-Jul-2023
 */
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
//This class will return a jwt token as a response when a user will login with valid credentials
public class JwtResponse {
	
	private String jwtToken;
	
	private String username;
}
