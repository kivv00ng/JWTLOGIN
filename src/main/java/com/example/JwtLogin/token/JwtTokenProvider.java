package com.example.JwtLogin.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

  private String secretKey;
  private long validityInMilliseconds;

  public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") String secretKey,
      @Value("${security.jwt.token.expire-length}") long validityInMilliseconds) {
    this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    this.validityInMilliseconds = validityInMilliseconds;
  }

  //토큰생성
  public String createToken(String subject) {
    Claims claims = Jwts.claims().setSubject(subject); //payload에 만들 claim 생성

    Date now = new Date();

    Date validity = new Date(now.getTime()
        + validityInMilliseconds);

    return Jwts.builder()
        .setClaims(claims) // Payload에 담길 데이터를 입력(이름그대로)
        .setIssuedAt(now) //발급 시간으로 현재 시간을 입력
        .setExpiration(validity) //만료기간
        .signWith(SignatureAlgorithm.HS256, secretKey) //사인값
        .compact(); //위 설정대로 JWT 토큰을 생성
  }

  //토큰에서 값 추출
  public String getSubject(String token) {
    //사인값을 base64로 디코딩+비밀키로 파싱=>클래임값을 base64로 파싱 => 가져온 멤버값으로 확인하기
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  public String resolveAccessToken(HttpServletRequest request) {
    return request.getHeader("AccessToken");
  }

  //유효한 토큰인지 확인
  public boolean validateToken(String token) {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token); //비밀키로 파싱..
      if (claims.getBody().getExpiration().before(new Date())) { //시간값이 지금보다 과거인지 확인..
        return false;
      }
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }
}
