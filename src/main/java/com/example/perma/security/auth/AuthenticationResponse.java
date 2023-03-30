package com.example.perma.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

  private String token;

  public static AuthenticationResponse notValidEmail() {
    return new AuthenticationResponse("Invalid email address");
  }
}
