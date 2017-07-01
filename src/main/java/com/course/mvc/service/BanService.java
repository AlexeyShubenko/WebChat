package com.course.mvc.service;

import com.course.mvc.dto.BanUserDto;
import com.course.mvc.dto.ChatUserDto;

import java.util.List;

/**
 * Created by Alexey on 01.07.2017.
 */
public interface BanService {
    
    void addUserToBanList(ChatUserDto chatUserDto);

    void deleteUserFromBanList(Long id);

    List<ChatUserDto> getAllUsersExceptAdmin();

    boolean isUserBaned(BanUserDto banUserDto);
}
