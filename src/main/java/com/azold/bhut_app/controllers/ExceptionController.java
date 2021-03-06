package com.azold.bhut_app.controllers;

import com.azold.bhut_app.exceptions.StandardError;
import com.azold.bhut_app.utils.DateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<StandardError>> beanValidationException(MethodArgumentNotValidException e){
        List<StandardError> listStandardError = e.getAllErrors().stream().map(
                objectError -> convertObjectErrorToStandardError(objectError)).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(listStandardError);
    }

    private StandardError convertObjectErrorToStandardError(ObjectError objectError){
        return new StandardError(objectError.getDefaultMessage(),
                DateUtils.convertSystemTimeMillisToString(System.currentTimeMillis()),
                HttpStatus.NOT_ACCEPTABLE.value());
    }




}
