package com.bonelf.auth.core.exception;

import com.bonelf.frame.web.core.advice.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandlerAdvice extends GlobalExceptionHandler {

}