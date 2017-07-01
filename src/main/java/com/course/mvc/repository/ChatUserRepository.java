package com.course.mvc.repository;

import com.course.mvc.domain.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alexey on 27.05.2017.
 */
@Repository
public interface ChatUserRepository extends JpaRepository<ChatUser,Long>,ChatUserRepositoryCustom {

    ChatUser findChatUserByLogin(String login);

    @Query("select u from ChatUser u where u.role.role<>com.course.mvc.domain.RoleEnum.ADMIN")
    List<ChatUser> findAllExceptAdmin();
}
