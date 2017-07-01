package com.course.mvc.dto;

import java.util.List;

/**
 * Created by Alexey on 01.07.2017.
 */
public class ResponseDto extends AuthDto{

    private List<BanUserDto> users;

    public List<BanUserDto> getUsers() {
        return users;
    }

    public void setUsers(List<BanUserDto> users) {
        this.users = users;
    }
}
