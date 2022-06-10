package com.effcode.clean.me.rest.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.effcode.clean.me.rest.model.EmailSendRequest;
import com.effcode.clean.me.rest.service.EmailHandler;
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
                .content("aaa")
                .subject("aaa")
                .build())))
        .andExpect(status().is4xxClientError());
    verifyNoInteractions(emailHandler);
  }

  @Test
  @SneakyThrows
  void testValidation_WrongFormatAddress() {
    mockMvc.perform(post(URL).contentType(APPLICATION_JSON)
            .content(mapper.writeValueAsString(EmailSendRequest.builder()
                .address("aaaa")
                .content("aaa")
                .subject("aaa")
                .build())))
        .andExpect(status().is4xxClientError());
    verifyNoInteractions(emailHandler);
  }

  @Test
  @SneakyThrows
  void testValidation_EmptySubject() {

    mockMvc.perform(post(URL).contentType(APPLICATION_JSON)
            .content(mapper.writeValueAsString(EmailSendRequest.builder()
                .address("aaa@gmail.com")
                .content("aaa")
                .build())))
        .andExpect(status().is4xxClientError());
    verifyNoInteractions(emailHandler);
  }

  @Test
  @SneakyThrows
  void testValidation_EmptyContent() {

    mockMvc.perform(post(URL).contentType(APPLICATION_JSON)
            .content(mapper.writeValueAsString(EmailSendRequest.builder()
                .address("aaa@gmail.com")
                .subject("aaa")
                .build())))
        .andExpect(status().is4xxClientError());
    verifyNoInteractions(emailHandler);
  }

  @Test
  @SneakyThrows
  void test_AllCorrect() {

    doNothing().when(emailHandler).send(anyString(), anyString(), anyString());
    mockMvc.perform(post(URL).contentType(APPLICATION_JSON)
            .content(mapper.writeValueAsString(EmailSendRequest.builder()
                .address("aaa@gmail.com")
                .content("aaa")
                .subject("aaa")
                .build())))
        .andExpect(status().isOk());
    verify(emailHandler, times(1)).send(anyString(), anyString(), anyString());
  }

  @Test
  @SneakyThrows
  void test_ServerError5xx() {

    doThrow(new RuntimeException("bye.")).when(emailHandler).send(anyString(), anyString(), anyString());
    mockMvc.perform(post(URL).contentType(APPLICATION_JSON)
            .content(mapper.writeValueAsString(EmailSendRequest.builder()
                .address("aaa@gmail.com")
                .content("aaa")
                .subject("aaa")
                .build())))
        .andExpect(status().is5xxServerError());
    verify(emailHandler, times(1)).send(anyString(), anyString(), anyString());
  }

}