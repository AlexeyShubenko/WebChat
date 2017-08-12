package com.course.security;

import com.course.mvc.domain.ChatUser;
import com.course.mvc.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Владимир on 29.07.2017.
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private LoginService loginService;

    @Autowired
    public CustomAuthenticationProvider(LoginService loginService){
        this.loginService = loginService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("in custom auth !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        //receive user name and password
        String userName = authentication.getName().trim();
        String password = authentication.getCredentials().toString().trim();

        ChatUser user = loginService.verifyLogin(userName, password);
        if(Objects.isNull(user)){
            return null;
        }
        String role = user.getRole().getRole().toString().trim();

        if (Objects.nonNull(role)) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            //в grantedAuthorities передается только роль пользователя
            GrantedAuthority grantedAuthorities = new SimpleGrantedAuthority(role);
            authorities.add(grantedAuthorities);
            Authentication auth = new UsernamePasswordAuthenticationToken(user, password, authorities);

            return auth;
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        System.out.println("in support !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }




}
