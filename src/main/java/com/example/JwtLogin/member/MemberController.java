package com.example.JwtLogin.member;

import com.example.JwtLogin.token.TokenResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class MemberController {

  private final MemberService memberService;

  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  @ResponseBody
  @PostMapping("/login")
  public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
    String token = memberService.createToken(loginRequest);

    log.info("#####token: " + token);
    return ResponseEntity.ok().body(new TokenResponse(token, "bearer"));
  }

  @GetMapping("/info")
  public ResponseEntity<MemberResponse> getUserFromToken(HttpServletRequest request) {
    String name = (String) request.getAttribute("name");
    Member member = memberService.findByUserName(name);
    return ResponseEntity.ok().body(new MemberResponse(member));
  }

  @GetMapping("/")
  public String loginForm() {
    return "/loginForm.html";
  }
}
