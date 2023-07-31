/**
 * @author: windows
 * @Created_Date:Jul 16, 2023
 * @File_Name:ExceptionResponseController.java
 *
 */
package com.kloc.crm.Exception.ExceptionResponce;
import javax.naming.NotContextException;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import com.fasterxml.jackson.core.JsonParseException;
import com.kloc.crm.Exception.DataNotFoundException;
import com.kloc.crm.Exception.InvalidInput;
import com.kloc.crm.Exception.NullDataException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
/**
 * @author windows
 *
 */
@SuppressWarnings("unused")
@ControllerAdvice
public class ExceptionResponseController
{
	/**
	 * If Data not found then this response goes to the user.
	 *
	 * @param Accept the Exception.
	 * @return Understandable Response to the user.
	 */
	@ExceptionHandler(InternalError.class)
	private ResponseEntity<String> InternalError(InternalError internalError) 
	{
		return new ResponseEntity<String>(internalError.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * If Data not found then this response goes to the user.
	 *
	 * @param Accept the Exception.
	 * @return Understandable Response to the user.
	 */
	@ExceptionHandler(DataNotFoundException.class)
	private ResponseEntity<String> DataNotFoundExceptionResponse(DataNotFoundException dataNotFoundException) 
	{
		return new ResponseEntity<String>("Data Not found with corrsponding id please enter valid id",HttpStatus.NOT_FOUND);
	}
	
	/**
	 * If Data not found then this response goes to the user.
	 *
	 * @param Accept the Exception.
	 * @return Understandable Response to the user.
	 */
	@ExceptionHandler(NullDataException.class)
	private ResponseEntity<String> DataNotFoundExceptionResponse(NullDataException nullDataException) 
	{
		return new ResponseEntity<String>(nullDataException.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Description of the method.
	 *
	 * @param parameter1 Description of the parameter.
	 * @return Description of the return value.
	 */
	@ExceptionHandler(InvalidInput.class)
	private ResponseEntity<String> InternalServerErrorResponse(InvalidInput invalidInput)
	{
		return new ResponseEntity<String>(invalidInput.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Description of the method.
	 *
	 * @param parameter1 Description of the parameter.
	 * @return Description of the return value.
	 */
	@ExceptionHandler(NotContextException.class)
	private ResponseEntity<String> NoContentException(NotContextException contextException)
	{
		return new ResponseEntity<String>("There is no content with respective this credentials",HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Description of the method.
	 *
	 * @param parameter1 Description of the parameter.
	 * @return Description of the return value.
	 */
	@ExceptionHandler(NullPointerException.class)
	private ResponseEntity<String> NullPointerException(NullPointerException nullPointerException) 
	{
		return new ResponseEntity<String>("Data not found.",HttpStatus.NOT_FOUND);
	}
	
	
	/**
	 * Description of the method.
	 *
	 * @param parameter1 Description of the parameter.
	 * @return Description of the return value.
	 */
	@ExceptionHandler(InternalServerError.class)
	private ResponseEntity<String> InternalServerErrorResponse(InternalServerError internalServerError)
	{
		return new ResponseEntity<String>("Something went Wrong.", HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Description of the method.
	 *
	 * @param parameter1 Description of the parameter.
	 * @return Description of the return value.
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	private ResponseEntity<String> MethodArgumentTypeMismatchExceptionResponse(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException)
	{
		return new ResponseEntity<String>("Please Check Inputs.", HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	private ResponseEntity<String> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException)
	{
		return new ResponseEntity<String>("Please change your http method request ",HttpStatus.BAD_REQUEST);
	}
	
	@ResponseBody
	@ExceptionHandler(io.jsonwebtoken.security.SignatureException.class)
	private ResponseEntity<String> JwtSignatureExceptionHandler(io.jsonwebtoken.security.SignatureException signatureException)
	{
		return new ResponseEntity<String>("Your JWT token is manipulated,please enter Autogenerated Token without manipulation",HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(JsonParseException.class)
	private ResponseEntity<String> jsonParseExceptionHandler(JsonParseException parseException)
	{
		return new ResponseEntity<String>("only whiteSpace is allowed in between the tokens please remove unwanted character ",HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(MalformedJwtException.class)
	private ResponseEntity<String> jwtMalformedJwtException(MalformedJwtException exception)
	{
		return new ResponseEntity<String>("Please enter valid JWt token ",HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(PropertyValueException.class)
	private ResponseEntity<String> notNullPropertyValueException(PropertyValueException notnullException)
	{
		return new ResponseEntity<String>("please enter the value within notnull field",HttpStatus.BAD_REQUEST);
	}
	
}