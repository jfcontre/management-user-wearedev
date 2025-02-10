package com.jhoncontreras.usermanagement.service;

/**
 * @Author: Jhon Contreras
 * @Date:   15 nov. 2023
 * Interface for JWTToken Provider
 */

public interface JwtTokenProviderService<T> {
	public String generateToken(T identifier);
}
