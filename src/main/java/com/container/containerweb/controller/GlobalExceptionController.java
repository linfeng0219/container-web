package com.container.containerweb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler
    public void exceptionHandler(Exception e) {
        logger.error(e.getMessage(), e);
    }
}
