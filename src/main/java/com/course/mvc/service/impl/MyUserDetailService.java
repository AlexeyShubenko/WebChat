package com.course.mvc.service.impl;

import com.course.mvc.domain.Ban;
import com.course.mvc.domain.ChatUser;
import com.course.mvc.repository.BansRepository;
import com.course.mvc.repository.ChatUserRepository;
import com.course.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Created by Alexey on 12.08.2017.
 */
@Service
public class MyUserDetailService implements UserDetailsService{

    private ChatUserRepository chatUserRepository;
    private BansRepository bansRepository;

    @Autowired
    public MyUserDetailService(ChatUserRepository chatUserRepository, BansRepository bansRepository) {
        this.chatUserRepository = chatUserRepository;
        this.bansRepository = bansRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        ChatUser chatUser = chatUserRepository.findChatUserByLogin(login);
        Ban ban = bansRepository.findBanByChatUserLogin(login);
        if(Objects.isNull(ban)){
            return new MyUserDetails(chatUser, false);
        }
        return new MyUserDetails(chatUser, true);
    }

}
