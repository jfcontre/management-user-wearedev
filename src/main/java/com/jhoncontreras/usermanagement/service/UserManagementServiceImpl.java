package com.jhoncontreras.usermanagement.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jhoncontreras.usermanagement.exception.UserNotFoundException;
import com.jhoncontreras.usermanagement.controller.dto.UserJwtTokenDto;
import com.jhoncontreras.usermanagement.exception.DuplicatedUserException;
import com.jhoncontreras.usermanagement.exception.PasswordFormatException;
import com.jhoncontreras.usermanagement.model.Phone;
import com.jhoncontreras.usermanagement.model.User;
import com.jhoncontreras.usermanagement.repository.UserRepository;
import com.jhoncontreras.usermanagement.utils.PasswordValidator;
import com.jhoncontreras.usermanagement.utils.UserJwtTokenDecorator;

/**
 * @Author: Jhon Contreras
 * @Date:   15 nov. 2023
 * Implementation of user management services.
 */

@Service
public class UserManagementServiceImpl implements UserManagementService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserJwtTokenDecorator userJwtTokenDecorator;
	
	@Autowired
	private PasswordValidator passwordValidator;

	@Override
	public List<User> find() {
		List<User> users = userRepository.findAll();
		return users;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public UserJwtTokenDto create(User user) {
		if(checkUserExists(user, false)) {
			throw new DuplicatedUserException("User has already registered with email " + user.getEmail());	
		}
		
		if(!checkPasswordFormat(user)) {
			throw new PasswordFormatException("Password format doesn't follow next rules: At least one digit, at least one upper case alphanumeric, at least one lower case alphanumeric, at least 12 characters long, at least one special character. ");	
		}
		
		LocalDateTime now = LocalDateTime.now();
		user.setCreatedAt(now);
		user.setLastLogin(now);
		user.setActive(true);
		
		List<Phone> toSavePhones = Optional.ofNullable(user.getPhones())
			    .orElse(Collections.emptyList())
				.stream()
				.peek(s->s.setUser(user))
				.peek(s->s.setCreatedAt(LocalDateTime.now()))								
				.collect(Collectors.toList());			
		
		user.setPhones(toSavePhones);
		
		User savedUser = userRepository.save(user);
				
		return userJwtTokenDecorator.getUserJwtToken(savedUser);						
		
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public UserJwtTokenDto update(User user, UUID id) {
	    System.out.println("id:" + id);
	    user.setId(id);

	    if (checkUserExists(user, true)) {
	        throw new DuplicatedUserException("User has already registered with email " + user.getEmail());
	    }
	    
	    if(!checkPasswordFormat(user)) {
			throw new PasswordFormatException("Password format doesn't follow next rules: At least one digit, at least one upper case alphanumeric, at least one lower case alphanumeric, at least 12 characters long, at least one special character. ");	
		}

	    Optional<User> optionalUser = userRepository.findById(id);

	    if (optionalUser.isPresent()) {
	        User existingUser = optionalUser.get();
	        
	        existingUser.setName(user.getName());
	        existingUser.setPassword(user.getPassword());
	        	        
	        LocalDateTime now = LocalDateTime.now();
	        existingUser.setUpdatedAt(LocalDateTime.now());
			user.setLastLogin(now);
			user.setActive(true);

	        List<Phone> updatedPhones = user.getPhones().stream()
	                .map(phone -> {
	                    if (phone.getCreatedAt() == null) {
	                        phone.setCreatedAt(LocalDateTime.now());
	                    }
	                    phone.setUpdatedAt(LocalDateTime.now());
	                    phone.setUser(existingUser);
	                    return phone;
	                })
	                .collect(Collectors.toList());

	        existingUser.getPhones().clear();
	        existingUser.getPhones().addAll(updatedPhones);

	        User savedUser = userRepository.save(existingUser);
			
			return userJwtTokenDecorator.getUserJwtToken(savedUser);	
	    } else {
	        throw new UserNotFoundException("User with id given doesn't exist.");
	    }
	}
	
	private boolean checkUserExists(User user, boolean isUpdate) {
	    if (isUpdate) {
	        return userRepository.findByEmailAndNotId(user.getEmail(), user.getId())
	                .isPresent();
	    } else {
	        return userRepository.findByEmail(user.getEmail())
	                .isPresent();
	    }
	}
	
	private boolean checkPasswordFormat(User user) {
	    return passwordValidator.validatePassword(user.getPassword());
	}

	@Override
	public User findById(UUID id) throws UserNotFoundException {
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFoundException("Account not found with id :" + id);
		}
		return user.get();
	}

}
