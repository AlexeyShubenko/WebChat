package com.course.mvc.exceptions;

/**
 * Created by Alexey on 27.05.2017.
 */
public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }
}
