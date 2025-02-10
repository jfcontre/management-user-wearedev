package com.jhoncontreras.usermanagement.controller.dto;

import com.jhoncontreras.usermanagement.model.User;

/**
 * @Author: Jhon Contreras
 * @Date:   15 nov. 2023
 * DTO that contains User and JWT token data.
 */


public class UserJwtTokenDto {
	
	private User user;
	private String token;
	
	public UserJwtTokenDto(User user, String token) {
		super();
		this.user = user;
		this.token = token;
	}
	
	public User getUser() {
		return user;
	}
	
	public String getToken() {
		return token;
	}
	
}
