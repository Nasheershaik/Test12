
package com.kloc.crm.Entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author :Avinash
 * @File_Name:SalesPerson.java
 * @Date:05-Jul-2023
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
/**
 * Represents a salesperson entity.
 */
public class SalesPerson {
    
    // Unique identifier for the salesperson
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sp_seq")
    @GenericGenerator(
            name = "sp_seq",
            type = com.kloc.crm.Entity.IdGenerator.class,
            parameters = {
                    @Parameter(name = IdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "Sp_"),
                    @Parameter(name = IdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d")
            }
    )
    private String salespersonId;
    
    // Target set for the salesperson
    @Column(name = "Target")
    private int target;
    
    // Frequency of sales activities
    @Column(name = "Frequency")
    private int frequency;
    
    // Amount achieved by the salesperson
    @Column(name = "Amount")
    private double amount;
    
    // Currency in which the amount is measured
    @Column(name = "Currency")
    private String currency;
    
    // Duration of the sales period
    @Column(name = "Duration")
    private int duration;
    
    // User associated with the salesperson
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
   // @JsonBackReference("user")
    private User user;
    
    // Constructors, getters, and setters would typically be defined here
    
}
