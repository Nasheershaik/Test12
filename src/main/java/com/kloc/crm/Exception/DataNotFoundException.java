/**
 * @Author : RiteshSingh
 * @Date : 06-July-2023
 * @FileName : DataNotFoundException.java
 */
package com.kloc.crm.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The DataNotFoundException class represents an exception that is thrown when data is not found.
 * It is used to return a 404 Not Found HTTP status.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DataNotFoundException extends RuntimeException {

    private String resourceName;
    private String filename;
    private Object fieldValue;
    private String message;

    /**
     * Constructs a DataNotFoundException with the given resource name, filename, and field value.
     *
     * @param resourceName The name of the resource
     * @param filename     The name of the file
     * @param fieldValue   The field value that was not found
     */
    public DataNotFoundException(String resourceName, String filename, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, filename, fieldValue));
        this.resourceName = resourceName;
        this.filename = filename;
        this.fieldValue = fieldValue;
    }

    /**
     * Constructs a DataNotFoundException with a custom message.
     *
     * @param message The custom error message
     */
    public DataNotFoundException(String message) {
        super(String.format(message));
        this.message = message;
    }

    /**
     * Returns the custom error message for the exception.
     *
     * @return The error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the name of the resource.
     *
     * @return The resource name
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * Returns the name of the file.
     *
     * @return The file name
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Returns the field value that was not found.
     *
     * @return The field value
     */
    public Object getFieldValue() {
        return fieldValue;
    }
}
