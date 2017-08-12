package com.course.security;

import com.course.mvc.domain.Ban;
import com.course.mvc.domain.ChatUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Alexey on 12.08.2017.
 */

public class MyUserDetails implements UserDetails{

    private ChatUser chatUser;
    private boolean isBaned;

    public MyUserDetails(ChatUser chatUser, boolean isBaned) {
        this.chatUser = chatUser;
        this.isBaned = isBaned;
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(chatUser.getRole().getRole().toString()));
        return grantedAuthorities;
    }

    public ChatUser getChatUser() {
        return chatUser;
    }

    @Override
    public String getPassword() {
        return chatUser.getPassword();
    }

    @Override
    public String getUsername() {
        return chatUser.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isBaned;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
