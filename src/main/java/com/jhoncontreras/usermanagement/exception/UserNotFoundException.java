package com.jhoncontreras.usermanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Author: Jhon Contreras
 * @Date:   15 nov. 2023
 * Exception for an inexistent User
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3753243555008513075L;

	public UserNotFoundException(String message) {
		super(message);
	}
}
