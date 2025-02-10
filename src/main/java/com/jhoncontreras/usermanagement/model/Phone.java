package com.jhoncontreras.usermanagement.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jhoncontreras.usermanagement.utils.DateTimeUtils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * @Author: Jhon Contreras
 * @Date:   15 nov. 2023
 * Entity for phones
 */

@Entity
@Table(name="phone")
public class Phone {
	@Id
	@GeneratedValue(generator = "uuid2")
	private UUID id;
	@Column(name="pnumber")
	private String number;
	@Column(name="city_code")
	private String cityCode;
	@Column(name="country_code")
	private String countryCode;	
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;
	@Column(name = "updated_at", nullable = true)
	private LocalDateTime updatedAt;
	@JsonIgnore
	@ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}	
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}	
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getCreatedAt() {
        return DateTimeUtils.formatDate(createdAt);
    }

    public String getUpdatedAt() {
        return DateTimeUtils.formatDate(updatedAt);
    }

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return String.format(
				"Phone [id=%s, number=%s, cityCode=%s, countryCode=%s, createdAt=%s, updatedAt=%s]", id,
				number, cityCode, countryCode, createdAt, updatedAt);
	}
	
	
}
