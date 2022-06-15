package com.effcode.clean.me.service;

import com.effcode.clean.me.config.SmtpProperties;
import com.effcode.clean.me.model.EmailSendRequest;
import com.effcode.clean.me.support.SmtpEmail;
import com.effcode.clean.me.support.SmtpHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class EmailHandler {

  private final SmtpHandler smtpHandler;
  public final SmtpProperties smtpProperties;

  public void send(EmailSendRequest emailSendRequest) {

    SmtpEmail smtpEmail = buildEmail(emailSendRequest);
    smtpHandler.post(smtpEmail);
    log.info("Email with subject [{}] sent successfully to [{}].",
        emailSendRequest.getSubject(), emailSendRequest.getAddress());
  }

  private SmtpEmail buildEmail(EmailSendRequest emailSendRequest) {
    SmtpEmail smtpEmail = new SmtpEmail();
    smtpEmail.adrs = new String[] {emailSendRequest.getAddress()};
    smtpEmail.content = emailSendRequest.getContent();
    smtpEmail.subject = emailSendRequest.getSubject();
    smtpEmail.password = smtpProperties.getPass();
    smtpEmail.username = smtpProperties.getLogin();
    return smtpEmail;
  }

}
