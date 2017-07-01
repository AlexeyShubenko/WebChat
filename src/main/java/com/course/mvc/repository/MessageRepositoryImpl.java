package com.course.mvc.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Alexey on 01.07.2017.
 */
@Repository
public class MessageRepositoryImpl implements MessageRepositoryCustom{

    @PersistenceContext
    private EntityManager em;

    @Modifying
    @Override
    @Transactional
    public void deleteByLogin(String receiverLogin) {
            em.createNativeQuery("DELETE FROM message where " +
                    "message.receiver_id in (select id from chatuser where chatuser.login=:receiverLogin)")
                    .setParameter("receiverLogin", receiverLogin).executeUpdate();
    }

}
