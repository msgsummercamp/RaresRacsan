package com.example.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MockMvcTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldHaveStatusOk() throws Exception {
        // testing the status of the /users endpoint
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldHaveDefaultResponse() throws Exception {
        String expectedJson = """
        [
            {"id":1,"name":"John Doe","email":"mail4@gmail.com"},
            {"id":2,"name":"John Doe2","email":"mail1@gmail.com"},
            {"id":3,"name":"John Doe3","email":"mail2@gmail.com"},
            {"id":4,"name":"John Doe4","email":"mail3@gmail.com"}
        ]
    """;

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }


    @Test
    void testHeaders() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"));
    }
}
