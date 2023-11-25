package com.bjoggis.mono.openai.adapter.in.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bjoggis.mono.openai.application.AIAccountService;
import com.bjoggis.mono.openai.application.port.ChatThreadRepository;
import com.bjoggis.mono.openai.domain.AccountId;
import com.bjoggis.mono.openai.domain.ChatThread;
import com.bjoggis.mono.user.adapter.in.TestPrincipal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ChatThreadController.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureDataJpa
@Import(TestWebThreadControllerConfiguration.class)
@ActiveProfiles("test")
public class WebChatThreadControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ChatThreadRepository repository;

  @Autowired
  AIAccountService aiAccountService;

  @Test
  void postOfThreadEndpointReturnsStatus201() throws Exception {
    mockMvc.perform(post("/v1/thread")
            .principal(new TestPrincipal("test"))
            .with(csrf()))
        .andExpect(status().isCreated());
  }

  @Test
  void getOfThreadEndpointReturnsStatus200() throws Exception {
    ChatThread saved = repository.save(new ChatThread());
    mockMvc.perform(get("/v1/thread/" + saved.getChatThreadId().chatThreadId()).with(csrf()))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"));
  }

  @Test
  void deleteOfThreadEndpointReturnsStatus200() throws Exception {
    ChatThread saved = repository.save(new ChatThread(AccountId.of(1L)));
    mockMvc.perform(delete("/v1/thread/" + saved.getChatThreadId().chatThreadId())
            .principal(new TestPrincipal("test")))
        .andExpect(status().isOk());
  }

  @Test
  void deleteOfThreadEndpointWhenThreadDoesntExistReturnsStatus400() throws Exception {
    mockMvc.perform(delete("/v1/thread/9999")
            .principal(new TestPrincipal("test")))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.title").value("Bad Request"))
        .andExpect(jsonPath("$.detail").exists());
  }
}
