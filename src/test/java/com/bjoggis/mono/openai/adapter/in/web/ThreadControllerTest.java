package com.bjoggis.mono.openai.adapter.in.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ThreadController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class ThreadControllerTest {


  @Autowired
  MockMvc mockMvc;

  @Test
  void postOfThreadEndpointCreatesANewThread() throws Exception {
    mockMvc.perform(post("/v1/thread").with(csrf()))
        .andExpect(status().isCreated());
  }
}
