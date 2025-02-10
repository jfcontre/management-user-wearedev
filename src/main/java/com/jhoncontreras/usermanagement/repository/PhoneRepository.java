package com.jhoncontreras.usermanagement.repository;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jhoncontreras.usermanagement.model.Phone;

/**
 * @Author: Jhon Contreras
 * @Date:   15 nov. 2023
 * Repository for phones
 */

public interface PhoneRepository extends JpaRepository<Phone, UUID> {	 
}
