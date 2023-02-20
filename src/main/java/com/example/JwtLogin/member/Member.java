package com.example.JwtLogin.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
@Entity
public class Member extends TimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 30, unique = true)
  private String username; // 아이디

  @Column(nullable = false, unique = true)
  private String nickname;

  @Column(length = 100)
  private String password;

  @Column(nullable = false, length = 50)
  private String email;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  protected Member() {
  }

  public Member(String username, String nickname, String password, String email, Role role) {
    this.username = username;
    this.nickname = nickname;
    this.password = password;
    this.email = email;
    this.role = role;
  }

  /* 회원정보 수정을 위한 set method*/
  public void modify(String nickname, String password) {
    this.nickname = nickname;
    this.password = password;
  }

  /* 소셜로그인시 이미 등록된 회원이라면 수정날짜만 업데이트하고
   * 기존 데이터는 그대로 보존하도록 예외처리 */
  public Member updateModifiedDate() {
    this.onPreUpdate();
    return this;
  }

  public String getRoleValue() {
    return this.role.getValue();
  }

}
