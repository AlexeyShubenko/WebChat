package com.course.mvc.service;

import javafx.util.Pair;

import java.util.List;
import java.util.Map;

/**
 * Created by Alexey on 10.06.2017.
 */
public interface WebSocketService {

    void saveBroadcastMessage(String broadcastMessage, String senderLogin);
    List<Pair<String,String>> getMessagesByLogin(String receiverLogin);
    List<Pair<String,String>> getBroadcastMessages();

    void savePrivateMessage(String receiverLogin, String senderLogin, String messageToForward);

    void deletePrivateMessages(String receiverLogin);
}
