package com.example.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest // Loads the full Spring context
@AutoConfigureMockMvc // Enables MockMvc for HTTP testing
class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddAndGetUsers() throws Exception {
        // Add a new user
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Omar\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Omar"));

        // Get all users and verify the list contains "Omar"
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Omar"));
    }

    @Test
    void testDeleteUser() throws Exception {
        // Create a user
        String response = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Omar\"}"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Extract the ID from the JSON response
        long id = JsonPath.read(response, "$.id");

        // Delete the user
        mockMvc.perform(delete("/api/users/" + id))
                .andExpect(status().isOk());

        // Optionally verify the user is gone
        mockMvc.perform(get("/api/users/" + id))
                .andExpect(status().isNotFound());
    }

}
