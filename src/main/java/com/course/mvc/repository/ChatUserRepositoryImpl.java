package com.course.mvc.repository;

import com.course.mvc.domain.ChatUser;
import com.course.mvc.exceptions.UserSaveException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

/**
 * Created by Alexey on 27.05.2017.
 */
@Repository
public class ChatUserRepositoryImpl implements ChatUserRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Modifying
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUser(ChatUser user) {
        try {
            System.out.println("EXCEPTION_ERROR!!!");
            em.persist(user);
            em.flush();
            System.out.println("AFTER_EXCEPTION_ERROR!!!");
        }catch (Throwable ex){
            ex.printStackTrace();
            if(ex.getCause() instanceof PersistenceException){
                PersistenceException persistenceException = (PersistenceException) ex.getCause();
                if(persistenceException.getCause() instanceof ConstraintViolationException){
                    throw new UserSaveException("user.exist");
                }
            }
            throw new UserSaveException("db.error");
        }
    }
}
