package com.jhoncontreras.usermanagement.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @Author: Jhon Contreras
 * @Date:   15 nov. 2023
 * Entity for Application configurations
 */

@Entity
@Table(name="configurationapp")
public class ConfigurationApp {
	@Id	
	private UUID id;
	@Column(name="name_parameter")
	private String name;
	@Column(name="value_parameter")
	private String value;
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
