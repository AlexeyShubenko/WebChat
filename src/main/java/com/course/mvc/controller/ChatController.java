package com.course.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * Created by Alexey on 10.06.2017.
 */
@Controller
public class ChatController {

    @Autowired
    Environment environment;

    @PreAuthorize("myAuth('ADMIN') or myAuth('USER')")
    @RequestMapping(value = "/chat",method = RequestMethod.GET)
    public ModelAndView getChatPage(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();

        if(Objects.nonNull(session.getAttribute("user"))){
            modelAndView.addObject("sockUrl",environment.getProperty("socket.url"));
            modelAndView.setViewName("chat");
        }else {
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }

}
