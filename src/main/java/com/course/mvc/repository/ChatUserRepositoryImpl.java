package com.course.mvc.repository;

import com.course.mvc.domain.ChatUser;
import com.course.mvc.exceptions.UserSaveException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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

    @Autowired
    private MessageSource messageSource;

    @Override
    // @Modifying /* Если удаляем, тогда эта аннотация нужна */
    // @Transactional(propagation = Propagation.REQUIRED) /* Propagation.REQUIRED - по умолчанию */
    @Transactional
    public void saveUser(ChatUser user) {
        try {
            em.persist(user);
            // Сохранить и завершить транзакцию, так как мы взяли на себя управление транзакцией и используем EntityManager
            em.flush();
        }catch (PersistenceException ex){
                if(ex.getCause() instanceof ConstraintViolationException){
                    throw new UserSaveException(messageSource.getMessage("user.exist", null, LocaleContextHolder.getLocale()));
                } else {
                    throw new UserSaveException(messageSource.getMessage("db.error", null, LocaleContextHolder.getLocale()));
                }
        }
    }
}
