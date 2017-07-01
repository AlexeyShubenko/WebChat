package com.course.mvc.service.impl;

import com.course.mvc.domain.Ban;
import com.course.mvc.domain.ChatUser;
import com.course.mvc.dto.BanUserDto;
import com.course.mvc.dto.ChatUserDto;
import com.course.mvc.exceptions.IncorrectBanUserLoginException;
import com.course.mvc.exceptions.ServiceException;
import com.course.mvc.exceptions.UserSaveException;
import com.course.mvc.repository.BansRepository;
import com.course.mvc.repository.ChatUserRepository;
import com.course.mvc.service.BanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Alexey on 01.07.2017.
 */
@Service
public class BanServiceImpl implements BanService {

    private BansRepository bansRepository;
    private ChatUserRepository chatUserRepository;

    private MessageSource messageSource;

    @Autowired
    public BanServiceImpl(BansRepository bansRepository, ChatUserRepository chatUserRepository,MessageSource messageSource) {
        this.bansRepository = bansRepository;
        this.chatUserRepository = chatUserRepository;
        this.messageSource = messageSource;
    }

    @Override
    public void addUserToBanList(ChatUserDto chatUserDto) {
        Ban ban = new Ban();
        ChatUser chatUser = chatUserRepository.findChatUserByLogin(chatUserDto.getLogin());
        if(Objects.isNull((chatUser))){
            throw new IncorrectBanUserLoginException(messageSource.getMessage("admin.ban.user", null, LocaleContextHolder.getLocale()));
        }
        ban.setUser(chatUser);
        bansRepository.save(ban);
    }

    @Override
    @Transactional
    public void deleteUserFromBanList(Long userId) {
//        ChatUser chatUser = chatUserRepository.findOne(id);
        Ban ban = bansRepository.findBanByChatUserId(userId);
        bansRepository.delete(ban);
    }

    @Override
    public List<ChatUserDto> getAllUsersExceptAdmin() {
        List<ChatUser> chatUsers = chatUserRepository.findAllExceptAdmin();
        List<ChatUserDto> chatUserDtos = chatUsers.stream()
                .map(chatUser -> new ChatUserDto.Builder()
                        .setId(chatUser)
                        .setLogin(chatUser)
                        .setName(chatUser)
                        .setPassword(chatUser)
                        .build())
                .collect(Collectors.toList());

        return chatUserDtos;
    }

    @Override
    public boolean isUserBaned(BanUserDto banUserDto) {
        Ban ban = bansRepository.findBanByChatUserLogin(banUserDto.getLogin());
        return Objects.nonNull(ban);
    }
}
