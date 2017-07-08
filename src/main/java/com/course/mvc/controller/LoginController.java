package com.course.mvc.controller;

import com.course.mvc.domain.ChatUser;
import com.course.mvc.domain.RoleEnum;
import com.course.mvc.dto.BanUserDto;
import com.course.mvc.dto.ChatUserDto;
import com.course.mvc.service.BanService;
import com.course.mvc.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by Alexey on 20.05.2017.
 */
@Controller
public class LoginController {

    private final LoginService loginService;
    private final BanService banService;

    @Autowired
    public LoginController(LoginService loginService,BanService banService) {
        this.loginService = loginService;
        this.banService=banService;
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
                              HttpSession session, HttpServletRequest request,
                              HttpServletResponse response,
                              RedirectAttributes redirectAttributes) {
        ChatUser chatUser = loginService.verifyLogin(login, password);

        if (Objects.nonNull(chatUser) && chatUser.getRole().getRole() == RoleEnum.USER) {

            session.setAttribute("user", chatUser);
            dropHttpOnlyFlag(session.getId(),request,response);
            BanUserDto banUserDto = new BanUserDto();
            banUserDto.setLogin(chatUser.getLogin());
            if(banService.isUserBaned(banUserDto)){
                return "redirect:/ban";
            }
            return "redirect:/chat";
        }
        if (Objects.nonNull(chatUser) && chatUser.getRole().getRole() == RoleEnum.ADMIN) {
            session.setAttribute("user", chatUser);
            return "redirect:/admin";
            //TODO user isBan
        }
        redirectAttributes.addFlashAttribute("error", "login.incorrect");
        return "redirect:/login";
    }

    ///сброс HttpOnlyFlag для возможности читать JavaScript-ом
    public void dropHttpOnlyFlag(String sessionId,HttpServletRequest request,
                                 HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies) {
            if(cookie.getName().equals("JSESSIONID")){
                cookie.setHttpOnly(false);
                response.addCookie(cookie);
            }
        }
    }

    @RequestMapping(value = "/ban",method = RequestMethod.GET)
    public ModelAndView banedUser(HttpSession session){
        ChatUser chatUser = (ChatUser) session.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
        if (Objects.nonNull(chatUser) ) {
            modelAndView.setViewName("ban");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }


}
