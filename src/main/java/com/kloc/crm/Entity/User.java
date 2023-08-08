/**
 * @Author :Avinash
 * @File_Name:User.java
 * @Date:04-Jul-2023
 */
package com.kloc.crm.Entity;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kloc.crm.Entity.IdGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
/**
 * Represents a user entity.
 */
public class User implements UserDetails {
    
   

	// Unique identifier for the user
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @GenericGenerator(
            name = "user_seq",
            type = com.kloc.crm.Entity.IdGenerator.class,
            parameters = {
                    @Parameter(name = IdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "user_"),
                    @Parameter(name = IdGenerator.NUMBER_FORMAT_PARAMETER, value = 
                    		"%04d")
            }
    )
    private String userId;
    
    // Name of the user
    private String userName;
    
    // Email address of the user
    @Column(name = "email",unique = true)
    private String email;
    
 // Password associated with the user
    @Column(name = "Password")
    private String password;
    
    // Role of the user
    @ManyToOne//(cascade = CascadeType.PERSIST)
    @JsonBackReference("role")
    private Status role;
    
    // Mobile number of the user
    @Column(name = "Mobile_No")
    private long mobileNo;
    
    // Alternate mobile number of the user
    @Column(name = "Alt_Mobile_No")
    private long altMobileNo;
    
    // User to whom this user reports
    @ManyToOne
    @JsonBackReference("reportingTo")
    private User reportingTo;
    
   @ManyToOne//(cascade = CascadeType.p)
   //mapping with status table
   @JsonBackReference("status")
   private Status status;
    
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	
	public long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public long getAltMobileNo() {
		return altMobileNo;
	}

	public void setAltMobileNo(long altMobileNo) {
		this.altMobileNo = altMobileNo;
	}
	
	public User getReportingTo() {
		return reportingTo;
	}

	public void setReportingTo(User reportingTo) {
		this.reportingTo = reportingTo;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
	    // Assuming you have a 'Status' object representing the user's role
	    Status role = getRole();

	    // Convert the role object to a role name (String) and add it to the authorities list
	    authorities.add(new SimpleGrantedAuthority(role.getStatusValue())); // Assuming 'getCategory()' gives the role name

	    // If a user can have multiple roles, you can add them like this:
	    // for (Status role : getRoles()) {
	    //     authorities.add(new SimpleGrantedAuthority(role.getCategory()));
	    // }

	    return authorities;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}

	public Status getRole() {
		return role;
	}

	public void setRole(Status role) {
		this.role = role;
	}


}