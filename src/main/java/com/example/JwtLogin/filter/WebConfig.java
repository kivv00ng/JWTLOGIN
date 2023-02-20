package com.example.JwtLogin.filter;

import com.example.JwtLogin.filter.AuthenticationFilter;
import com.example.JwtLogin.token.AuthorizationExtractor;
import com.example.JwtLogin.token.JwtTokenProvider;
import javax.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  private AuthorizationExtractor authExtractor;
  private JwtTokenProvider jwtTokenProvider;

  public WebConfig(AuthorizationExtractor authExtractor, JwtTokenProvider jwtTokenProvider) {
    this.authExtractor = authExtractor;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Bean
  public FilterRegistrationBean loginFilter() {
    FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<Filter>();

    filterRegistrationBean.setFilter(new AuthenticationFilter(authExtractor, jwtTokenProvider));
    filterRegistrationBean.setOrder(1);
    filterRegistrationBean.addUrlPatterns("/*");
    return filterRegistrationBean;
  }

//  @Bean
//  public FilterRegistrationBean authorizationFilter() {
//    FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<Filter>();
//
//    filterRegistrationBean.setFilter(new AuthorizationFilter());
//    filterRegistrationBean.setOrder(2);
//    filterRegistrationBean.addUrlPatterns("/*");
//    return filterRegistrationBean;
//  }

}
