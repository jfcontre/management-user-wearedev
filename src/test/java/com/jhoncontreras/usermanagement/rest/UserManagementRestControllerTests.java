package com.jhoncontreras.usermanagement.rest;

import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhoncontreras.usermanagement.controller.UserManagementRestController;
import com.jhoncontreras.usermanagement.model.Phone;
import com.jhoncontreras.usermanagement.model.User;

/**
 * @Author: Jhon Contreras
 * @Date:   15 nov. 2023
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserManagementRestControllerTests {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @InjectMocks
    private UserManagementRestController userController;
        
    /**
     * When you send a get request, then service must return a 200 http code and the three names loaded by default on script.
     * @Return HttpCode 400 - BadRequest
     */
    @Test
    public void testGetUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.body.[*].name").value(Matchers.containsInAnyOrder("John Doe", "Alice Smith", "Bob Johnson")));
    }
    
    /**
     * When you doesn't send a right body in create operation, then service must return a 400 http error code
     */
    @Test
    public void testWrongRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user"))
               .andExpect(MockMvcResultMatchers.status().isBadRequest());               
    }
    
    /**
     * When you send a post request with right body and email is not duplicated, then service must return a 201 http code.
     */
    @Test
    public void testCreateUser() throws Exception {
    	
    	// Mock user data
        User user = new User();        
        user.setName("Jhon Contreras");
        user.setEmail("jhon@contreras.com");
        user.setPassword("Passw0rd@Secure");
        
        Phone p1 = new Phone();
        p1.setCityCode("CO");
        p1.setCountryCode("CO");
        p1.setNumber("1234556677");                      
        p1.setUser(user);
        
        user.getPhones().add(p1);        
                                
        String userJson = objectMapper.writeValueAsString(user);
    	
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.user.name").value("Jhon Contreras"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.user.phones[*].number", Matchers.hasItem("1234556677")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.token").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.token").isNotEmpty());
    }
    
    /**
     * When you send a post request with right body and email is used by another user, then service must return a 403 http code.
     */
    @Test
    public void testCreateUserExistingEmail() throws Exception {    	 
    	// Mock user data
        User user = new User();        
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("Passw0rd@Secure");
        
        Phone p1 = new Phone();
        p1.setCityCode("CO");
        p1.setCountryCode("CO");
        p1.setNumber("1234556677");               
        p1.setUser(user);
        
        user.getPhones().add(p1);        
                                
        String userJson = objectMapper.writeValueAsString(user);
    	
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isForbidden());                
    }
    
    /**
     * When you send a post request with right body and password is not secure, then service must return a 403 http code.
     */
    @Test
    public void testCreateUserFormatPassword() throws Exception {    	 
    	// Mock user data
        User user = new User();        
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("PasswordNoSecure");
        
        Phone p1 = new Phone();
        p1.setCityCode("CO");
        p1.setCountryCode("CO");
        p1.setNumber("1234556677");               
        p1.setUser(user);
        
        user.getPhones().add(p1);        
                                
        String userJson = objectMapper.writeValueAsString(user);
    	
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isForbidden());                
    }
    
    /**
     * When you send a put request with right body and email is not duplicated, then service must return a 200 http code.
     */
    @Test
    public void testUpdateUser() throws Exception {    	 
    	// Mock user data
        User user = new User();
    	user.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174001"));
        user.setName("John Doe Modified");
        user.setEmail("john.doe@example.com");
        user.setPassword("Passw0rd@SecureModified");                                                       
        
        Phone p1 = new Phone();
        p1.setCityCode("COUpdated");
        p1.setCountryCode("COUpdated");
        p1.setNumber("12345566770");        
        p1.setUser(user);
        
        user.getPhones().add(p1);        
                                
        String userJson = objectMapper.writeValueAsString(user);
    	
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/user/"+UUID.fromString("123e4567-e89b-12d3-a456-426614174001"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.user.name").value("John Doe Modified"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.user.phones[*].number", Matchers.hasItem("12345566770")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.token").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.token").isNotEmpty());
        
    }
    
    /**
     * When you send a put request with right body and email is used by another user, then service must return a 403 http code.
     */
    @Test
    public void testUpdateUserExistingEmail() throws Exception {    	 
    	// Mock user data
        User user = new User();
    	user.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174001"));
        user.setName("John Doe Modified");
        user.setEmail("alice.smith@example.com");
        user.setPassword("Passw0rd@SecureModified");                                                        
        
        Phone p1 = new Phone();
        p1.setCityCode("COUpdated");
        p1.setCountryCode("COUpdated");
        p1.setNumber("1234556677Updated");                       
        p1.setUser(user);
        
        user.getPhones().add(p1);        
                                
        String userJson = objectMapper.writeValueAsString(user);
    	
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/user/"+UUID.fromString("123e4567-e89b-12d3-a456-426614174001"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isForbidden());                
    }
    
    /**
     * When you send a put request with right body and password is not secure, then service must return a 403 http code.
     */
    @Test
    public void testUpdateUserNoFormatPassword() throws Exception {    	 
    	// Mock user data
        User user = new User();
    	user.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174001"));
        user.setName("John Doe Modified");
        user.setEmail("alice.smith@example.com");
        user.setPassword("PSecureModified");                                                        
        
        Phone p1 = new Phone();
        p1.setCityCode("COUpdated");
        p1.setCountryCode("COUpdated");
        p1.setNumber("1234556677Updated");                       
        p1.setUser(user);
        
        user.getPhones().add(p1);        
                                
        String userJson = objectMapper.writeValueAsString(user);
    	
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/user/"+UUID.fromString("123e4567-e89b-12d3-a456-426614174001"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isForbidden());                
    }
}
