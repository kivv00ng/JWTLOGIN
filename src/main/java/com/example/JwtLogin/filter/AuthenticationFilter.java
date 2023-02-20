package com.example.JwtLogin.filter;

import com.example.JwtLogin.token.JwtTokenProvider;
import com.example.JwtLogin.token.AuthorizationExtractor;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

@Slf4j
public class AuthenticationFilter implements Filter {

  private static final String[] whitelist = {"/", "/login"};

  private AuthorizationExtractor authExtractor;
  private JwtTokenProvider jwtTokenProvider;

  public AuthenticationFilter(AuthorizationExtractor authExtractor,
      JwtTokenProvider jwtTokenProvider) {
    this.authExtractor = authExtractor;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    String requestURI = httpRequest.getRequestURI();
//    HttpServletResponse httpResponse = (HttpServletResponse) response;

    if (isLoginCheckPath(requestURI)) {

      String token = authExtractor.extract(httpRequest, "Bearer");

      if (!jwtTokenProvider.validateToken(token)) {
        throw new IllegalArgumentException("유효하지 않은 토큰");
      }

      String name = jwtTokenProvider.getSubject(token);
      httpRequest.setAttribute("name", name);
    }
    chain.doFilter(request, response);
  }


  private boolean isLoginCheckPath(String requestURI) {
    return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
  }
}
