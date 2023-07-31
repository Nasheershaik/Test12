/**
 * @Author :Avinash
 * @File_Name:AppConfig.java
 * @Date:14-Jul-2023
 */
package com.kloc.crm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		// Create and return an instance of BCryptPasswordEncoder
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        // Get the AuthenticationManager from the AuthenticationConfiguration and return it
        return builder.getAuthenticationManager();
    }
}

