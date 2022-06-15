package com.effcode.clean.me.config;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@ConfigurationProperties(prefix = "com.effcode.smtp")
@Validated
public class SmtpProperties {

  @NotBlank
  private String login;
  @NotBlank
  private String pass;

}
