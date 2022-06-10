package com.effcode.clean.me.rest.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.effcode.clean.me.rest.model.EmailSendRequest;
import com.effcode.clean.me.rest.service.EmailHandler;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(
    consumes = APPLICATION_JSON_VALUE,
    produces = APPLICATION_JSON_VALUE,
    path = "/api/v1"
)
public class EmailController {

  private final EmailHandler emailHandler;

  //todo @PreAuthorize(hasRole(..))
  @PostMapping(path = "/send-email")
  public ResponseEntity<Void> send(@RequestBody @Valid EmailSendRequest request) {
    emailHandler.send(request.getAddress(), request.getSubject(), request.getContent());
    return ResponseEntity.ok().build();
  }

}
