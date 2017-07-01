package com.course.mvc.dto;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by Alexey on 01.07.2017.
 */
public class BanUserDto extends ResourceSupport {

    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
