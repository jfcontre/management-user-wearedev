package com.jhoncontreras.usermanagement.controller.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Author: Jhon Contreras
 * @Date:   15 nov. 2023
 * DTO with common fields that a response must be send.
 */


@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponseDto<T> implements Serializable {

	private static final long serialVersionUID = 6610640525563381L;

	private boolean success;
	private String message;	
	private T body;
	
	@JsonCreator
	public BaseResponseDto(@JsonProperty("success")boolean success,@JsonProperty("message")String message, @JsonProperty("body")T body) {
		super();
		this.success = success;
		this.message = message;
		this.body = body;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}
	
}

