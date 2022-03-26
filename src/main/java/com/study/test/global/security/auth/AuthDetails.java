package com.study.test.global.security.auth;

import com.study.test.domain.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@RequiredArgsConstructor
public class AuthDetails implements UserDetails {

    private final User user;

    //계정이 갖고있는 권한 목록을 리턴한다.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    //계정의 비밀번호를 리턴한다.
    @Override
    public String getPassword() {
        return null;
    }

    //계정의 이름을 리턴한다.
    @Override
    public String getUsername() {
        return user.getAccountId();
    }

    //계정이 만료되지 않았는 지 리턴한다. (true: 만료안됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정이 잠겨있지 않았는 지 리턴한다. (true: 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //비밀번호가 만료되지 않았는 지 리턴한다. (true: 만료안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정이 활성화(사용가능)인 지 리턴한다. (true: 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }

}
