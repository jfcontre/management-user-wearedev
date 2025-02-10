package com.jhoncontreras.usermanagement.repository;


import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jhoncontreras.usermanagement.model.User;

/**
 * @Author: Jhon Contreras
 * @Date:   15 nov. 2023
 * Repository for users
 */

public interface UserRepository extends JpaRepository<User, UUID> {
	@Query("SELECT u FROM User u WHERE u.email = :email AND u.id <> :userId")
	Optional<User> findByEmailAndNotId(@Param("email") String email, @Param("userId") UUID userId);

	@Query("SELECT u FROM User u WHERE u.email = :email")
	Optional<User> findByEmail(@Param("email") String email);		 
	
	@Query("SELECT u FROM User u WHERE u.id = :id")
	Optional<User> findById(@Param("id") UUID email);
}
