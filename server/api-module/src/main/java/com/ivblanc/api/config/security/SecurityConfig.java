package com.ivblanc.api.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.ivblanc.api.oauth.handler.OAuth2AuthenticationFailureHandler;
import com.ivblanc.api.oauth.handler.OAuth2SuccessHandler;
import com.ivblanc.api.oauth.repository.HttpCookieOAuth2AuthorizationRequestRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomOauth2UserService customOauth2UserService;
    private OAuth2SuccessHandler oAuth2SuccessHandler;
    private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**");
        web.ignoring().antMatchers("/api/**");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);

        http.httpBasic().disable(); // rest api 이므로 기본설정 사용안함
        http.csrf()
                .disable()
                .headers()
                .frameOptions()
                .disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                    // permitAll() 처리한 경로의 API는 JWT 값이 없어도 실행 가능

            //01.19 수정 이부분인가??
                    .antMatchers("/com/ivblanc/api/**", "/com/ivblanc/api/auth/**").permitAll()
                    .antMatchers("/docs/**").permitAll()
                    .anyRequest().hasRole("USER")
                .and()
                    // 403 예외처리 핸들링
                    .exceptionHandling().accessDeniedPage("/user/denied")
                .and()
                    .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        http.logout()
            .logoutSuccessUrl("/")
            .and()
            .oauth2Login()
            .authorizationEndpoint()
            .authorizationRequestRepository(cookieAuthorizationRequestRepository())
            .baseUri("/oauth2/authorization")
            .and()
            .redirectionEndpoint()
            .baseUri("/oauth2/callback/*")
            .and()
            .userInfoEndpoint()
            .userService(customOauth2UserService)
            .and()
            .successHandler(oAuth2SuccessHandler)
            .failureHandler((AuthenticationFailureHandler) oAuth2AuthenticationFailureHandler);


        //http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
}


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder().encode("1234")).roles("ADMIN")
                .and()
                .withUser("guest").password(passwordEncoder().encode("guest")).roles("GUEST");
    }

}
