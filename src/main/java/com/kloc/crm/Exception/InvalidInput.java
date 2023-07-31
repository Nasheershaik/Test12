/**
 * @author: windows
 * @Created_Date:Jul 17, 2023
 * @File_Name:InvalidInput.java
 *
 */
package com.kloc.crm.Exception;

import lombok.Getter;

/**
 * Exception Class for invalid input
 * 
 * @author Ankush
 */
@Getter
public class InvalidInput extends RuntimeException
{
	private String message;
	/**
	 * this constructor for user understandable response.
	 */
	public InvalidInput(String message) 
	{
		super(String.format(message));
		this.message = message;
	}
}
