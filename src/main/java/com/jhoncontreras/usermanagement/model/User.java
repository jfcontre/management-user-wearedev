package com.jhoncontreras.usermanagement.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.jhoncontreras.usermanagement.utils.DateTimeUtils;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * @Author: Jhon Contreras
 * @Date:   15 nov. 2023
 * Entity for users
 */

@Entity
@Table(name = "userm")
public class User {
	@Id
	@GeneratedValue(generator = "uuid2")
	private UUID id;
	@Column(name = "uname")
	private String name;
	@Column(name = "email")
	private String email;
	@Column(name = "passw")
	private String password;
	@OneToMany(mappedBy = "user", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Phone> phones = new ArrayList<>();
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;
	@Column(name = "updated_at", nullable = true)
	private LocalDateTime updatedAt;
	@Column(name = "last_login", nullable = true)
	private LocalDateTime lastLogin;
	@Column(name = "is_active", nullable = false)
	private boolean isActive;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}
	
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedAt() {
        return DateTimeUtils.formatDate(createdAt);
    }

    public String getUpdatedAt() {
        return DateTimeUtils.formatDate(updatedAt);
    }

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getLastLogin() {
		return DateTimeUtils.formatDate(lastLogin);
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return String.format(
				"User [id=%s, name=%s, email=%s, phones=%s, createdAt=%s, updatedAt=%s, lastLogin=%s, isActive=%s]", id,
				name, email, phones, createdAt, updatedAt, lastLogin, isActive);
	}

}
