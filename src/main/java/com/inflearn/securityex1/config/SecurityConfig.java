package com.inflearn.securityex1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Spring security filter(SecurityConfig.java) -> Spring filter chain에 등록
public class SecurityConfig {

    //해당 메서드에서 리턴되는 오브젝트를 IoC로 등록해준다.
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                // user, manager, admin 일 때에는 access 거른다.
                .antMatchers("/user/**").authenticated() // 인증만 되면 들어갈 수 있는 주소
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()

                // 위의 세 경우에 모두 로그인 페이지로 자동 이동하도록 한다.
                .and()
                .formLogin()
                .loginPage("/loginForm")

                // '/login' 주소가 호출되면 security가 낚아채서 대신 로그인을 해준다.
                .loginProcessingUrl("/login")

                // '/loginForm'으로 들어와서 로그인 성공하면 '/'로 보내주고
                // 다른 url로 들어오면 로그인 성공시 그쪽으로 보내준다.
                .defaultSuccessUrl("/");
        return http.build();
    }

}
