package com.inflearn.securityex1.config.auth;

import com.inflearn.securityex1.model.User;
import com.inflearn.securityex1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * SecurityConfig에서 .loginProcessingUrl("/login") 설정함
 * '/login' 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어있는
 * loadUserByUsername 함수가 실행된다! => 규칙임
 */

@Service
public class PrincipalDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    /** Security Session(내부에 Authentication(내부에 UserDetails))
     *  PrincipalDetails를 리턴하면서 Authentication에 넣어줌
     *  그리고 그 Authentication을 Session에 넣어줌
     *  이 모든 작업을 loadUserByUsername 함수가 처리한다!!
     */
     @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username);
        if(userEntity != null) {
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}
