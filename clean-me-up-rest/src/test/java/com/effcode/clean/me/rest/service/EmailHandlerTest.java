package com.effcode.clean.me.rest.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.effcode.clean.me.rest.config.SmtpProperties;
import com.effcode.clean.me.support.SmtpEmail;
import com.effcode.clean.me.support.SmtpHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
class EmailHandlerTest {

  @Captor
  private ArgumentCaptor<SmtpEmail> smtpEmailArgumentCaptor;
  @Mock
  private SmtpHandler smtpHandler;
  @Mock
  private SmtpProperties smtpProperties;
  @InjectMocks
  private EmailHandler emailHandler;

  @Test
  void testSuccessfulSending() {
    when(smtpProperties.getLogin()).thenReturn("login");
    when(smtpProperties.getPass()).thenReturn("pass");
    doNothing().when(smtpHandler).post(smtpEmailArgumentCaptor.capture());

    emailHandler.send("123@gmal.com", "hi", "how are you?");

    verify(smtpHandler, times(1)).post(smtpEmailArgumentCaptor.capture());
    verify(smtpProperties, times(1)).getLogin();
    verify(smtpProperties, times(1)).getPass();
    final SmtpEmail email = smtpEmailArgumentCaptor.getValue();
    assertAll(
        () -> assertNotNull(email),
        () -> assertEquals("123@gmal.com", email.adrs[0]),
        () -> assertEquals("hi", email.subject),
        () -> assertEquals("how are you?", email.content),
        () -> assertEquals("login", email.username),
        () -> assertEquals("pass", email.password)
        );
  }

}