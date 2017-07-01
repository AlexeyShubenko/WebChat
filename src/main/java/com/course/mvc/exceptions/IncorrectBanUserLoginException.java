package com.course.mvc.exceptions;

/**
 * Created by Alexey on 27.05.2017.
 */
public class IncorrectBanUserLoginException extends RuntimeException {

    public IncorrectBanUserLoginException(String message) {
        super(message);
    }
}
