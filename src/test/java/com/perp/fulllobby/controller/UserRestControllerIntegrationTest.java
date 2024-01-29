package com.perp.fulllobby.controller;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.perp.fulllobby.exception.UserNotFoundException;
import com.perp.fulllobby.services.MyUserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRestControllerIntegrationTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    MyUserService userService;

    @Test
    public void whenGetUserByIdAndDoesNotExist_thenReturnUserNotFound()
    throws UserNotFoundException {
        
            UUID random = UUID.randomUUID();

        }

}