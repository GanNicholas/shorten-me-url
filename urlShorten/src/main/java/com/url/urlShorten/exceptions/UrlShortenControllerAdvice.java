package com.url.urlShorten.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UrlShortenControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
		return new ResponseEntity<String>("Error proceessing request. Please try again.",
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UrlEntityCreationException.class)
	public ResponseEntity<String> handleUrlEntityCreationException(UrlEntityCreationException ex) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UrlEntityRetrievalException.class)
	public ResponseEntity<String> handleUrlEntityRetrievalException(UrlEntityRetrievalException ex) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
