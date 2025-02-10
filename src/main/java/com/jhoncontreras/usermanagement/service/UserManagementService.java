package com.jhoncontreras.usermanagement.service;

import java.util.List;
import java.util.UUID;

import com.jhoncontreras.usermanagement.controller.dto.UserJwtTokenDto;
import com.jhoncontreras.usermanagement.exception.UserNotFoundException;
import com.jhoncontreras.usermanagement.model.User;

/**
 * @Author: Jhon Contreras
 * @Date:   15 nov. 2023
 * Interface for User management
 */

public interface UserManagementService {
	List<User> find();
	UserJwtTokenDto create(User user);
	UserJwtTokenDto update(User user, UUID id);
	User findById(UUID id) throws UserNotFoundException;

}
