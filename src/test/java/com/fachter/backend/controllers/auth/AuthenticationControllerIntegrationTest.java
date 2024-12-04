package com.fachter.backend.controllers.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2ns.backend.config.Role;
import com.j2ns.backend.models.auth.AuthenticationRequestModel;
import com.j2ns.backend.models.auth.AuthenticationResponseModel;
import com.j2ns.backend.utils.JsonWebTokenUtil;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@Tag("integration")
class AuthenticationControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JsonWebTokenUtil jsonWebTokenUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void createAuthenticationToken_givenInvalidUsername_thenReturnUnauthorized() throws Exception {
        String content = objectMapper.writeValueAsString(new AuthenticationRequestModel()
                .setUsername("invalid-username")
                .setPassword("unimportant"));

        mockMvc.perform(
                        post("/api/authenticate")
                                .contentType("application/json")
                                .content(content))
                .andExpect(status().isUnauthorized());
    }


    @Test
    public void createAuthenticationToken_givenInvalidPassword_thenReturnUnauthorized() throws Exception {
        String content = objectMapper.writeValueAsString(new AuthenticationRequestModel()
                .setUsername("admin")
                .setPassword("invalid-password"));

        mockMvc.perform(
                        post("/api/authenticate")
                                .contentType("application/json")
                                .content(content))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void givenValidCredentials_thenReturnResponseViewModel() throws Exception {
        String content = objectMapper.writeValueAsString(new AuthenticationRequestModel()
                .setUsername("admin")
                .setPassword("admin123"));

        MvcResult mvcResult = mockMvc.perform(
                        post("/api/authenticate")
                                .contentType("application/json")
                                .content(content))
                .andExpect(status().isOk())
                .andReturn();

        AuthenticationResponseModel response = objectMapper
                .readValue(mvcResult.getResponse().getContentAsByteArray(), AuthenticationResponseModel.class);
        assertEquals(List.of(Role.ADMIN.name(), Role.USER.name()), response.authorities);
        assertEquals("admin", jsonWebTokenUtil.extractUsername(response.token));
    }

    @Test
    void givenNoToken_thenReturnUnauthorized() throws Exception {
        mockMvc.perform(
                get("/api/refresh-token")).andExpect(status().isUnauthorized());
    }

    @Test
    void givenValidToken_thenReturnRefreshedToken() throws Exception {
        UserDetails userDetails = userDetailsService.loadUserByUsername("admin");
        String validJwt = "Bearer " + jsonWebTokenUtil.generateToken(userDetails);

        MvcResult mvcResult = mockMvc.perform(
                        get("/api/refresh-token").header("Authorization", validJwt))
                .andExpect(status().isOk()).andReturn();

        AuthenticationResponseModel response = objectMapper
                .readValue(mvcResult.getResponse().getContentAsByteArray(), AuthenticationResponseModel.class);
        assertEquals(List.of(Role.ADMIN.name(), Role.USER.name()), response.authorities);
        assertEquals("admin", jsonWebTokenUtil.extractUsername(response.token));
    }
}