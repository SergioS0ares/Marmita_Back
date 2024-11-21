package com.fachter.backend.controllers.auth;

import com.fachter.backend.repositories.UserRepository;
import com.fachter.backend.utils.JsonWebTokenUtil;
import com.fachter.backend.viewModels.auth.AuthenticationResponseViewModel;
import com.fachter.backend.viewModels.auth.RegisterUserViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@Tag("integration")
class UserManagementControllerIntegrationTest {

    private final String username = "definitely-a-new-username";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JsonWebTokenUtil jsonWebTokenUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @AfterEach
    void tearDown() {
        userRepository.findByUsername(username).ifPresent(userAccount -> userRepository.delete(userAccount));
    }

    @Test
    void register_givenNoContent_thenReturn400() throws Exception {
        mockMvc.perform(
                        post("/api/register")
                                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void register_givenViewModelWithoutContent_thenReturn400() throws Exception {
        String content = objectMapper.writeValueAsString(new RegisterUserViewModel());

        MvcResult mvcResult = mockMvc.perform(
                        post("/api/register")
                                .contentType("application/json")
                                .content(content))
                .andExpect(status().isBadRequest()).andReturn();

        assertEquals("Invalid data!", mvcResult.getResponse().getContentAsString());
    }

    @Test
    void register_givenUsernameAlreadyExists_thenReturn406() throws Exception {
        String content = objectMapper.writeValueAsString(new RegisterUserViewModel()
                .setUsername("admin")
                .setPassword("does not matter"));

        MvcResult mvcResult = mockMvc.perform(
                        post("/api/register")
                                .contentType("application/json")
                                .content(content))
                .andExpect(status().isNotAcceptable()).andReturn();

        assertEquals("Username already exists!", mvcResult.getResponse().getContentAsString());
    }

    @Test
    void register_givenNewUsername_thenSaveAndReturn200() throws Exception {
        String password = "does-actually-matter";
        String content = objectMapper.writeValueAsString(new RegisterUserViewModel()
                .setUsername(username)
                .setPassword(password));

        var mvcResult = mockMvc.perform(
                        post("/api/register")
                                .contentType("application/json")
                                .content(content))
                .andExpect(status().isOk()).andReturn();

        var user = userRepository.findByUsername(username);
        assertTrue(user.isPresent());
        assertEquals(username, user.get().getUsername());
        assertTrue(passwordEncoder.matches(password, user.get().getPassword()));
        AuthenticationResponseViewModel response = objectMapper
                .readValue(mvcResult.getResponse().getContentAsByteArray(), AuthenticationResponseViewModel.class);
        assertEquals(username, jsonWebTokenUtil.extractUsername(response.token));

    }
}