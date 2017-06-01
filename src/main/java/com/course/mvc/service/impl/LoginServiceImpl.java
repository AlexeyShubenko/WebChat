package com.course.mvc.service.impl;

import com.course.mvc.domain.ChatUser;
import com.course.mvc.dto.ChatUserDto;
import com.course.mvc.exceptions.ServiceException;
import com.course.mvc.exceptions.UserSaveException;
import com.course.mvc.repository.ChatUserRepository;
import com.course.mvc.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by Alexey on 27.05.2017.
 */
@Service
public class LoginServiceImpl implements LoginService {

    private ChatUserRepository chatUserRepository;

    @Autowired
    public LoginServiceImpl(ChatUserRepository chatUserRepository) {
        this.chatUserRepository = chatUserRepository;
    }

    @Override
//    @Transactional
    public void save(ChatUserDto chatUserDto) {
        ChatUser chatUser = new ChatUser.Builder()
                            .setName(chatUserDto)
                            .setLogin(chatUserDto)
                            .setPassword(chatUserDto)
                            .build();
        try {
            System.out.println("IN_LOGIN_SERVICE!");
            chatUserRepository.saveUser(chatUser);
        }catch (UserSaveException ex){
            ///intercept by aspectExceptionHandler
            throw new ServiceException(ex.getMessage());
        }
    }
}
