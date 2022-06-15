package com.effcode.clean.me.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.effcode.clean.me.model.EmailSendRequest;
import com.effcode.clean.me.service.EmailHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

  //todo @PreAuthorize(hasAuthority(..))
  @Operation(summary = "Sends email from given request.",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully sent."),
          @ApiResponse(responseCode = "400", description = "Input validation error."),
          @ApiResponse(responseCode = "500", description = "Internal server error.")
      })
  @PostMapping(path = "/send-email")
  public ResponseEntity<Void> send(@RequestBody @Valid EmailSendRequest request) {
    emailHandler.send(request);
    return ResponseEntity.ok().build();
  }

}
