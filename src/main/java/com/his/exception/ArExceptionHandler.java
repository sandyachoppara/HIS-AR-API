package com.his.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ArExceptionHandler {
	
	// AR  Custom Exception
	@ExceptionHandler(value=ArException.class)
	public ResponseEntity<ArApiError> handleEdException(ArException e) {
		ArApiError apiError=new ArApiError("AR-API-03", e.getMessage(), new Date());
		return new ResponseEntity<ArApiError>(apiError,HttpStatus.BAD_REQUEST);
	};
	
	@ExceptionHandler(value=Exception.class)
	public ResponseEntity<ArApiError> handleException(Exception e) {
		ArApiError apiError=new ArApiError("AR-API-03", e.getMessage(), new Date());
		return new ResponseEntity<ArApiError>(apiError,HttpStatus.BAD_REQUEST);
	};

}
