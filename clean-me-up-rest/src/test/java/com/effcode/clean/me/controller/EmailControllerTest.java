package com.effcode.clean.me.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.effcode.clean.me.model.EmailSendRequest;
import com.effcode.clean.me.rest.EmailController;
import com.effcode.clean.me.service.EmailHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(EmailController.class) //we can add also error messages checks
class EmailControllerTest {

  private static final String URL = "/api/v1/send-email";
  private static final String SUBJECT = "Code quality discussion.";
  private static final String EMAIL = "testmail@gmail.coom";
  private static final String CONTENT = "lets talk";
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private EmailHandler emailHandler;
  private ObjectMapper mapper = new ObjectMapper();

  @Test
  @SneakyThrows
  void testValidation_EmptyAddress() {

    mockMvc.perform(post(URL).contentType(APPLICATION_JSON)
            .content(mapper.writeValueAsString(EmailSendRequest.builder()
                .content(CONTENT)
                .subject(SUBJECT)
                .build())))
        .andExpect(status().is4xxClientError());
    verifyNoInteractions(emailHandler);
  }

  @Test
  @SneakyThrows
  void testValidation_WrongFormatAddress() {
    mockMvc.perform(post(URL).contentType(APPLICATION_JSON)
            .content(mapper.writeValueAsString(EmailSendRequest.builder()
                .address("testmail")
                .content(CONTENT)
                .subject(SUBJECT)
                .build())))
        .andExpect(status().is4xxClientError());
    verifyNoInteractions(emailHandler);
  }

  @Test
  @SneakyThrows
  void testValidation_EmptySubject() {

    mockMvc.perform(post(URL).contentType(APPLICATION_JSON)
            .content(mapper.writeValueAsString(EmailSendRequest.builder()
                .address(EMAIL)
                .content(CONTENT)
                .build())))
        .andExpect(status().is4xxClientError());
    verifyNoInteractions(emailHandler);
  }

  @Test
  @SneakyThrows
  void testValidation_EmptyContent() {

    mockMvc.perform(post(URL).contentType(APPLICATION_JSON)
            .content(mapper.writeValueAsString(EmailSendRequest.builder()
                .address(EMAIL)
                .subject(SUBJECT)
                .build())))
        .andExpect(status().is4xxClientError());
    verifyNoInteractions(emailHandler);
  }

  @Test
  @SneakyThrows
  void test_AllCorrect() {

    doNothing().when(emailHandler).send(any(EmailSendRequest.class));
    mockMvc.perform(post(URL).contentType(APPLICATION_JSON)
            .content(mapper.writeValueAsString(EmailSendRequest.builder()
                .address(EMAIL)
                .content(CONTENT)
                .subject(SUBJECT)
                .build())))
        .andExpect(status().isOk());
    verify(emailHandler, times(1)).send(any(EmailSendRequest.class));
  }

  @Test
  @SneakyThrows
  void test_ServerError5xx() {

    doThrow(new RuntimeException("bye.")).when(emailHandler).send(any(EmailSendRequest.class));
    mockMvc.perform(post(URL).contentType(APPLICATION_JSON)
            .content(mapper.writeValueAsString(EmailSendRequest.builder()
                .address(EMAIL)
                .content(CONTENT)
                .subject(SUBJECT)
                .build())))
        .andExpect(status().is5xxServerError());
    verify(emailHandler, times(1)).send(any(EmailSendRequest.class));
  }

}