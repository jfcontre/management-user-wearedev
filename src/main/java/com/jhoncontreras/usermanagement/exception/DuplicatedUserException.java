package com.jhoncontreras.usermanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Author: Jhon Contreras
 * @Date:   15 nov. 2023
 * Exception for a existing User
 */

@ResponseStatus(HttpStatus.FORBIDDEN)
public class DuplicatedUserException extends RuntimeException {

	private static final long serialVersionUID = 32177480148399466L;

	public DuplicatedUserException(String string) {
		super(string);
	}

}
