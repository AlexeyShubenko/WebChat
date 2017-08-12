 package com.course.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MyAuthFilter /*extends AbstractAuthenticationProcessingFilter*/{


//    public MyAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
//        super(requiresAuthenticationRequestMatcher);
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request
//                                                , HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
//        String userLogin = request.getParameter("login").trim();
//        String userPassword = request.getParameter("password").trim();
//
//        Authentication authentication = new UsernamePasswordAuthenticationToken(userLogin,userPassword);
//
//        return authentication;
//    }

}