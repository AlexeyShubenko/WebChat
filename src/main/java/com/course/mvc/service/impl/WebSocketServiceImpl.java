package com.course.mvc.service.impl;

import com.course.mvc.domain.ChatUser;
import com.course.mvc.domain.Message;
import com.course.mvc.repository.ChatUserRepository;
import com.course.mvc.repository.MessageRepository;
import com.course.mvc.service.WebSocketService;
import com.course.mvc.service.redis.RedisDao;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Alexey on 10.06.2017.
 */
@Service
public class WebSocketServiceImpl implements WebSocketService {

    private MessageRepository messageRepository;
    private ChatUserRepository chatUserRepository;
    private RedisDao redisDao;

    @Autowired
    public WebSocketServiceImpl(MessageRepository messageRepository, ChatUserRepository chatUserRepository, RedisDao redisDao) {
        this.messageRepository = messageRepository;
        this.chatUserRepository = chatUserRepository;
        this.redisDao = redisDao;
    }

    @Override
    public void saveBroadcastMessage(String broadcastMessage, String senderLogin) {
        String value = senderLogin + ":" + broadcastMessage;
        redisDao.saveDataByKey("broadcast", value);
//        System.out.println("AFTER SAVE BROADCAST MESSAGE");
    }

    @Override
    @Transactional
    public List<Pair<String,String>> getMessagesByLogin(String receiverLogin) {
        List<Message> messages = messageRepository.findMessagesByLogin(receiverLogin);
        System.out.println("IN SERVICE " + messages.size());
//        Map<String, String> mapMessages = messages.stream()
//                .collect(Collectors.toMap(message -> message.getSender().getLogin(),
//                        message -> message.getBody()
//                ));
        List<Pair<String,String>> mapMessages = new ArrayList<>();
        for (Message message: messages){
            System.out.println(message.getSender());
            mapMessages.add(
                    new Pair<String, String>(message.getSender().getLogin(),message.getBody()));
        }
        System.out.println("END MESSAGES, MAP SIZE: " + mapMessages.size());
        return mapMessages;
    }

    public void deletePrivateMessages(String receiverLogin){
        messageRepository.deleteByLogin(receiverLogin);
    }

    @Override
    public List<Pair<String,String>> getBroadcastMessages() {
        List<String> messages = redisDao.getAllDataByKey("broadcast");
        List<Pair<String,String>> mapMessages = new ArrayList<>();
        for (String str : messages) {
            String[] senderAndMessage = str.split(":");
            mapMessages.add(new Pair<String, String>(senderAndMessage[0], senderAndMessage[1]));
        }
        return mapMessages;
    }

    @Override
    @Transactional
    public void savePrivateMessage(String receiverLogin, String senderLogin, String messageToForward) {
        Message message = new Message();
        message.setBody(messageToForward);
        ChatUser sender = chatUserRepository.findChatUserByLogin(senderLogin);
        ChatUser receiver = chatUserRepository.findChatUserByLogin(receiverLogin);
        message.setSender(sender);
        message.setDate(LocalDateTime.now());
        message.setReceiver(receiver);
        messageRepository.save(message);
    }
}
