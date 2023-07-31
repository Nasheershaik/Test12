/**
 * @Author :Avinash
 * @File_Name:UserDetailsServiceImpl.java
 * @Date:14-Jul-2023
 */
package com.kloc.crm.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kloc.crm.Entity.User;
import com.kloc.crm.Repository.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//	User user	=userRepository.findByEmail(username).orElseThrow(()-> new RuntimeException("could not found user with this mail id"));
//		
//		return user;
//	}
	
	 // Find the user based on the provided username (email)
	 @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        User user = userRepository.findByEmail(username);
	        if (user == null) {
	            throw new UsernameNotFoundException("Could not find user with this email: " + username);
	        }
	        // Create and return a UserDetails object using the user's details
	        return org.springframework.security.core.userdetails.User.builder()
	                .username(user.getEmail())
	                .password(user.getPassword())
	               // .roles("USER") // Set the user's roles here
	                .build();
	    }
	
}
