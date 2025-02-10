package com.jhoncontreras.usermanagement.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jhoncontreras.usermanagement.exception.UserNotFoundException;
import com.jhoncontreras.usermanagement.controller.dto.BaseResponseDto;
import com.jhoncontreras.usermanagement.controller.dto.UserJwtTokenDto;
import com.jhoncontreras.usermanagement.exception.DuplicatedUserException;
import com.jhoncontreras.usermanagement.exception.PasswordFormatException;
import com.jhoncontreras.usermanagement.model.User;
import com.jhoncontreras.usermanagement.service.UserManagementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

/**
 * @Author: Jhon Contreras
 * @Date:   15 nov. 2023
 * REST Controller for User resources
 */


@RestController
@RequestMapping("/api/v1/user")
public class UserManagementRestController {

	@Autowired
	private UserManagementService userManagementService;

	@Operation(summary = "Get list of users")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Get All Users", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
			@ApiResponse(responseCode = "500", description = "General Error Getting All Users", content = @Content) })
	@GetMapping
	public ResponseEntity<BaseResponseDto<List<User>>> getUsers() {
		List<User> users = userManagementService.find();
		BaseResponseDto<List<User>> response = new BaseResponseDto<List<User>>(true, "Users retrieve successfully.",
				users);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Get a user by its id")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Found the user", 
	    content = { @Content(mediaType = "application/json", 
	      schema = @Schema(implementation = User.class)) }),
	  @ApiResponse(responseCode = "400", description = "Invalid id supplied", 
	    content = @Content), 
	  @ApiResponse(responseCode = "404", description = "User not found", 
	    content = @Content),
	  @ApiResponse(responseCode = "500", description = "General Error Getting User by id", content = @Content)})
	@GetMapping(value = "/{id}")
	public ResponseEntity<BaseResponseDto<User>> getUserById(@PathVariable("id") UUID id) {
		BaseResponseDto<User> response = null;
		try {
			User user = userManagementService.findById(id);
			response = new BaseResponseDto<User>(true, "Users retrieve successfully.", user);
			return ResponseEntity.ok(response);
		} catch (UserNotFoundException ex) {
			response = new BaseResponseDto<User>(false, ex.getMessage(), null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			response = new BaseResponseDto<User>(false, "Cannot get user by id." + ex.getMessage(), null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@Operation(summary = "Create a user")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "201", description = "User created", 
	    content = { @Content(mediaType = "application/json", 
	      schema = @Schema(implementation = User.class)) }),
	  @ApiResponse(responseCode = "400", description = "Invalid body supplied", 
	    content = @Content), 
	  @ApiResponse(responseCode = "403", description = "Invalid password supplied", 
	    content = @Content),
	  @ApiResponse(responseCode = "403", description = "User with existing email supplied", 
	    content = @Content),	  
	  @ApiResponse(responseCode = "500", description = "General Error Creating a User", content = @Content)})
	@PostMapping
	public ResponseEntity<BaseResponseDto<UserJwtTokenDto>> createUser(@RequestBody User user) {
		BaseResponseDto<UserJwtTokenDto> response = null;
		try {
			UserJwtTokenDto createdUser = userManagementService.create(user);
			response = new BaseResponseDto<UserJwtTokenDto>(true, "User created succesfully", createdUser);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(createdUser.getUser().getId()).toUri();
			return ResponseEntity.created(location).body(response);
		} catch (DuplicatedUserException | PasswordFormatException ex) {
			response = new BaseResponseDto<UserJwtTokenDto>(false, ex.getMessage(), null);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			response = new BaseResponseDto<UserJwtTokenDto>(false, "Cannot create user." + ex.getMessage(), null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@Operation(summary = "Update a user")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "User updated", 
	    content = { @Content(mediaType = "application/json", 
	      schema = @Schema(implementation = User.class)) }),
	  @ApiResponse(responseCode = "400", description = "Invalid body supplied", 
	    content = @Content), 
	  @ApiResponse(responseCode = "403", description = "Invalid password supplied", 
	    content = @Content),
	  @ApiResponse(responseCode = "403", description = "User with existing email supplied", 
	    content = @Content),
	  @ApiResponse(responseCode = "404", description = "User not found", 
	    content = @Content),
	  @ApiResponse(responseCode = "500", description = "General Error Updating a User", content = @Content)})
	@PutMapping(value = "/{id}")
	public ResponseEntity<BaseResponseDto<UserJwtTokenDto>> updateUser(@PathVariable("id") UUID id,
			@RequestBody User updateUser) {
		BaseResponseDto<UserJwtTokenDto> response = null;
		try {
			UserJwtTokenDto updatedUser = userManagementService.update(updateUser, id);
			response = new BaseResponseDto<UserJwtTokenDto>(true, "User updated succesfully", updatedUser);
			return ResponseEntity.ok(response);
		} catch (DuplicatedUserException | PasswordFormatException ex) {
			response = new BaseResponseDto<UserJwtTokenDto>(false, ex.getMessage(), null);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		} catch (UserNotFoundException ex) {
			response = new BaseResponseDto<UserJwtTokenDto>(false, ex.getMessage(), null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			response = new BaseResponseDto<UserJwtTokenDto>(false, "Cannot UPDATE user." + ex.getMessage(), null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

}
