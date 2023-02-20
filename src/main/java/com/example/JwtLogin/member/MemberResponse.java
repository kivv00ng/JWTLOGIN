package com.example.JwtLogin.member;

import com.example.JwtLogin.member.Member;
import lombok.Getter;

@Getter
public class MemberResponse {

  public String name;
  public String password;

  public MemberResponse(Member member) {
    this.name = member.getUsername();
    this.password = member.getPassword();
  }
}
