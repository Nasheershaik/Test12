package com.kloc.crm.Entity;
/**
 * @Author :Nasheer
 *@Date :06-July-2023
 *@FileName:Status.java
 */
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import jakarta.persistence.*;
import lombok.*;




@Entity    
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Status {
	
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "status-id-generator")
	@GenericGenerator(name = "status-id-generator",type= com.kloc.crm.Entity.IdGenerator.class,
			 parameters = {
	                    @Parameter(name = IdGenerator.INCREMENT_PARAM, value = "1"),
	                    @Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "Status_"),
	                    @Parameter(name = IdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d")
	            })
	@Column(name = "status_id")
	private String statusId;
	
	private String tableName;
	
	private String statusType;
	
	private String statusValue;
	
	private String statusDescription;


}
