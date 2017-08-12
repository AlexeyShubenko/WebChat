package com.course.security;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * Created by Владимир on 29.07.2017.
 */
public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot {


    public CustomMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public boolean myAuth(String key){

       for(GrantedAuthority garant: authentication.getAuthorities()){
           if(garant.getAuthority().equals(key)){
               return true;
           }

       }
       return false;
    }
}
