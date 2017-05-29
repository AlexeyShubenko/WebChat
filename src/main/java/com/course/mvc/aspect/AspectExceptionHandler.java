package com.course.mvc.aspect;

import com.course.mvc.dto.ChatUserDto;
import com.course.mvc.exceptions.ServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by Alexey on 27.05.2017.
 */
@ControllerAdvice
public class AspectExceptionHandler {

    @ExceptionHandler(value = ServiceException.class)
    public String saveUserError(ServiceException ex, RedirectAttributes attributes){
        attributes.addFlashAttribute("error",ex.getMessage());
        attributes.addFlashAttribute("save",new ChatUserDto());
        return "redirect:/registration";
    }

}
