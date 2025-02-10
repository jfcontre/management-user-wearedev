package com.jhoncontreras.usermanagement.utils;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jhoncontreras.usermanagement.model.ConfigurationApp;
import com.jhoncontreras.usermanagement.repository.ConfigurationAppRepository;

/**
 * @Author: Jhon Contreras
 * @Date:   15 nov. 2023
 * Validate a password against the REGEX configured on DB.
 */

@Component
public class PasswordValidator {
	
	@Autowired
	private ConfigurationAppRepository configurationAppRepository;	

	private Pattern pattern;
	private Matcher matcher;	

	public boolean validatePassword(String password){
		Optional<ConfigurationApp> patternValue = configurationAppRepository.findByName(Constants.NAME_REGEX_PASSWORD);
		if(patternValue.isPresent()) {
			pattern = Pattern.compile(patternValue.get().getValue());						
			matcher = pattern.matcher(password);
			return matcher.matches();
		} else {
			return false;
		}
	}	
}
