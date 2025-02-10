package com.jhoncontreras.usermanagement.repository;


import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jhoncontreras.usermanagement.model.ConfigurationApp;

/**
 * @Author: Jhon Contreras
 * @Date:   15 nov. 2023
 * Repository for application configurations
 */

public interface ConfigurationAppRepository extends JpaRepository<ConfigurationApp, UUID> {
		
	@Query("SELECT ca FROM ConfigurationApp ca WHERE ca.name = :name")
	Optional<ConfigurationApp> findByName(@Param("name") String name);
}
