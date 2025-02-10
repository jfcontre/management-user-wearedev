package com.jhoncontreras.usermanagement.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jhoncontreras.usermanagement.controller.dto.UserJwtTokenDto;
import com.jhoncontreras.usermanagement.model.User;
import com.jhoncontreras.usermanagement.service.JwtTokenProviderService;

/**
 * @Author: Jhon Contreras
 * @Date:   15 nov. 2023
 * Decorator to add the jwt token to a User
 */

@Component
public class UserJwtTokenDecoratorImpl implements UserJwtTokenDecorator {

	@Autowired
	private JwtTokenProviderService<String> jwtTokenProviderService;

	@Override
	public UserJwtTokenDto getUserJwtToken(User user) {				
		return new UserJwtTokenDto(user, jwtTokenProviderService.generateToken(user.getEmail()));
	}

}
