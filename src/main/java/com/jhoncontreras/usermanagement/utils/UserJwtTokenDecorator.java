package com.jhoncontreras.usermanagement.utils;

/**
 * @Author: Jhon Contreras
 * @Date:   15 nov. 2023
 */

import com.jhoncontreras.usermanagement.controller.dto.UserJwtTokenDto;
import com.jhoncontreras.usermanagement.model.User;

public interface UserJwtTokenDecorator {
	 UserJwtTokenDto getUserJwtToken(User user);
}
