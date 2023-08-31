/**
 * @Author :Avinash
 * @File_Name:SecurityConfig.java
 * @Date:14-Jul-2023
 */
package com.kloc.crm.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kloc.crm.security.JwtAuthenticationEntryPoint;
import com.kloc.crm.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationEntryPoint point;

	@Autowired
	private JwtAuthenticationFilter filter;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// Disable CSRF protection
		http.csrf(csrf -> csrf.disable())
				// Disable CORS
				.cors(cors -> cors.disable())
				// Configure authorization rules for different URLs
				.authorizeHttpRequests(auth -> auth.requestMatchers("/api/**").authenticated()
						.requestMatchers("/auth/login").permitAll().requestMatchers("/auth/saveUser/{reportingTo}")
						.authenticated().requestMatchers("/app/**").authenticated().requestMatchers("/forget/**").permitAll().requestMatchers("/statuses/**").authenticated().requestMatchers("/task/**")
						.authenticated().requestMatchers("/OfferingController/**").authenticated()
						.requestMatchers("/ContactController/**").authenticated().requestMatchers("/app/vendorpartners/**").authenticated().requestMatchers("/statuses")
						.authenticated().requestMatchers("/notifications/**").authenticated()
						.requestMatchers("/emails/**").authenticated().requestMatchers("/Opr/Opportunity/**")
						.authenticated().requestMatchers("/opr/opportunitySubController/**").authenticated()
						.requestMatchers("cust/Customer/**").authenticated().anyRequest().authenticated())
				// Configure exception handling for authentication errors
				.exceptionHandling(ex -> ex.authenticationEntryPoint(point))
				// Set session management to IF_Required to store users otp
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).maximumSessions(1).maxSessionsPreventsLogin(false).expiredUrl("/session-expired"));

		// Add JwtAuthenticationFilter before UsernamePasswordAuthenticationFilter
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
	
	

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		// Create a DaoAuthenticationProvider
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		// Set the UserDetailsService
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		// Set the PasswordEncoder
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		return daoAuthenticationProvider;
	}
}
