package com.bjoggis.mono.openai.adapter.in.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ThreadController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestWebThreadControllerConfiguration.class)
@ActiveProfiles("test")
public class WebThreadControllerTest {


  @Autowired
  MockMvc mockMvc;

  @Test
  void postOfThreadEndpointReturnsStatus201() throws Exception {
    mockMvc.perform(post("/v1/thread").with(csrf())
            .contentType("application/json")
            .content("{\"accountId\": 1}"))
        .andExpect(status().isCreated());
  }

  @Test
  void getOfThreadEndpointReturnsStatus200() throws Exception {
    mockMvc.perform(get("/v1/thread/1").with(csrf()))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"));
  }
}
