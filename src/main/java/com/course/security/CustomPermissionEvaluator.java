package com.course.security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Владимир on 29.07.2017.
 */
public class CustomPermissionEvaluator implements PermissionEvaluator{
    @Override
    public boolean hasPermission(Authentication authentication, Object targetObject, Object permissionObject) {
        if(Objects.isNull(authentication)||Objects.isNull(targetObject)||Objects.isNull(permissionObject)){
            return false;
        }
        return hasPrivileges(authentication,targetObject.toString().toUpperCase(),permissionObject.toString().toUpperCase());
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String targetType, Object permission) {

        if(Objects.isNull(authentication)||Objects.isNull(targetType)||Objects.isNull(permission)){
            return false;
        }
        return hasPrivileges(authentication,targetType.toUpperCase(),permission.toString().toUpperCase());
    }

    private boolean hasPrivileges(Authentication authentication, String targetType, String permission){

        for(GrantedAuthority authority:authentication.getAuthorities()){
            if (authority.getAuthority().startsWith(targetType)) {
                if(authority.getAuthority().contains(permission)){
                    return true;
                }
            }
        }
        return false;
    }

//    @Override
//    public boolean hasAuthority
}
