package com.effcode.clean.me.rest.service;

import com.effcode.clean.me.rest.config.SmtpProperties;
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

  public void send(String address, String subject, String content) {

    SmtpEmail smtpEmail = buildEmail(address, subject, content);
    smtpHandler.post(smtpEmail);
    log.info("Email with subject [{}] sent successfully to [{}].", subject, address);
  }

  private SmtpEmail buildEmail(String address, String subject, String content) {
    SmtpEmail smtpEmail = new SmtpEmail();
    smtpEmail.adrs = new String[] {address};
    smtpEmail.content = content;
    smtpEmail.subject = subject;
    smtpEmail.password = smtpProperties.getPass();
    smtpEmail.username = smtpProperties.getLogin();
    return smtpEmail;
  }

}
