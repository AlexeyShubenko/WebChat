package com.course.mvc.service.impl;

import com.course.mvc.domain.ChatUser;
import com.course.mvc.domain.Role;
import com.course.mvc.domain.RoleEnum;
import com.course.mvc.dto.ChatUserDto;
import com.course.mvc.exceptions.ServiceException;
import com.course.mvc.exceptions.UserSaveException;
import com.course.mvc.repository.ChatUserRepository;
import com.course.mvc.repository.RoleRepository;
import com.course.mvc.service.LoginService;
import com.course.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Created by Alexey on 27.05.2017.
 */
@Service
public class LoginServiceImpl implements LoginService {

    private ChatUserRepository chatUserRepository;
    private RoleRepository roleRepository;
    private UserDetailsService userDetailsService;
    private AuthenticationManager authenticateManager;

    @Autowired
    public LoginServiceImpl(ChatUserRepository chatUserRepository, RoleRepository roleRepository,UserDetailsService userDetailsService,AuthenticationManager authenticateManager) {
        this.chatUserRepository = chatUserRepository;
        this.roleRepository = roleRepository;
        this.userDetailsService = userDetailsService;
        this.authenticateManager = authenticateManager;
    }

    @Override
    public void save(ChatUserDto chatUserDto) {
        Role role = roleRepository.findRoleByRoleName(RoleEnum.USER);
        ChatUser chatUser = new ChatUser.Builder()
                            .setName(chatUserDto)
                            .setLogin(chatUserDto)
                            .setPassword(chatUserDto)
                            .setRole(role)
                            .build();
        try {
            chatUserRepository.saveUser(chatUser);
        }catch (UserSaveException ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public ChatUser verifyLogin(String login, String password) {
//        ChatUser chatUser = chatUserRepository.findChatUserByLogin(login);
//        if (Objects.nonNull(chatUser) && chatUser.getPassword().equals(password)) {
//            return chatUser;
//        }
//        return null;
        MyUserDetails userDetails = (MyUserDetails) userDetailsService.loadUserByUsername(login);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        authenticateManager.authenticate(usernamePasswordAuthenticationToken);
        if(usernamePasswordAuthenticationToken.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        return userDetails.getChatUser();
    }
}
