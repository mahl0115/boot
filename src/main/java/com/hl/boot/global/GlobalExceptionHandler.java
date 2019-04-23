package com.hl.boot.global;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @author mahl
 * @date 2019-04-23
 */
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = Exception.class)
    public void defaultExceptionProcess(HttpServletRequest req, Exception e) {
        System.out.println(e);
    }
}
