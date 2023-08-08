/**
 * @Author: Ankush
 * @Created_Date: Jul 17, 2023
 * @File_Name: NullDataException.java
 */
package com.kloc.crm.Exception;

import lombok.Getter;

/**
 * Exception Class for Null Data input.
 * This exception is thrown when there is a null data input where non-null data is expected.
 * It is used to indicate that the input data is null, which is not allowed.
 *
 * @Author: windows
 */
@Getter
public class NullDataException extends RuntimeException 
{

    private String message;

    /**
     * Constructor for creating a NullDataException with a user-readable message.
     *
     * @param message The user-readable message describing the null data input issue.
     */
    public NullDataException(String message)
    {
        super(String.format(message));
        this.message = message;
    }
}
