/**
 * @author: windows
 * @Created_Date:Jul 17, 2023
 * @File_Name:NullDataException.java
 *
 */
package com.kloc.crm.Exception;

import lombok.Getter;

/**
 * Exception Class for Null Data input
 * 
 * @author Ankush
 */
@Getter
public class NullDataException extends RuntimeException
{
	private String message;
	
	/**
	 * this constructor for user understandable response.
	 */
	public NullDataException(String message)
	{
		super(String.format(message));
		this.message = message;
	}
}
