package com.example.JwtLogin.member;

import com.example.JwtLogin.token.JwtTokenProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MemberService {

  private final MemberRepository memberRepository;
  private final JwtTokenProvider jwtTokenProvider;

  public MemberService(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider) {
    this.memberRepository = memberRepository;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  public Member findByUserName(String userName) {
    return memberRepository.findByUsername(userName)
        .orElseThrow(IllegalArgumentException::new);
  }

  public String createToken(LoginRequest loginRequest) {
    Member member = memberRepository.findByUsername(loginRequest.getName())
        .orElseThrow(IllegalArgumentException::new);
    //비밀번호 확인 등의 유효성 검사 진행
    return jwtTokenProvider.createToken(member.getUsername());
  }

}
