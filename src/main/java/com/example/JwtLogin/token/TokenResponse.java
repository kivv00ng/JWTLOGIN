package com.example.JwtLogin.token;

import lombok.Data;

@Data
public class TokenResponse {

  private String accessToken;
  private String tokenType;

  public TokenResponse() {
  }

  public TokenResponse(String accessToken, String tokenType) {
    this.accessToken = accessToken;
    this.tokenType = tokenType;
  }
}
