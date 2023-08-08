/**
 * @Author: Ankush
 * @Created_Date: Jul 17, 2023
 * @File_Name: InvalidInput.java
 */
package com.kloc.crm.Exception;

import lombok.Getter;

/**
 * Exception class for invalid input.
 * This exception is thrown when there is an issue with the input provided by the user.
 * It is used to indicate that the input is invalid and cannot be processed.
 *
 * @Author: Ankush
 */
@Getter
public class InvalidInput extends RuntimeException 
{
    private String message;

    /**
     * Constructor for creating an InvalidInput exception with a user-readable message.
     *
     * @param message The user-readable message describing the invalid input issue.
     */
    public InvalidInput(String message)
    {
        super(String.format(message));
        this.message = message;
    }
}
