package com.course.mvc.dto;

import com.course.mvc.domain.ChatUser;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Alexey on 20.05.2017.
 */
public class ChatUserDto {

    private Long id;
    @NotNull
    @Pattern(regexp = "\\w{2,}",message = "{message.name.err}")
    private String name;
    @NotNull
    @Pattern(regexp = "\\w{2,}",message = "{message.login.err}")
    private String login;
    @NotNull
    @Size(min = 5,message = "{message.password.size}")
    private String password;

    public static class Builder{

        ChatUserDto chatUserDto = new ChatUserDto();

        public Builder setId(ChatUser user){
            chatUserDto.setId(user.getId());
            return this;
        }

        public Builder setName(ChatUser user){
            chatUserDto.setName(user.getName());
            return this;
        }
        public Builder setLogin(ChatUser user){
            chatUserDto.setLogin(user.getLogin());
            return this;
        }
        public Builder setPassword(ChatUser user){
            chatUserDto.setPassword(user.getPassword());
            return this;
        }
        public ChatUserDto build(){
            return chatUserDto;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
