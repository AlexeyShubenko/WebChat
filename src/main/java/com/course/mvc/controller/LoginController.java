package com.course.mvc.controller;

import com.course.mvc.domain.ChatUser;
import com.course.mvc.domain.RoleEnum;
import com.course.mvc.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by Alexey on 20.05.2017.
 */
@Controller
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET, name = "loginUser")
    public ModelAndView loginUser() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        modelAndView.addObject("loginHandler", "./login");
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String verifyLogin(@RequestParam("login") String login,
                              @RequestParam("password") String password,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
//        Optional<ChatUser> chatUser = loginService.verifyLogin(login, password);
//        chatUser.ifPresent((user) -> { session.setAttribute("user", user); });

        ChatUser chatUser = loginService.verifyLogin(login, password);

        if (Objects.nonNull(chatUser) && chatUser.getRole().getRole() == RoleEnum.USER) {
            session.setAttribute("user", chatUser);
            return "redirect:/chat";
        }
        if (Objects.nonNull(chatUser) && chatUser.getRole().getRole() == RoleEnum.ADMIN) {
            session.setAttribute("user", chatUser);
            return "redirect:/admin";
        }
        redirectAttributes.addFlashAttribute("error", "login.incorrect");
        return "redirect:/login";
    }

}
