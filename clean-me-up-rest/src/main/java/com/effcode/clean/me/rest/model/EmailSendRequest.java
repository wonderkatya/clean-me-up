package com.effcode.clean.me.rest.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor // we may also use declarative way of describing APIs and generate models and rest clients from yml.
public class EmailSendRequest {

  @NotBlank
  @Email
  private String address;
  @NotBlank
  private String subject;
  @NotBlank
  @Size(max = 65000)
  private String content;
}
