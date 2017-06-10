package com.course.mvc.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Alexey on 10.06.2017.
 */
  /*
        Client ot Server
            {"sessionid": "smth"}
            {"broadcast":"Hello"}
            {"login":"vasya",
            "message":"Hi"}
            {"logout":""}
            {"":""}
        Server to Client
            {"auth":"yes"}
            {"auth":"yes",
            "list":["Vasya","Petya"]} список активных пользователей
            {"auth":"yes",
            "login":"Vasya",
            "message":"Hi"}
     */

public class ChatWebSocketHandler extends TextWebSocketHandler {

    ///String - login клієнта, WebSocketSession - його webSocket сесія
    private Map<String, WebSocketSession> socketSessionMap = new HashMap<>();
    //зберігаються логін і сесія
    private Map<String, String> httpSessionLogin = new HashMap<>();
    private WebSocketService socketService;

    @Autowired
    public void setSocketService(WebSocketService socketService) {
        this.socketService = socketService;
    }

    //визивається коли від клієнта прийшов message
    @Override
    public void handleTextMessage(WebSocketSession socketSession, TextMessage message) throws Exception {
        ///json -> String
        String jsonMessage = message.getPayload();
        Gson gson = new Gson();
        Type gsonType = new TypeToken<HashMap<String, String>>() {
        }.getType();
        //формуємо map з json
        Map<String, String> stringMap = gson.fromJson(jsonMessage, gsonType);
        //if true - user хочет стать online
        if (Objects.nonNull(stringMap.get("sessionid"))) {
            ///регистрируем online
            if (registration(stringMap.get("sessionid"))) {
                //registrated online
                socketSession.sendMessage(new TextMessage("{'auth':'yes'}"));
                ///сказать всем юзерам что online
                sendListOfUsers();
                sendAllMessageForUser(socketSession);
            } else {
                //если нету в httpSession юзера с sessionId
                socketSession.sendMessage(new TextMessage("{'auth':'no'}"));
            }
        } else {
            //1. кто то хочет в обход системи послать json
            //2. пользователь уже online и хочеть послать json
            ///получуаем с sessionSocketMap
            String senderLogin = getKeyByValue(socketSession);
            //true (ключ null) -  уже онлайн
            if (Objects.nonNull(senderLogin)) {
                if (Objects.nonNull(stringMap.get("broadcast"))) {
                    //получаем message
                    String broadcastMessage = stringMap.get("broadcast");
                    socketService.saveBroadcastMessage(broadcastMessage);
                    ///формируем json ответа
                    JsonObject broadcastJson = new JsonObject();
                    broadcastJson.addProperty(senderLogin, broadcastMessage);
                    ///отсилает сообщ. всем активним пользователям
                    sendAllActiveUsers(broadcastJson);
                } else if (Objects.nonNull(stringMap.get("login"))) { //приватные сообщения если ключ login
                    String receiverLogin = stringMap.get("login");
                    String messageToForward = stringMap.get("message");
                    if (Objects.nonNull(receiverLogin)) {
                        //user active
                        forwardMessage(receiverLogin, senderLogin, messageToForward);
                    } else {
                        //если user offline
                        saveMessageToDB(receiverLogin, senderLogin, messageToForward);
                    }
                } else if (Objects.nonNull(stringMap.get("logout"))) {//хочет уйти из чата
                    ///убиваем session
                    invalidateHttpSession(socketSession);
                    ///удаляем user из активных
                    removeUserFromMap(socketSession);
                } else {
                    socketSession.sendMessage(new TextMessage("bad json"));
                }
            } else {
                socketSession.sendMessage(new TextMessage("{'auth':'no'}"));
            }

        }
    }


}
