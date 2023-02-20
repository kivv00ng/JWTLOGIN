package com.example.JwtLogin.member;

import lombok.Getter;

@Getter
public enum Role {
  USER("ROLE_USER"),
  ADMIN("ROLE_ADMIN"),
  SOCIAL("ROLE_SOCIAL"); // OAuth

  private final String value;

  Role(String value) {
    this.value = value;
  }

//  public String getValue() {
//    return value;
//  }
}
