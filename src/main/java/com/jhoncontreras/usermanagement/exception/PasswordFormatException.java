package com.jhoncontreras.usermanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Author: Jhon Contreras
 * @Date:   15 nov. 2023
 * Exception for a insecure password
 */

@ResponseStatus(HttpStatus.FORBIDDEN)
public class PasswordFormatException extends RuntimeException {

	private static final long serialVersionUID = 32177480148399466L;

	public PasswordFormatException(String string) {
		super(string);
	}

}
