package com.inflearn.securityex1.config.auth;

import com.inflearn.securityex1.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Security가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
 * 로그인이 완료되면 Security Session을 만들어준다.(Security ContextHolder의 키 값에 저장)
 * Session에 들어갈 수 있는 object => Authentication 타입 객체
 * Authentication 안에 User 정보가 있어야함
 * User 오브젝트 타입 => UserDetails 타입 객체
 *
 * Security Session => Authentication => UserDetails
 */
public class PrincipalDetails implements UserDetails {
    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    // 해당 User의 권한을 리턴하는 곳!
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        /**
         * 만약 로그인 한지 1년이 넘으면 비활성화시킨다는 정책이 있다면
         * 현재시간 - User.getLoginTime => 1년을 초과하면
         * 아래의 return 값을 false로 바꿔준다.
         */
        return true;
    }
}
